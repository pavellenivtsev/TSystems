package com.tsystems.dto;

import com.tsystems.enumaration.CargoStatus;

import java.io.Serializable;
import java.util.List;

public class CargoDto implements Serializable {

    private long id;

    private String name;

    private double weight;

    private CargoStatus status;

    private List<WaypointDto> waypointList;

    private UserOrderDto userOrder;

    public CargoDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    public List<WaypointDto> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<WaypointDto> waypointList) {
        this.waypointList = waypointList;
    }

    public UserOrderDto getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrderDto userOrder) {
        this.userOrder = userOrder;
    }
}