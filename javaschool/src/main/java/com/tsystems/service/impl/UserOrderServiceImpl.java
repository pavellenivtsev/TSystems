package com.tsystems.service.impl;

import com.tsystems.dao.api.*;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.Location;
import com.tsystems.entity.UserOrder;
import com.tsystems.entity.Waypoint;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.enumaration.WaypointType;
import com.tsystems.service.api.UserOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    private static final Logger LOGGER= LogManager.getLogger(UserOrderServiceImpl.class);

    private final UserOrderDao userOrderDao;

    private final CargoDao cargoDao;

    private final TruckDao truckDao;

    private final LocationDao locationDao;

    private final WaypointDao waypointDao;

    private final ModelMapper modelMapper;

    @Autowired
    public UserOrderServiceImpl(UserOrderDao userOrderDao, CargoDao cargoDao,TruckDao truckDao, LocationDao locationDao, WaypointDao waypointDao, ModelMapper modelMapper) {
        this.userOrderDao = userOrderDao;
        this.cargoDao = cargoDao;
        this.truckDao=truckDao;
        this.locationDao = locationDao;
        this.waypointDao = waypointDao;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public List<UserOrderDto> findAll() {
        List<UserOrder> userOrderList = userOrderDao.findAll();
        return userOrderList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(UserOrderDto userOrderDto, String cargoName, double cargoWeight, String locationFromCity, String locationToCity) {
//        if(locationFromCity.isEmpty()){
//            logger.error("");
//        }
//        if (locationToCity.isEmpty()){
//            logger.error();
//        }

        //create new order
        UserOrder userOrder=convertToEntity(userOrderDto);
        userOrder.setStatus(UserOrderStatus.NOT_COMPLETED);
        userOrderDao.save(userOrder);

        //create new cargo
        Cargo cargo = new Cargo();
        cargo.setName(cargoName);
        cargo.setWeight(cargoWeight);
        cargo.setStatus(CargoStatus.PREPARED);
        cargo.setUserOrder(userOrder);
        cargoDao.save(cargo);

        //create place of loading and place of unloading
        Location from = new Location();
        Location to = new Location();
        from.setCity(locationFromCity);
        to.setCity(locationToCity);
        locationDao.save(from);
        locationDao.save(to);

        //create waypoints
        Waypoint waypointFrom = new Waypoint();
        Waypoint waypointTo = new Waypoint();
        waypointFrom.setType(WaypointType.LOADING);
        waypointTo.setType(WaypointType.UNLOADING);
        waypointFrom.setLocation(from);
        waypointTo.setLocation(to);
        waypointFrom.setCargo(cargo);
        waypointTo.setCargo(cargo);
        waypointDao.save(waypointFrom);
        waypointDao.save(waypointTo);
    }

    @Override
    @Transactional
    public void update(UserOrderDto userOrderDto, String cargoName, double cargoWeight, String locationFromCity, String locationToCity) {
        UserOrder userOrder=userOrderDao.findById(userOrderDto.getId());
        userOrder.setUniqueNumber(userOrderDto.getUniqueNumber());

        userOrderDao.update(userOrder);

        Cargo cargo=userOrder.getCargoList().get(0);
        cargo.setName(cargoName);
        cargo.setWeight(cargoWeight);
        cargoDao.update(cargo);

        List<Waypoint> waypointList=cargo.getWaypointList();

        Location from= waypointList.get(0).getLocation();
        Location to =waypointList.get(1).getLocation();
        from.setCity(locationFromCity);
        to.setCity(locationToCity);
        locationDao.update(from);
        locationDao.update(to);
    }

    @Override
    @Transactional
    public UserOrderDto findById(long id) {
        return convertToDto(userOrderDao.findById(id));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        UserOrder userOrder=userOrderDao.findById(id);
        for(Cargo cargo:userOrder.getCargoList()){
            for(Waypoint waypoint:cargo.getWaypointList()){
                locationDao.delete(waypoint.getLocation());
                waypointDao.delete(waypoint);
            }
            cargoDao.delete(cargo);
        }
        userOrderDao.delete(userOrder);
    }

    @Override
    @Transactional
    public void addTruck(UserOrderDto userOrderDto, long id) {
        UserOrder userOrder=userOrderDao.findById(userOrderDto.getId());
        userOrder.setTruck(truckDao.findById(id));
        userOrderDao.update(userOrder);
    }

    private UserOrderDto convertToDto(UserOrder userOrder) {
        return modelMapper.map(userOrder, UserOrderDto.class);
    }

    private UserOrder convertToEntity(UserOrderDto userOrderDto) {
        return modelMapper.map(userOrderDto, UserOrder.class);
    }
}
