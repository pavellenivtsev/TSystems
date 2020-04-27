package com.tsystems.dao.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.entity.Driver;
import com.tsystems.enumaration.DriverStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDaoImpl extends AbstractGenericDao<Driver> implements DriverDao {

    /**
     * Finds all drivers without truck
     *
     * @return List<Driver>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Driver> findAllDriversWithoutTruck() {
        return (List<Driver>) getSession()
                .createQuery("from Driver where truck=null")
                .list();
    }

    /**
     * Find driver by personal number
     *
     * @param personalNumber - personalNumber
     * @return Driver
     */
    @SuppressWarnings("unchecked")
    @Override
    public Driver findByPersonalNumber(String personalNumber) {
        List<Driver> driverList = getSession().createQuery("from Driver where personalNumber=?0")
                .setParameter(0, personalNumber)
                .list();
        return driverList.isEmpty() ? null : driverList.get(0);
    }

    /**
     * Returns the number of drivers
     *
     * @return all drivers count
     */
    @Override
    public Long getDriversCount() {
        return (Long) getSession().createQuery("select count(*) from Driver").list().get(0);
    }

    /**
     * Returns the number of rest drivers
     *
     * @return rest drivers count
     */
    @Override
    public Long getRestDriversCount() {
        return (Long) getSession().createQuery("select count(*) from Driver where status=?1")
                .setParameter(1, DriverStatus.REST)
                .list().get(0);
    }

    /**
     * Returns the number of drivers on shift
     *
     * @return on shift drivers count
     */
    @Override
    public Long getOnShiftDriversCount() {
        return (Long) getSession().createQuery("select count(*) from Driver where status=?1")
                .setParameter(1, DriverStatus.ON_SHIFT)
                .list().get(0);
    }
}
