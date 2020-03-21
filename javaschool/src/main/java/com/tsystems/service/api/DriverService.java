package com.tsystems.service.api;

import com.tsystems.dto.DriverDto;

import java.util.List;

public interface DriverService {
    List<DriverDto> findAll();

    void save(DriverDto driverDto);

    void update(DriverDto driverDto);

    void deleteById(long id);

    DriverDto findById(long id);

    List<DriverDto> findAllAvailable();

}
