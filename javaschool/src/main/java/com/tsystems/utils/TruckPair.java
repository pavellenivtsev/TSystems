package com.tsystems.utils;

import com.tsystems.dto.TruckDto;
import lombok.*;

/**
 * Used for sorting trucks by distance and for implementing the nearest neighbor algorithm
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TruckPair implements Comparable<TruckPair> {
    private TruckDto truckDto;

    int approximatelyTotalDistanceForTruckAndOrder;

    public TruckPair(TruckDto truckDto) {
        this.truckDto = truckDto;
    }

    @Override
    public int compareTo(TruckPair truckPair) {
        return this.approximatelyTotalDistanceForTruckAndOrder - truckPair.approximatelyTotalDistanceForTruckAndOrder;
    }
}
