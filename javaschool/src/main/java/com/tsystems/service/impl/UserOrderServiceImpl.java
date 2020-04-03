package com.tsystems.service.impl;

import com.tsystems.dao.api.*;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Location;
import com.tsystems.entity.UserOrder;
import com.tsystems.entity.Waypoint;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.enumaration.WaypointType;
import com.tsystems.service.api.UserOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    private static final Logger LOGGER = LogManager.getLogger(UserOrderServiceImpl.class);

    private final UserOrderDao userOrderDao;

    private final TruckDao truckDao;

    private final LocationDao locationDao;

    private final WaypointDao waypointDao;

    private final ModelMapper modelMapper;

    @Autowired
    public UserOrderServiceImpl(UserOrderDao userOrderDao, TruckDao truckDao, LocationDao locationDao, WaypointDao waypointDao, ModelMapper modelMapper) {
        this.userOrderDao = userOrderDao;
        this.truckDao = truckDao;
        this.locationDao = locationDao;
        this.waypointDao = waypointDao;
        this.modelMapper = modelMapper;
    }

    /**
     * Finds all orders
     *
     * @return List<UserOrderDto>
     */
    @Override
    @Transactional
    public List<UserOrderDto> findAllSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllSortedByDate();
        return userOrderList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates new order
     *
     * @param locationFromCity
     * @param locationToCity
     * @param latitudeFrom
     * @param longitudeFrom
     * @param latitudeTo
     * @param longitudeTo
     * @param distance
     * @return true if order was created
     */
    @Override
    @Transactional
    public boolean addOrder(String locationFromCity, String locationToCity,
                            double latitudeFrom, double longitudeFrom,
                            double latitudeTo, double longitudeTo,
                            double distance) {
        String uniqueNumber = generateUniqueNumber();
        outer:
        for (UserOrder userOrder : userOrderDao.findAll()) {
            if (userOrder.getUniqueNumber().equals(uniqueNumber)) {
                uniqueNumber = generateUniqueNumber();
                break outer;
            }
        }
        UserOrder userOrder = new UserOrder();
        userOrder.setCreationDate(new DateTime());
        userOrder.setStatus(UserOrderStatus.NOT_TAKEN);
        userOrder.setUniqueNumber(uniqueNumber);
        userOrder.setDistance(distance / 1000);
        userOrderDao.save(userOrder);

        //create place of loading and place of unloading
        Location from = new Location();
        Location to = new Location();
        from.setCity(locationFromCity);
        from.setLatitude(latitudeFrom);
        from.setLongitude(longitudeFrom);
        to.setCity(locationToCity);
        to.setLatitude(latitudeTo);
        to.setLongitude(longitudeTo);
        locationDao.save(from);
        locationDao.save(to);

        //create waypoints
        Waypoint waypointFrom = new Waypoint();
        Waypoint waypointTo = new Waypoint();
        waypointFrom.setType(WaypointType.LOADING);
        waypointTo.setType(WaypointType.UNLOADING);
        waypointFrom.setLocation(from);
        waypointTo.setLocation(to);
        waypointFrom.setUserOrder(userOrder);
        waypointTo.setUserOrder(userOrder);
        waypointDao.save(waypointFrom);
        waypointDao.save(waypointTo);

        LOGGER.info("Created a new order with unique number" + userOrder.getUniqueNumber());
        return true;
    }

    /**
     * Update order
     *
     * @param orderId
     * @param locationFromCity
     * @param locationToCity
     * @param latitudeFrom
     * @param longitudeFrom
     * @param latitudeTo
     * @param longitudeTo
     * @param distance
     * @return true if order was updated
     */
    @Override
    @Transactional
    public boolean update(long orderId, String locationFromCity, String locationToCity,
                          double latitudeFrom, double longitudeFrom,
                          double latitudeTo, double longitudeTo, double distance) {
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }
        userOrder.setDistance(distance / 1000);
        userOrderDao.update(userOrder);

        Location from = userOrder.getWaypointList().get(0).getLocation();
        Location to = userOrder.getWaypointList().get(1).getLocation();
        from.setCity(locationFromCity);
        from.setLatitude(latitudeFrom);
        from.setLongitude(longitudeFrom);
        to.setCity(locationToCity);
        to.setLatitude(latitudeTo);
        to.setLongitude(longitudeTo);
        locationDao.update(from);
        locationDao.update(to);
        return true;
    }

    /**
     * Finds order by id
     *
     * @param id
     * @return UserOrderDto
     */
    @Override
    @Transactional
    public UserOrderDto findById(long id) {
        return convertToDto(userOrderDao.findById(id));
    }

    /**
     * Delete order by id
     *
     * @param id
     * @return true if order was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        UserOrder userOrder = userOrderDao.findById(id);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }
        for (Waypoint waypoint : userOrder.getWaypointList()) {
            locationDao.delete(waypoint.getLocation());
        }
        userOrderDao.delete(userOrder);
        LOGGER.info("An order with unique number " + userOrder.getUniqueNumber()+" was deleted ");
        return true;
    }

    /**
     * Add truck to order
     *
     * @param userOrderDto
     * @param truckId
     * @return true if truck was added
     */
    @Override
    @Transactional
    public boolean addTruck(UserOrderDto userOrderDto, long truckId) {
        if (userOrderDto.getCargoList() == null) {
            return false;
        }
        UserOrder userOrder = userOrderDao.findById(userOrderDto.getId());
        userOrder.setTruck(truckDao.findById(truckId));
        userOrder.setStatus(UserOrderStatus.TAKEN);
        userOrderDao.update(userOrder);

        return true;
    }

    private UserOrderDto convertToDto(UserOrder userOrder) {
        return modelMapper.map(userOrder, UserOrderDto.class);
    }

    private UserOrder convertToEntity(UserOrderDto userOrderDto) {
        return modelMapper.map(userOrderDto, UserOrder.class);
    }

    /**
     * Generates a random sequence
     * @return String
     */
    private String generateUniqueNumber() {
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
