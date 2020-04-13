package com.tsystems.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfficeDto {
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private List<TruckDto> truckList;
}
