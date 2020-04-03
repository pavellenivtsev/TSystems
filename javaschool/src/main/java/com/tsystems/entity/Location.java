package com.tsystems.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "location")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private  List<Waypoint> waypointList;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Truck> truckList;

}