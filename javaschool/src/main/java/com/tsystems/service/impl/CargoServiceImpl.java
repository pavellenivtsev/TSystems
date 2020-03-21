package com.tsystems.service.impl;


import com.tsystems.dto.CargoDto;
import com.tsystems.service.api.CargoService;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService {
    @Override
    public void save(CargoDto cargoDto) {
//        Cargo cargo=new Cargo();
//        cargo.setName(cargoDto.getName());
//        cargo.setWeight(cargoDto.getWeight());
//        cargo.setStatus("PREPARED");
//        cargo.setUserOrder();
    }

    @Override
    public CargoDto findById(long id) {
        return null;
    }
}
