package com.tsystems.dao.api;

import com.tsystems.entity.Truck;

import java.util.List;

public interface TruckDao extends GenericDao<Truck> {
    Truck findByRegistrationNumber(String registrationNumber);

    List<Truck> findAllAvailable();
}
