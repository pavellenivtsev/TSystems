package com.tsystems.utils;

import com.tsystems.dto.TruckDto;
import lombok.*;

/**
 * Used for sorting trucks by distance and for implementing the nearest neighbor algorithm
 */
@Value
public class TruckPair implements Comparable<TruckPair> {
    private final TruckDto truckDto;
    private final int approximatelyTotalDistanceForTruckAndOrder;

    @Override
    public int compareTo(TruckPair truckPair) {
        return this.approximatelyTotalDistanceForTruckAndOrder - truckPair.approximatelyTotalDistanceForTruckAndOrder;
    }
}
