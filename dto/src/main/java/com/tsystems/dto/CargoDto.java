package com.tsystems.dto;

import java.io.Serializable;

public class CargoDto implements Serializable {

    private long id;

    private String name;

    private double weight;

    private String status;

    private WaypointDto waypointDto;

}