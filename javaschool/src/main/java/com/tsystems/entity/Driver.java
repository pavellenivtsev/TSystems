package com.tsystems.entity;

import com.tsystems.enumaration.DriverStatus;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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

    @Column(name = "shift_start_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime shiftStartTime;

    @ManyToOne
    @JoinColumn(name = "truck_id", referencedColumnName = "id")
    private Truck truck;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}