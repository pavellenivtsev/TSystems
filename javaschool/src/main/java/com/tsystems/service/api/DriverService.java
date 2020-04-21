package com.tsystems.service.api;

import com.tsystems.dto.DriverDto;

public interface DriverService {

    DriverDto findById(long id);

    DriverDto findByUsername(String username);

    boolean changeDriverStatusToOnShift(long id);

    boolean changeDriverStatusToRest(long id);

    boolean changeTruckStatusToOnDuty(long id);

    boolean changeTruckStatusToFaulty(long id);

    boolean completeOrder(long userOrderId);

    String getTruckJson(DriverDto driverDto);
}
