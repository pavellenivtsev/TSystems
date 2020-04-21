package com.tsystems.service.api;

import com.tsystems.dto.EntryDto;
import com.tsystems.dto.UserOrderDto;

import java.util.List;

public interface SecondAppService {
    List<UserOrderDto> findAllCompletedOrCarriedOrders();

    List<EntryDto> createCountTable();
}
