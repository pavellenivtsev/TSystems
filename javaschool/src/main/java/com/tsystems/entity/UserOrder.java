package com.tsystems.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_order")
public class UserOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Driver> driverList;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Truck> truckList;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Waypoint> waypointList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public List<Truck> getTruckList() {
        return truckList;
    }

    public void setTruckList(List<Truck> truckList) {
        this.truckList = truckList;
    }

    public List<Waypoint> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrder userOrder = (UserOrder) o;
        return id == userOrder.id &&
                Objects.equals(status, userOrder.status) &&
                Objects.equals(driverList, userOrder.driverList) &&
                Objects.equals(truckList, userOrder.truckList) &&
                Objects.equals(waypointList, userOrder.waypointList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, driverList, truckList, waypointList);
    }
}