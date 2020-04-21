package com.tsystems.dto;

import com.fasterxml.jackson.annotation.*;
import com.tsystems.enumaration.DriverStatus;
import lombok.*;
import org.joda.time.DateTime;

import java.io.Serializable;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = DriverDto.class)
@Getter
@Setter
@NoArgsConstructor
public class DriverDto implements Serializable {

    private long id;

    private Double hoursThisMonth;

    private String personalNumber;

    private DriverStatus status;

    @JsonIgnore
    private DateTime shiftStartTime;

    private TruckDto truck;

    private UserDto user;
}