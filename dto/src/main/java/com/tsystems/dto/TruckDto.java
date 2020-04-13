package com.tsystems.dto;

import com.tsystems.enumaration.TruckStatus;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TruckDto implements Serializable {
    private long id;

    @NotBlank
    @Size(min = 7, message = "At least 7 characters")
    private String registrationNumber;

    @NotNull
    private double driverShiftSize;

    @NotNull
    private double weightCapacity;

    private TruckStatus status;

    private String address;

    private double latitude;

    private double longitude;

    private UserOrderDto userOrder;

    private List<DriverDto> driverList;

    private OfficeDto office;
}