package com.tsystems.service.api;

import com.tsystems.dto.TruckDto;
import java.util.List;

public interface TruckService {
    List<TruckDto> findAll();

    void save(TruckDto truckDto, String locationCity);

    void update(TruckDto truckDto, String locationCity);

    void deleteById(long id);

    TruckDto findById(long id);
}
