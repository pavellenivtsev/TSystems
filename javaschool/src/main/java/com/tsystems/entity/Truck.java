package com.tsystems.entity;

import com.tsystems.enumaration.TruckStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "truck")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private TruckStatus status;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL)
    private List<UserOrder> userOrderList;

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL)
    private List<Driver> driverList;

}