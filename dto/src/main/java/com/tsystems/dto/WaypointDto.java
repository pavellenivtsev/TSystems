package com.tsystems.dto;

import com.tsystems.enumaration.WaypointType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class WaypointDto implements Serializable {

    private long id;

    private WaypointType type;

    private LocationDto location;

    private CargoDto cargo;

}