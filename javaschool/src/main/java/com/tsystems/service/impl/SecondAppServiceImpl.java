package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.EntryDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.UserOrder;
import com.tsystems.service.api.SecondAppService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
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

    /**
     * Finds all orders
     *
     * @return List<UserOrderDto>
     */
    @Override
    @Transactional
    public List<UserOrderDto> findAllCompletedOrCarriedOrders() {
        List<UserOrder> userOrderList = userOrderDao.findAllCompletedOrCarried();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder, UserOrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Creates count table
     *
     * @return List<EntryDto>
     */
    @Override
    @Transactional
    public List<EntryDto> createCountTable() {
        List<EntryDto> countTable = new LinkedList<>();
        Long trucksCount = truckDao.getTrucksCount();
        Long carryingOrderTrucksCount = truckDao.getCarryingOrderTrucksCount();
        Long faultyTrucksCount = truckDao.getFaultyTrucksCount();
        Long freeTrucksCount = trucksCount - carryingOrderTrucksCount - faultyTrucksCount;
        countTable.add(new EntryDto("Total drivers count", driverDao.getDriversCount()));
        countTable.add(new EntryDto("Drivers on shift", driverDao.getOnShiftDriversCount()));
        countTable.add(new EntryDto("Drivers on vacation", driverDao.getRestDriversCount()));
        countTable.add(new EntryDto("Total trucks count", trucksCount));
        countTable.add(new EntryDto("Trucks carrying an order", carryingOrderTrucksCount));
        countTable.add(new EntryDto("Free trucks", freeTrucksCount));
        countTable.add(new EntryDto("Faulty trucks", faultyTrucksCount));
        return countTable;
    }
}
