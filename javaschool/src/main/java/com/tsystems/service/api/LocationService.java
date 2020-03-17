package com.tsystems.service.api;

import com.tsystems.entity.Location;

import java.util.List;

public interface LocationService {
    List<Location> findAll();

    void save(Location location);

    void update(Location location);

    void delete(Location location);

    Location findById(long id);
}
