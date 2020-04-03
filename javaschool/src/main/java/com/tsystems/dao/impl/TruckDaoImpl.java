package com.tsystems.dao.impl;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.entity.Truck;
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
}
