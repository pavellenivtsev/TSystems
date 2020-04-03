package com.tsystems.service.api;

import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;

import java.util.List;

public interface TruckService {
    List<TruckDto> findAll();

    boolean save(TruckDto truckDto, String locationCity, double latitude, double longitude);

    boolean update(TruckDto truckDto, String locationCity, double latitude, double longitude);

    boolean deleteById(long id);

    TruckDto findById(long id);

    List<TruckDto> findAllAvailable(UserOrderDto userOrderDto);

    boolean addDriver(TruckDto truckDto, long driverId);

    boolean deleteDriver(TruckDto truckDto, long driverId);
}
