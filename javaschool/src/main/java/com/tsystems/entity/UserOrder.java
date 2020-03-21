package com.tsystems.entity;

import com.tsystems.enumaration.UserOrderStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "user_order")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
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

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Cargo> cargoList;

}