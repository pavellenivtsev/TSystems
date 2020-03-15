package com.tsystems.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "waypoint")
public class Waypoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.ALL)
    private List<Location> locationList;

    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.ALL)
    private List<Cargo> cargoList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint waypoint = (Waypoint) o;
        return id == waypoint.id &&
                Objects.equals(type, waypoint.type) &&
                Objects.equals(userOrder, waypoint.userOrder) &&
                Objects.equals(locationList, waypoint.locationList) &&
                Objects.equals(cargoList, waypoint.cargoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, userOrder, locationList, cargoList);
    }
}