package com.tsystems.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "driver")
public class Driver implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "hours_this_month")
    private Double hoursThisMonth;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id &&
                Objects.equals(hoursThisMonth, driver.hoursThisMonth) &&
                Objects.equals(status, driver.status) &&
                Objects.equals(truck, driver.truck) &&
                Objects.equals(userOrder, driver.userOrder) &&
                Objects.equals(location, driver.location) &&
                Objects.equals(user, driver.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoursThisMonth, status, truck, userOrder, location, user);
    }
}