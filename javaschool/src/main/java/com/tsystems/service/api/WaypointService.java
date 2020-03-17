package com.tsystems.service.api;

import com.tsystems.dto.UserOrderDto;
import com.tsystems.dto.WaypointDto;

import java.util.List;

public interface WaypointService {
    List<WaypointDto> findAll();

    void save(WaypointDto waypointDto);

    void update(WaypointDto waypointDto);

    void delete(WaypointDto waypointDto);

    WaypointDto findById(long id);

    void deleteById(long id);
}
