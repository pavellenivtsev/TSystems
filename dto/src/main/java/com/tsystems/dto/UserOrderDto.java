package com.tsystems.dto;

import com.tsystems.enumaration.UserOrderStatus;

import java.io.Serializable;
import java.util.List;

public class UserOrderDto implements Serializable {

    private long id;

    private UserOrderStatus status;

    private String uniqueNumber;

    private List<DriverDto> driverList;

    private List<TruckDto> truckList;

    private List<CargoDto> cargoList;

    public UserOrderDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserOrderStatus getStatus() {
        return status;
    }

    public void setStatus(UserOrderStatus status) {
        this.status = status;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public List<DriverDto> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverDto> driverList) {
        this.driverList = driverList;
    }

    public List<TruckDto> getTruckList() {
        return truckList;
    }

    public void setTruckList(List<TruckDto> truckList) {
        this.truckList = truckList;
    }

    public List<CargoDto> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<CargoDto> cargoList) {
        this.cargoList = cargoList;
    }
}