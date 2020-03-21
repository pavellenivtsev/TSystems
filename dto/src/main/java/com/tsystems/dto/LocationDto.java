package com.tsystems.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationDto implements Serializable {

    private long id;

    private String city;

    private double latitude;

    private double longitude;

    private List<WaypointDto> waypointList;

    private List<DriverDto> driverList;

    private List<TruckDto> truckList;
}