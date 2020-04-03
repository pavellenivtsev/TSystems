package com.tsystems.dto;

import com.tsystems.enumaration.DriverStatus;
import lombok.*;
import org.joda.time.DateTime;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DriverDto implements Serializable{

    private long id;

    private Double hoursThisMonth;

    private String personalNumber;

    private DriverStatus status;

    private DateTime shiftStartTime;

    private TruckDto truck;

    private UserOrderDto userOrder;

    private UserDto user;

}