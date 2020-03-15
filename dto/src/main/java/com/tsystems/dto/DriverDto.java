package com.tsystems.dto;

import java.io.Serializable;

public class DriverDto implements Serializable{

    private long id;

    private Double hoursThisMonth;

    private String status;

    private TruckDto truckDto;

    private UserOrderDto userOrderDto;

    private LocationDto locationDto;

    private UserDto userDto;


}