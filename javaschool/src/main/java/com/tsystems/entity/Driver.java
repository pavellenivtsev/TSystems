package com.tsystems.entity;

import com.tsystems.enumaration.DriverStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Driver implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "hours_this_month")
    private Double hoursThisMonth;

    @Column(name = "personal_number")
    private String personalNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private DriverStatus status;

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

}