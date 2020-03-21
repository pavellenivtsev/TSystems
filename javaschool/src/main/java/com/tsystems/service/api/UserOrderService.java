package com.tsystems.service.api;

import com.tsystems.dto.UserOrderDto;


import java.util.List;

public interface UserOrderService {
    List<UserOrderDto> findAll();

    void save(UserOrderDto userOrderDto, String cargoName, double cargoWeight, String locationFromCity, String locationToCity);

    void update(UserOrderDto userOrderDto, String cargoName, double cargoWeight, String locationFromCity, String locationToCity);

    UserOrderDto findById(long id);

    void deleteById(long id);

    void addTruck(UserOrderDto userOrderDto, long id);
}
