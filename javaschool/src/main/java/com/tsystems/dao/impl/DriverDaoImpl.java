package com.tsystems.dao.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.entity.Driver;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDaoImpl extends AbstractGenericDao<Driver> implements DriverDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Driver> findAllDriversWithoutTruck() {
        return (List<Driver>) getSession()
                .createQuery("from Driver where truck=null")
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Driver findByPersonalNumber(String personalNumber) {
        List<Driver> driverList = getSession().createQuery("from Driver where personalNumber=?0")
                .setParameter(0, personalNumber)
                .list();
        return driverList.isEmpty() ? null : driverList.get(0);
    }
}
