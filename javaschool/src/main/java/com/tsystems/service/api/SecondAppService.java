package com.tsystems.service.api;

import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;

import java.util.List;

public interface SecondAppService {
    List<DriverDto> findAllDrivers();

    List<UserOrderDto> findAllOrders();

    List<TruckDto> findAllTrucks();
}
