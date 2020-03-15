package com.tsystems.dto;

import java.io.Serializable;
import java.util.List;

public class LocationDto implements Serializable {

    private long id;

    private String city;

    private double latitude;

    private double longitude;

    private WaypointDto waypointDto;

    private List<DriverDto> driverDtoList;

    private List<TruckDto> truckDtoList;

}