package com.tsystems.service.api;

import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;

import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;
import com.tsystems.utils.TruckPair;

import java.util.List;
import java.util.TreeMap;

public interface CountingService {
    /**
     * Converts coordinates to radians
     *
     * @param value - value
     * @return radians
     */
    double convertToRadians(double value);

    /**
     * Calculates distance between two coordinates
     *
     * @param latitudeFrom - latitude from
     * @param longitudeFrom - longitude from
     * @param latitudeTo - latitude to
     * @param longitudeTo - longitude to
     * @return integer kilometers of distance between two points
     */
    int getDistanceLength(double latitudeFrom,double longitudeFrom, double latitudeTo, double longitudeTo);

    /**
     * Calculates hours of working for driver
     *
     * @param driver - driver
     * @return hours of working
     */
    double getDriverHours(Driver driver);

    /**
     * Calculates approximately distance for this truck and this order and checks whether the weight is exceeded
     *
     * @param truckDtoList - trucks
     * @param userOrderDto - order
     * @return mapping for this truck and this order
     */
    List<TruckPair> getApproximatelyTotalDistanceForTrucksAndOrder(List<TruckDto> truckDtoList, UserOrderDto userOrderDto);
}
