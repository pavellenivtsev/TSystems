package com.tsystems.dto;

import com.tsystems.enumaration.CargoStatus;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CargoDto implements Serializable {

    private long id;

    @NotBlank(message = "Name of material is absent")
    private String name;

    @NotNull
    private double weight;

    private CargoStatus status;

    @NotBlank
    private String loadingAddress;

    @NotNull
    private double loadingLatitude;

    @NotNull
    private double loadingLongitude;

    @NotBlank
    private String unloadingAddress;

    @NotNull
    private double unloadingLatitude;

    @NotNull
    private double unloadingLongitude;

    private UserOrderDto userOrder;
}