package com.tsystems.dto;

import com.tsystems.enumaration.CargoStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CargoDto implements Serializable {

    private long id;

    @NotBlank(message = "Name of material is absent")
    private String name;

    private double weight;

    private CargoStatus status;

    private List<WaypointDto> waypointList;

    private UserOrderDto userOrder;


}