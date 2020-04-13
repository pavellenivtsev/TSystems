package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;
import com.tsystems.service.api.SecondAppService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecondAppServiceImpl implements SecondAppService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<DriverDto> findAllDrivers() {
        List<Driver> driverList=driverDao.findAll();
        return driverList.stream()
                .map(driver -> modelMapper.map(driver,DriverDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<UserOrderDto> findAllOrders() {
        List<UserOrder> userOrderList = userOrderDao.findAll();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder,UserOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TruckDto> findAllTrucks() {
        List<Truck> truckList = truckDao.findAll();
        return truckList.stream()
                .map(truck -> modelMapper.map(truck,TruckDto.class))
                .collect(Collectors.toList());
    }


}
