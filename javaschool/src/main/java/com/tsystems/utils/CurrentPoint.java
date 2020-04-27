package com.tsystems.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Used for implementing the nearest neighbor algorithm
 * Displays the current point on the map
 */
@Getter
@Setter
public class CurrentPoint {
    private double latitude;

    private double longitude;
}
