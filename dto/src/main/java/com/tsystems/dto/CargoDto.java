package com.tsystems.dto;

import com.tsystems.enumaration.CargoStatus;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CargoDto implements Serializable {

    private long id;

    private String name;

    private double weight;

    private CargoStatus status;

    private List<WaypointDto> waypointList;

    private UserOrderDto userOrder;


}