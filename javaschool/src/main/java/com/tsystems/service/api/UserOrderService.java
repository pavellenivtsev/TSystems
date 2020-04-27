package com.tsystems.service.api;

import com.tsystems.dto.UserOrderDto;

import java.util.List;

public interface UserOrderService {
    List<UserOrderDto> findAllSortedByDate();

    UserOrderDto findById(long id);

    boolean deleteById(long id);

    boolean addOrder();

    List<UserOrderDto> findAllCompletedSortedByDate();

    List<UserOrderDto> findAllTakenSortedByDate();

    List<UserOrderDto> findAllNotTakenSortedByDate();
}
