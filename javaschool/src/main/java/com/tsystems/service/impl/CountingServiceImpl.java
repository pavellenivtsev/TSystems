package com.tsystems.service.impl;

import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.service.api.CountingService;
import com.tsystems.utils.CargoPair;
import com.tsystems.utils.CurrentPoint;
import com.tsystems.utils.TruckPair;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountingServiceImpl implements CountingService {
    public static final int EARTH_RADIUS = 6371;

    public double convertToRadians(double value) {
        return value * Math.PI / 180;
    }

    /**
     * Calculates the distance between two points
     *
     * @param latitudeFrom - latitude from
     * @param longitudeFrom - longitude from
     * @param latitudeTo - latitude to
     * @param longitudeTo - longitude to
     * @return km of distance
     */
    public int getDistanceLength(double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo) {
        double latFrom = convertToRadians(latitudeFrom);
        double lngFrom = convertToRadians(longitudeFrom);
        double latTo = convertToRadians(latitudeTo);
        double lngTo = convertToRadians(longitudeTo);

        //The haversine formula
        double sin1 = Math.sin((latFrom - latTo) / 2);
        double sin2 = Math.sin((lngFrom - lngTo) / 2);

        return (int) (2 * EARTH_RADIUS * Math.asin(Math.sqrt(sin1 * sin1 + sin2 * sin2 * Math.cos(latFrom) * Math.cos(latTo))));
    }

    /**
     * Calculates hours of working for driver
     *
     * @param driver - driver
     * @return hours of working
     */
    public double getDriverHours(Driver driver) {
        double hoursWorked = calculateTime(driver.getShiftStartTime());
        double maxHoursWorked = driver.getTruck().getDriverShiftSize();
        return Math.min(maxHoursWorked, hoursWorked);
    }

    /**
     * Calculates approximately distance for this truck and this order and checks whether the weight is exceeded
     *
     * @param truckDtoList - trucks
     * @param userOrderDto - order
     * @return mapping for this truck and this order
     */
    @Override
    public List<TruckPair> getApproximatelyTotalDistanceForTruckAndOrder(List<TruckDto> truckDtoList, UserOrderDto userOrderDto) {
        List<TruckPair> truckPairList = new ArrayList<>();
        CurrentPoint currentPoint = new CurrentPoint();

        Iterator<TruckDto> truckDtoIterator = truckDtoList.iterator();
        outerLoop:
        while (truckDtoIterator.hasNext()) {
            int approximatelyTotalDistance = 0;
            int totalCargoWeight = 0;
            TruckDto truckDto = truckDtoIterator.next();
            List<CargoPair> cargoPairs = userOrderDto.getCargoList().stream()
                    .map(CargoPair::new)
                    .collect(Collectors.toList());
            currentPoint.setLatitude(truckDto.getLatitude());
            currentPoint.setLongitude(truckDto.getLongitude());
            CargoPair minDistanceCargo = new CargoPair();
            minDistanceCargo.setDistanceToCurrentPoint(Integer.MAX_VALUE);

            do {
                if (truckDto.getWeightCapacity() < totalCargoWeight) {
                    truckDtoIterator.remove();
                    continue outerLoop;
                }

                //determining the nearest point
                Iterator<CargoPair> cargoPairIterator = cargoPairs.iterator();
                while (cargoPairIterator.hasNext()) {
                    CargoPair cargoPair = cargoPairIterator.next();
                    if (cargoPair.isUnloaded()) {
                        cargoPairIterator.remove();
                        continue;
                    }
                    if (cargoPair.isLoaded()) {
                        cargoPair.setDistanceToCurrentPoint(getDistanceLength(
                                currentPoint.getLatitude(),
                                currentPoint.getLongitude(),
                                cargoPair.getUnloadingLatitude(),
                                cargoPair.getUnloadingLongitude()
                        ));
                    } else {
                        cargoPair.setDistanceToCurrentPoint(getDistanceLength(
                                currentPoint.getLatitude(),
                                currentPoint.getLongitude(),
                                cargoPair.getLoadingLatitude(),
                                cargoPair.getLoadingLongitude()
                        ));
                    }
                    if (minDistanceCargo.getDistanceToCurrentPoint() > cargoPair.getDistanceToCurrentPoint()) {
                        minDistanceCargo = cargoPair;
                    }
                }
                if (cargoPairs.isEmpty()) {
                    minDistanceCargo.setDistanceToCurrentPoint(Integer.MAX_VALUE);
                    break;
                }
                approximatelyTotalDistance += minDistanceCargo.getDistanceToCurrentPoint();
                if (minDistanceCargo.isLoaded()) {
                    minDistanceCargo.setUnloaded(true);
                    totalCargoWeight -= minDistanceCargo.getWeight();
                    currentPoint.setLatitude(minDistanceCargo.getUnloadingLatitude());
                    currentPoint.setLongitude(minDistanceCargo.getUnloadingLongitude());
                } else {
                    minDistanceCargo.setLoaded(true);
                    totalCargoWeight += minDistanceCargo.getWeight();
                    currentPoint.setLatitude(minDistanceCargo.getLoadingLatitude());
                    currentPoint.setLongitude(minDistanceCargo.getLoadingLongitude());
                }
                minDistanceCargo.setDistanceToCurrentPoint(Integer.MAX_VALUE);
            } while (!cargoPairs.isEmpty());

            //adds distance between the last unloading point and the offi`ce
            approximatelyTotalDistance += getDistanceLength(
                    currentPoint.getLatitude(),
                    currentPoint.getLongitude(),
                    truckDto.getLatitude(),
                    truckDto.getLongitude()
            );
            truckPairList.add(new TruckPair(truckDto, approximatelyTotalDistance));
        }
        return truckPairList;
    }

    /**
     * Calculates hours of working for driver
     *
     * @param shiftStartTime - start shift size
     * @return hours of working
     */
    private double calculateTime(DateTime shiftStartTime) {
        DateTime currentDate = new DateTime();
        Seconds seconds = Seconds.secondsBetween(shiftStartTime, currentDate);
        double secondsDouble = seconds.getSeconds();
        return secondsDouble / 3600;
    }
}