package com.tsystems.dto;

import com.tsystems.enumaration.UserOrderStatus;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserOrderDto implements Serializable {

    private long id;

    private UserOrderStatus status;

    private String uniqueNumber;

    private List<DriverDto> driverList;

    private TruckDto truck;

    private List<CargoDto> cargoList;

}