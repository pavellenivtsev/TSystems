package com.tsystems.service.api;

import com.tsystems.dto.CargoDto;

public interface CargoService {

    boolean addCargoToOrder(CargoDto cargoDto, long orderId);

    CargoDto findById(long id);

    boolean editCargo(CargoDto cargoDto);

    boolean deleteCargo(long cargoId);

    boolean setStatusDelivered(long id);

    boolean setStatusShipped(long id);
}
