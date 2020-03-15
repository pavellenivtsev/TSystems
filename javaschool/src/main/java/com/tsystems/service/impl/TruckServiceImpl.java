package com.tsystems.service.impl;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.impl.TruckDaoImpl;
import com.tsystems.dto.TruckDto;
import com.tsystems.entity.Truck;
import com.tsystems.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {
    private TruckDao truckDao = new TruckDaoImpl();

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    @Transactional
    public List<TruckDto> findAll() {
        List<Truck> truckList=truckDao.findAll();
        List<TruckDto> truckDtoList=new ArrayList<>();
        TruckDto truckDto=new TruckDto();
        for(Truck truck:truckList){
            truckDto.setId(truck.getId());
            truckDto.setRegistrationNumber(truck.getRegistrationNumber());
            truckDto.setWeightCapacity(truck.getWeightCapacity());
            truckDto.setDriverShiftSize(truck.getDriverShiftSize());
            truckDto.setStatus(truck.getStatus());
            truckDtoList.add(truckDto);
        }

        return truckDtoList;
    }

    @Override
    @Transactional
    public void save(TruckDto truckDto) {
        Truck truck=new Truck();
        truck.setRegistrationNumber(truckDto.getRegistrationNumber());
        truck.setDriverShiftSize(truckDto.getDriverShiftSize());
        truck.setWeightCapacity(truckDto.getWeightCapacity());
        truck.setStatus(truckDto.getStatus());
        truckDao.save(truck);
    }

    @Override
    @Transactional
    public void update(TruckDto truckDto) {
        Truck truck=truckDao.findById(truckDto.getId());
        truck.setRegistrationNumber(truckDto.getRegistrationNumber());
        truck.setDriverShiftSize(truckDto.getDriverShiftSize());
        truck.setWeightCapacity(truckDto.getWeightCapacity());
        truck.setStatus(truckDto.getStatus());
        truckDao.update(truck);
    }

    @Override
    @Transactional
    public void delete(TruckDto truckDto) {
        Truck truck=truckDao.findById(truckDto.getId());
        truckDao.delete(truck);
    }

    @Override
    @Transactional
    public TruckDto findById(long id) {
        Truck truck=truckDao.findById(id);
        TruckDto truckDto= new TruckDto(truck.getId(),truck.getRegistrationNumber(),truck.getDriverShiftSize(),truck.getWeightCapacity(),truck.getStatus());
        return truckDto;
    }
}
