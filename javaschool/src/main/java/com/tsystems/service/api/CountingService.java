package com.tsystems.service.api;

import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;

import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;

import java.util.List;
import java.util.TreeMap;

public interface CountingService {
    /**
     * Converts coordinates to radians
     *
     * @param value
     * @return radians
     */
    double convertToRadians(double value);

    /**
     * Calculates distance between two coordinates
     *
     * @param latitudeFrom
     * @param longitudeFrom
     * @param latitudeTo
     * @param longitudeTo
     * @return integer kilometers of distance between two points
     */
    int getDistanceLength(double latitudeFrom,double longitudeFrom, double latitudeTo, double longitudeTo);

    /**
     * Calculates hours of working for driver
     *
     * @param driver
     * @return hours of working
     */
    double getDriverHours(Driver driver);

    /**
     * Calculates approximately distance for this truck and this order
     *
     * @param truckDtoList
     * @param userOrderDto
     * @return mapping for this truck and this order
     */
    TreeMap<Integer, TruckDto> getApproximatelyTotalDistanceForTruckAndOrder(List<TruckDto> truckDtoList, UserOrderDto userOrderDto);
}
