package com.tsystems.service.api;

import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;

import java.util.List;

public interface DriverService {
    List<DriverDto> findAll();

    DriverDto findById(long id);

    List<DriverDto> findAllAvailable(TruckDto truckDto);

    DriverDto findByUsername(String username);

    boolean changeDriverStatusToOnShift(long id);

    boolean changeDriverStatusToRest(long id);

    boolean changeTruckStatusToOnDuty(long id);

    boolean changeTruckStatusToFaulty(long id);

    boolean completeOrder(long userOrderId);
}
