package com.tsystems.dto;

import com.tsystems.enumaration.WaypointType;

import java.io.Serializable;

public class WaypointDto implements Serializable {

    private long id;

    private WaypointType type;

    private LocationDto location;

    private CargoDto cargo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WaypointType getType() {
        return type;
    }

    public void setType(WaypointType type) {
        this.type = type;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }
}