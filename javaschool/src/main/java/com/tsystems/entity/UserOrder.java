package com.tsystems.entity;

import com.tsystems.enumaration.UserOrderStatus;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "user_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
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

    @Column(name = "distance")
    private double distance;

    @Column(name = "creation_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationDate;

    @OneToOne
    @JoinColumn(name = "truck_id", referencedColumnName = "id")
    private Truck truck;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<Cargo> cargoList;

}