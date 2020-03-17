package com.tsystems.service.impl;

import com.tsystems.dao.api.CargoDao;
import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dao.api.WaypointDao;
import com.tsystems.dao.impl.CargoDaoImpl;
import com.tsystems.dao.impl.LocationDaoImpl;
import com.tsystems.dao.impl.UserOrderDaoImpl;
import com.tsystems.dao.impl.WaypointDaoImpl;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.Location;
import com.tsystems.entity.UserOrder;
import com.tsystems.entity.Waypoint;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.enumaration.WaypointType;
import com.tsystems.service.api.UserOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    private UserOrderDao userOrderDao = new UserOrderDaoImpl();

    private CargoDao cargoDao=new CargoDaoImpl();

    private LocationDao locationDao=new LocationDaoImpl();

    private WaypointDao waypointDao=new WaypointDaoImpl();

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Autowired
    public void setWaypointDao(WaypointDao waypointDao) {
        this.waypointDao = waypointDao;
    }

    @Override
    @Transactional
    public List<UserOrderDto> findAll() {
        List<UserOrder> userOrderList = userOrderDao.findAll();
        List<UserOrderDto> userOrderDtoList = userOrderList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return userOrderDtoList;
    }

    @Override
    @Transactional
    public void save(UserOrderDto userOrderDto, String cargoName, String cargoWeight, String locationFromCity, String locationToCity) {
        //create new order
        UserOrder userOrder=convertToEntity(userOrderDto);
        userOrder.setStatus(UserOrderStatus.NOT_COMPLETED);
        userOrderDao.save(userOrder);

        //create new cargo
        Cargo cargo = new Cargo();
        cargo.setName(cargoName);
        cargo.setWeight(Double.parseDouble(cargoWeight));
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
    public void update(UserOrderDto userOrderDto, String cargoName, String cargoWeight, String locationFromCity, String locationToCity) {
        UserOrder userOrder=userOrderDao.findById(userOrderDto.getId());
        userOrder.setUniqueNumber(userOrderDto.getUniqueNumber());

        userOrderDao.update(userOrder);

        Cargo cargo=userOrder.getCargoList().get(0);
        cargo.setName(cargoName);
        cargo.setWeight(Double.parseDouble(cargoWeight));
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

    private UserOrderDto convertToDto(UserOrder userOrder) {
        return modelMapper.map(userOrder, UserOrderDto.class);
    }

    private UserOrder convertToEntity(UserOrderDto userOrderDto) {
        return modelMapper.map(userOrderDto, UserOrder.class);
    }
}
