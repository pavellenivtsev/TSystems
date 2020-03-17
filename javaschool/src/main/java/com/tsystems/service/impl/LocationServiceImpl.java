package com.tsystems.service.impl;

import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.impl.LocationDaoImpl;
import com.tsystems.entity.Location;
import com.tsystems.service.api.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private LocationDao locationDao=new LocationDaoImpl();

    @Autowired
    @Transactional
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    @Transactional
    public List<Location> findAll() {
        return locationDao.findAll();
    }

    @Override
    @Transactional
    public void save(Location location) {
        locationDao.save(location);
    }

    @Override
    @Transactional
    public void update(Location location) {
        locationDao.update(location);
    }

    @Override
    @Transactional
    public void delete(Location location) {
        locationDao.delete(location);
    }

    @Override
    @Transactional
    public Location findById(long id) {
        return locationDao.findById(id);
    }
}
