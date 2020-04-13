package com.tsystems.service.api;

import com.tsystems.dto.UserOrderDto;

import java.util.List;

public interface UserOrderService {
    List<UserOrderDto> findAllSortedByDate();

    UserOrderDto findById(long id);

    boolean deleteById(long id);

//    boolean addTruck(UserOrderDto userOrderDto, long truckId);

    boolean addOrder();
}
