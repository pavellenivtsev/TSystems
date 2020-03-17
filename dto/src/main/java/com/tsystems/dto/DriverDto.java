package com.tsystems.dto;

import com.tsystems.enumaration.DriverStatus;

import java.io.Serializable;

public class DriverDto implements Serializable{

    private long id;

    private Double hoursThisMonth;

    private DriverStatus status;

    private TruckDto truck;

    private UserOrderDto userOrder;

    private LocationDto location;

    private UserDto user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getHoursThisMonth() {
        return hoursThisMonth;
    }

    public void setHoursThisMonth(Double hoursThisMonth) {
        this.hoursThisMonth = hoursThisMonth;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }

    public UserOrderDto getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrderDto userOrder) {
        this.userOrder = userOrder;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}