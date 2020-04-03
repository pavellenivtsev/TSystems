package com.tsystems.entity;

import com.tsystems.enumaration.WaypointType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "waypoint")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Waypoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private WaypointType type;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;


}