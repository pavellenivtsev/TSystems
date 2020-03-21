package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.entity.Driver;
import com.tsystems.service.api.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverDao driverDao;

    private final ModelMapper modelMapper;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao, ModelMapper modelMapper) {
        this.driverDao = driverDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<DriverDto> findAll() {
        List<Driver> drivers = driverDao.findAll();
        return drivers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(DriverDto driverDto) {

    }

    @Override
    @Transactional
    public void update(DriverDto driverDto) {

    }

    @Override
    @Transactional
    public void deleteById(long id) {

    }

    @Override
    @Transactional
    public DriverDto findById(long id) {
        Driver driver =driverDao.findById(id);
        return convertToDto(driver);
    }

    @Override
    @Transactional
    public List<DriverDto> findAllAvailable() {
        List<Driver> drivers = driverDao.findAllAvailable();
        return drivers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private DriverDto convertToDto(Driver driver) {
        return modelMapper.map(driver, DriverDto.class);
    }

    private Driver convertToEntity(DriverDto driverDto) {
        return modelMapper.map(driverDto, Driver.class);
    }
}
