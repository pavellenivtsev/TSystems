package com.tsystems.service.impl;

import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.impl.LocationDaoImpl;
import com.tsystems.dao.impl.TruckDaoImpl;
import com.tsystems.dto.TruckDto;
import com.tsystems.entity.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.tsystems.entity.Truck;
import com.tsystems.service.api.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {
    private static final Logger logger = LogManager.getLogger(TruckServiceImpl.class);

    private TruckDao truckDao = new TruckDaoImpl();

    private LocationDao locationDao = new LocationDaoImpl();

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    @Transactional
    public List<TruckDto> findAll() {
        List<Truck> trucks = truckDao.findAll();
        List<TruckDto> trucksDto = trucks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return trucksDto;
    }

    @Override
    @Transactional
    public void save(TruckDto truckDto, String locationCity) {
        Truck truck =convertToEntity(truckDto);
        Location location=new Location();
        location.setCity(locationCity);
        locationDao.save(location);
        truck.setLocation(location);
        truckDao.save(truck);
    }

    @Override
    @Transactional
    public void update(TruckDto truckDto, String locationCity) {
        Truck truck=truckDao.findById(truckDto.getId());
        truck.setRegistrationNumber(truckDto.getRegistrationNumber());
        truck.setWeightCapacity(truckDto.getWeightCapacity());
        truck.setDriverShiftSize(truckDto.getDriverShiftSize());
        truck.setStatus(truckDto.getStatus());

        Location location=truck.getLocation();
        location.setCity(locationCity);
        locationDao.update(location);
        truckDao.update(truck);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Truck truck = truckDao.findById(id);
        locationDao.delete(truck.getLocation());
        truckDao.delete(truck);
    }

    @Override
    @Transactional
    public TruckDto findById(long id) {
        return convertToDto(truckDao.findById(id));
    }

    private TruckDto convertToDto(Truck truck) {
        return modelMapper.map(truck, TruckDto.class);
    }

    private Truck convertToEntity(TruckDto truckDto) {
        return modelMapper.map(truckDto, Truck.class);
    }
}
