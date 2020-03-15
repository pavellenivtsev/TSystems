package com.tsystems.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "truck")
public class Truck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "registration_number", length = 7)
    private String registrationNumber;

    @Column(name = "driver_shift_size")
    private double driverShiftSize;

    @Column(name = "weight_capacity")
    private double weightCapacity;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL)
    private List<Driver> driverList;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return id == truck.id &&
                Double.compare(truck.driverShiftSize, driverShiftSize) == 0 &&
                Double.compare(truck.weightCapacity, weightCapacity) == 0 &&
                Objects.equals(registrationNumber, truck.registrationNumber) &&
                Objects.equals(status, truck.status) &&
                Objects.equals(location, truck.location) &&
                Objects.equals(userOrder, truck.userOrder) &&
                Objects.equals(driverList, truck.driverList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber, driverShiftSize, weightCapacity, status, location, userOrder, driverList);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", driverShiftSize=" + driverShiftSize +
                ", weightCapacity=" + weightCapacity +
                ", status='" + status + '\'' +
                ", location=" + location +
                ", userOrder=" + userOrder +
                ", driverList=" + driverList +
                '}';
    }
}