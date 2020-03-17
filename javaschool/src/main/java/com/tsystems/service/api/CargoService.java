package com.tsystems.service.api;

import com.tsystems.dto.CargoDto;

public interface CargoService {
    void save(CargoDto cargoDto);
    CargoDto findById(long id);
}
