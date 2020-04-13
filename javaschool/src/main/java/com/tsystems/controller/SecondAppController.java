package com.tsystems.controller;

import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.api.SecondAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecondAppController {

    @Autowired
    private SecondAppService secondAppService;

    /**
     * Returns all drivers
     *
     * @return List<DriverDto>
     */
    @GetMapping(value = "secondapp/drivers", produces = "application/json;charset=utf8")
    public List<DriverDto> getAllDrivers() {
        return secondAppService.findAllDrivers();
    }

    /**
     * Returns all orders
     *
     * @return List<UserOrderDto>
     */
    @GetMapping(value = "secondapp/orders", produces = "application/json;charset=utf8")
    public List<UserOrderDto> getAllSubOrders() {
        return secondAppService.findAllOrders();
    }

    /**
     * Returns all trucks
     *
     * @return List<TruckDto>
     */
    @GetMapping(value = "secondapp/trucks", produces = "application/json;charset=utf8")
    public List<TruckDto> getAllCars() {
        return secondAppService.findAllTrucks();
    }
}
