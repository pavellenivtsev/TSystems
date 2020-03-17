package com.tsystems.entity;

import com.tsystems.enumaration.UserOrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "user_order")
public class UserOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private UserOrderStatus status;

    @Column(name = "unique_number")
    private String uniqueNumber;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Driver> driverList;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Truck> truckList;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Cargo> cargoList;

    public UserOrder() {
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

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }
}