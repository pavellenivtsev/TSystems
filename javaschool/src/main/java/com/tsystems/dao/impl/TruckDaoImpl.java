package com.tsystems.dao.impl;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.entity.Truck;
import com.tsystems.enumaration.TruckStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDaoImpl extends AbstractGenericDao<Truck> implements TruckDao {

    @SuppressWarnings("unchecked")
    @Override
    public Truck findByRegistrationNumber(String registrationNumber) {
        List<Truck> truckList = getSession()
                .createQuery("from Truck where registrationNumber=?0")
                .setParameter(0, registrationNumber)
                .list();
        return truckList.isEmpty() ? null : truckList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Truck> findAllAvailable() {
        return (List<Truck>) getSession()
                .createQuery("select truck from Truck truck where truck.driverList is not empty and truck.status=?1")
                .setParameter(1, TruckStatus.ON_DUTY)
                .list();
    }
}
