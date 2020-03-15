package com.tsystems.dto;

import java.io.Serializable;
import java.util.List;

public class UserOrderDto implements Serializable {

    private long id;

    private String status;

    private List<DriverDto> driverDtoList;

    private List<TruckDto> truckDtoList;

    private List<WaypointDto> waypointDtoList;

}