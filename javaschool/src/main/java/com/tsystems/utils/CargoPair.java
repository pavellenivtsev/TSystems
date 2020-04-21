package com.tsystems.utils;

import com.tsystems.dto.CargoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoPair {
    private CargoDto cargoDto;

    private int distanceToCurrentPoint;

    private boolean isLoaded;

    private boolean isUnloaded;

    public CargoPair() {
    }

    public CargoPair(CargoDto cargoDto){
        this.cargoDto=cargoDto;
    }

    public double getLoadingLatitude(){
       return cargoDto.getLoadingLatitude();
    }

    public double getLoadingLongitude(){
        return cargoDto.getLoadingLongitude();
    }

    public double getUnloadingLatitude(){
        return cargoDto.getUnloadingLatitude();
    }

    public double getUnloadingLongitude(){
        return cargoDto.getUnloadingLongitude();
    }

    public double getWeight(){
        return cargoDto.getWeight();
    }
}
