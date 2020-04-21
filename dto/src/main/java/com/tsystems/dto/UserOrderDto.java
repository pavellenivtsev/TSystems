package com.tsystems.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tsystems.enumaration.UserOrderStatus;
import lombok.*;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope=UserOrderDto.class)
@Getter
@Setter
@NoArgsConstructor
public class UserOrderDto implements Serializable {

    private long id;

    private UserOrderStatus status;

    private String uniqueNumber;

    private double distance;

    @JsonIgnore
    private DateTime creationDate;

    @JsonIgnore
    private TruckDto truck;

    private List<CargoDto> cargoList;
}