package com.tsystems.dao.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.entity.Driver;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDaoImpl extends AbstractGenericDao<Driver> implements DriverDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Driver> findAllAvailable() {
        return (List<Driver>) getSession()
                .createQuery("from Driver where truck=null")
                .list();
    }
}
