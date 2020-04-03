package com.tsystems.dto;

import com.tsystems.enumaration.TruckStatus;
import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TruckDto implements Serializable {

    private long id;

    @Size(min = 7, message = "At least 7 characters")
    private String registrationNumber;

    private double driverShiftSize;

    private double weightCapacity;

    private TruckStatus status;

    private LocationDto location;

    private List<UserOrderDto> userOrderList;

    private List<DriverDto> driverList;

}