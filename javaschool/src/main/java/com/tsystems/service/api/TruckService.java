package com.tsystems.service.api;

import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.utils.TruckPair;

import java.util.List;

public interface TruckService {

    boolean save(long officeId, TruckDto truckDto);

    boolean update(TruckDto truckDto);

    boolean deleteById(long id);

    TruckDto findById(long id);

    List<TruckPair> findAllAvailable(long orderId);

    boolean addOrder(long truckId, long orderId);

    List<DriverDto> findAllAvailableDrivers(long truckId);

    boolean removeDriver(long truckId, long driverId);

    boolean addDriver(long truckId, long driverId);
}
