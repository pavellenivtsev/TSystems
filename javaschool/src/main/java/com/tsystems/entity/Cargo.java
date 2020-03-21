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
@ToString
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

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    private List<Waypoint> waypointList;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

}