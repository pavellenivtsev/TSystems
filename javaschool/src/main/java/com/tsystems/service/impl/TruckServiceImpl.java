package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Location;
import com.tsystems.entity.Truck;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.service.api.TruckService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {
    private static final Logger log=Logger.getLogger(TruckServiceImpl.class);

    private final TruckDao truckDao;

    private final LocationDao locationDao;

    private final DriverDao driverDao;

    private final ModelMapper modelMapper;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao, LocationDao locationDao, DriverDao driverDao, ModelMapper modelMapper) {
        this.truckDao = truckDao;
        this.locationDao = locationDao;
        this.driverDao = driverDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<TruckDto> findAll() {
        List<Truck> trucks = truckDao.findAll();
        return trucks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(TruckDto truckDto, String locationCity) {
        Truck truck =convertToEntity(truckDto);
        Location location=new Location();
        location.setCity(locationCity);
        locationDao.save(location);
        truck.setLocation(location);
        truck.setStatus(TruckStatus.ON_DUTY);
        truckDao.save(truck);

        log.info("Added a new truck with registration number "+truck.getRegistrationNumber());
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

        log.info("Deleted a truck with registration number "+ truck.getRegistrationNumber());

        truckDao.delete(truck);
    }

    @Override
    @Transactional
    public TruckDto findById(long id) {
        return convertToDto(truckDao.findById(id));
    }

    @Override
    @Transactional
    public List<TruckDto> findAllAvailable() {

        //добавить проверку
        List<Truck> trucks = truckDao.findAll();
        List<TruckDto> trucksDto = trucks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return trucksDto;
    }

    @Override
    @Transactional
    public void addDriver(TruckDto truckDto, long driverId) {
        Truck truck=convertToEntity(truckDto);
        List<Driver> driverList=truck.getDriverList();
        Driver driver =driverDao.findById(driverId);
        driverList.add(driver);
        truck.setDriverList(driverList);
        truckDao.update(truck);
    }

    @Override
    @Transactional
    public void deleteDriver(TruckDto truckDto, long driverId) {
        Truck truck=convertToEntity(truckDto);
        List<Driver> driverList=truck.getDriverList();
        Driver driver =driverDao.findById(driverId);
        driverList.remove(driver);
        truck.setDriverList(driverList);
        truckDao.update(truck);
    }

    private TruckDto convertToDto(Truck truck) {
        return modelMapper.map(truck, TruckDto.class);
    }

    private Truck convertToEntity(TruckDto truckDto) {
        return modelMapper.map(truckDto, Truck.class);
    }
}
