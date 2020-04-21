package com.tsystems.controller;

import com.tsystems.dto.EntryDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.api.SecondAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecondAppController {

    @Autowired
    private SecondAppService secondAppService;

    /**
     * Returns all orders
     *
     * @return List<UserOrderDto>
     */
    @GetMapping(value = "secondapp/orders", produces = "application/json;charset=utf8")
    public List<UserOrderDto> getAllCompletedOrCarriedOrders() {
        return secondAppService.findAllCompletedOrCarriedOrders();
    }

    /**
     * Returns count table
     *
     * @return List<EntryDto>
     */
    @GetMapping(value = "secondapp/count-table", produces = "application/json;charset=utf8")
    public List<EntryDto> getCountTable(){
        return secondAppService.createCountTable();
    }
}
