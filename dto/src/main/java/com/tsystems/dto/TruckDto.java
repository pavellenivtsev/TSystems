package com.tsystems.dto;

import java.io.Serializable;
import java.util.List;

public class TruckDto implements Serializable {

    private long id;

    private String registrationNumber;

    private double driverShiftSize;

    private double weightCapacity;

    private String status;

    private LocationDto locationDto;

    private UserOrderDto userOrderDto;

    private List<DriverDto> driverDtoList;

    public TruckDto() {
    }

    public TruckDto(long id, String registrationNumber, double driverShiftSize, double weightCapacity, String status) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.driverShiftSize = driverShiftSize;
        this.weightCapacity = weightCapacity;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getDriverShiftSize() {
        return driverShiftSize;
    }

    public void setDriverShiftSize(double driverShiftSize) {
        this.driverShiftSize = driverShiftSize;
    }

    public double getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(double weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    public UserOrderDto getUserOrderDto() {
        return userOrderDto;
    }

    public void setUserOrderDto(UserOrderDto userOrderDto) {
        this.userOrderDto = userOrderDto;
    }

    public List<DriverDto> getDriverDtoList() {
        return driverDtoList;
    }

    public void setDriverDtoList(List<DriverDto> driverDtoList) {
        this.driverDtoList = driverDtoList;
    }
}