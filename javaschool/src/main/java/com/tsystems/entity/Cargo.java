package com.tsystems.entity;

import com.tsystems.enumaration.CargoStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cargo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Cargo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CargoStatus status;

    @Column(name = "loading_address")
    private String loadingAddress;

    @Column(name = "loading_latitude")
    private double loadingLatitude;

    @Column(name = "loading_longitude")
    private double loadingLongitude;

    @Column(name = "unloading_address")
    private String unloadingAddress;

    @Column(name = "unloading_latitude")
    private double unloadingLatitude;

    @Column(name = "unloading_longitude")
    private double unloadingLongitude;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;
}