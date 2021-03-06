package com.tsystems.dao.api;

import com.tsystems.entity.Driver;

import java.util.List;

public interface DriverDao extends GenericDao<Driver> {

    List<Driver> findAllDriversWithoutTruck();

    Driver findByPersonalNumber(String personalNumber);

    Long getDriversCount();

    Long getRestDriversCount();

    Long getOnShiftDriversCount();
}
