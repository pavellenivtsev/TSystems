package com.tsystems.service.api;

import com.tsystems.dto.TruckDto;

import java.util.List;

public interface TruckService {
    List<TruckDto> findAll();
    void save(TruckDto truckDto);
    void update(TruckDto truckDto);
    void delete(TruckDto truckDto);
    TruckDto findById(long id);
}
