package com.tsystems.dto;

import java.io.Serializable;
import java.util.List;

public class LocationDto implements Serializable {

    private long id;

    private String city;

    private double latitude;

    private double longitude;

    private List<WaypointDto> waypointList;

    private List<DriverDto> driverList;

    private List<TruckDto> truckList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<WaypointDto> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<WaypointDto> waypointList) {
        this.waypointList = waypointList;
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
}