package com.tsystems.dao.impl;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.entity.Truck;
import com.tsystems.enumaration.TruckStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDaoImpl extends AbstractGenericDao<Truck> implements TruckDao {

    /**
     * Finds truck by registration number
     *
     * @param registrationNumber - registration number
     * @return Truck
     */
    @SuppressWarnings("unchecked")
    @Override
    public Truck findByRegistrationNumber(String registrationNumber) {
        List<Truck> truckList = getSession()
                .createQuery("from Truck where registrationNumber=?0")
                .setParameter(0, registrationNumber)
                .list();
        return truckList.isEmpty() ? null : truckList.get(0);
    }

    /**
     * Finds all trucks that have at least 1 driver and a working status
     *
     * @return List<Truck>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Truck> findAllAvailable() {
        return (List<Truck>) getSession()
                .createQuery("select truck from Truck truck where truck.driverList is not empty and truck.status=?1")
                .setParameter(1, TruckStatus.ON_DUTY)
                .list();
    }

    /**
     * Returns the number of trucks
     *
     * @return all trucks count
     */
    @Override
    public Long getTrucksCount() {
        return (Long) getSession().createQuery("select count(*) from Truck").list().get(0);
    }

    /**
     * Returns the number of trucks that complete the order
     *
     * @return count of trucks that complete the order
     */
    @SuppressWarnings("unchecked")
    @Override
    public Long getCarryingOrderTrucksCount() {
        return (Long) getSession().createQuery("select count(*) from UserOrder where truck is not null").list().get(0);
    }

    /**
     * Returns the number of faulty trucks
     *
     * @return faulty trucks count
     */
    @Override
    public Long getFaultyTrucksCount() {
        return (Long) getSession().createQuery("select count(*) from Truck where status=?1")
                .setParameter(1, TruckStatus.FAULTY)
                .list().get(0);
    }
}
