package com.tsystems.dto;

import com.tsystems.enumaration.TruckStatus;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TruckDto implements Serializable {

    private long id;

    private String registrationNumber;

    private double driverShiftSize;

    private double weightCapacity;

    private TruckStatus status;

    private LocationDto location;

    private List<UserOrderDto> userOrderList;

    private List<DriverDto> driverList;

}