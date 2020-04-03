package com.tsystems.service.api;

import com.tsystems.dto.CargoDto;

public interface CargoService {

    boolean addCargoToOrder(CargoDto cargoDto, long orderId);

    CargoDto findById(long id);

    boolean editCargo(CargoDto cargoDto, long orderId);

    boolean deleteCargo(long cargoId, long orderId);

    boolean setStatusDelivered(long id);

    boolean setStatusShipped(long id);
}
