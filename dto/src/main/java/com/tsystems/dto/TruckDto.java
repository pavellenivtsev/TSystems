package com.tsystems.dto;

import com.tsystems.enumaration.TruckStatus;

import java.io.Serializable;
import java.util.List;

public class TruckDto implements Serializable {

    private long id;

    private String registrationNumber;

    private double driverShiftSize;

    private double weightCapacity;

    private TruckStatus status;

    private LocationDto location;

    private UserOrderDto userOrderDto;

    private List<DriverDto> driverDtoList;

    public TruckDto() {
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

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
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