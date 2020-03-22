package com.tsystems.dto;

import com.tsystems.enumaration.DriverStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DriverDto implements Serializable{

    private long id;

    private Double hoursThisMonth;

    private String personalNumber;

    private DriverStatus status;

    private TruckDto truck;

    private UserOrderDto userOrder;

    private LocationDto location;

    private UserDto user;

}