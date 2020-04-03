package com.tsystems.dto;

import com.tsystems.enumaration.UserOrderStatus;
import lombok.*;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserOrderDto implements Serializable {

    private long id;

    private UserOrderStatus status;

    private String uniqueNumber;

    private double distance;

    private DateTime creationDate;

    private List<DriverDto> driverList;

    private TruckDto truck;

    private List<CargoDto> cargoList;

    private List<WaypointDto> waypointList;

}