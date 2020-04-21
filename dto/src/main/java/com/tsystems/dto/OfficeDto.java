package com.tsystems.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope=OfficeDto.class)
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
