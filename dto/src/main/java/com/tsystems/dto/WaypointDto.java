package com.tsystems.dto;

import java.io.Serializable;
import java.util.List;

public class WaypointDto implements Serializable {

    private long id;

    private String type;

    private UserOrderDto userOrderDto;

    private List<LocationDto> locationDtoList;

    private List<CargoDto> cargoDtoList;

}