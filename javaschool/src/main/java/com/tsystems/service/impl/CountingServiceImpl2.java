package com.tsystems.service.impl;

import com.google.common.util.concurrent.AtomicDouble;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.exception.TruckOverloadedException;
import com.tsystems.service.api.CountingService;
import com.tsystems.utils.CargoPair;
import com.tsystems.utils.CurrentPoint;
import com.tsystems.utils.TruckPair;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountingServiceImpl2 implements CountingService {
    public static final int EARTH_RADIUS = 6371;

    /**
     * Converts degrees to radians
     *
     * @param value - value to convert
     * @return converted value
     */
    public double convertToRadians(final double value) {
        return value * Math.PI / 180;
    }

    /**
     * Calculates the distance between two points
     *
     * @param latitudeFrom  - latitude from
     * @param longitudeFrom - longitude from
     * @param latitudeTo    - latitude to
     * @param longitudeTo   - longitude to
     * @return km of distance
     */
    @Override
    public int getDistanceLength(final double latitudeFrom, final double longitudeFrom,
                                 final double latitudeTo, final double longitudeTo) {
        final double latFrom = convertToRadians(latitudeFrom);
        final double lngFrom = convertToRadians(longitudeFrom);
        final double latTo = convertToRadians(latitudeTo);
        final double lngTo = convertToRadians(longitudeTo);

        //The haversine formula
        final double sin1 = Math.sin((latFrom - latTo) / 2);
        final double sin2 = Math.sin((lngFrom - lngTo) / 2);

        return (int) (2 * EARTH_RADIUS * Math.asin(Math.sqrt(sin1 * sin1 + sin2 * sin2 * Math.cos(latFrom) * Math.cos(latTo))));
    }

    /**
     * Calculates hours of working for driver
     *
     * @param driver - driver
     * @return hours of working
     */
    @Override
    public double getDriverHours(final @NonNull Driver driver) {
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
    public List<TruckPair> getApproximatelyTotalDistanceForTrucksAndOrder(final @NonNull List<TruckDto> truckDtoList,
                                                                          final @NonNull UserOrderDto userOrderDto) {
        return truckDtoList
                .stream()
                .map(truckDto -> getApproximatelyTotalDistanceTruckAndOrder(truckDto, userOrderDto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Defines the distance for the truck and this order
     * using the nearest neighbor algorithm
     * and checks whether the weight is exceeded
     *
     * @param truckDto     - truck
     * @param userOrderDto - order
     * @return TruckPair with approximately total distance
     */
    private Optional<TruckPair> getApproximatelyTotalDistanceTruckAndOrder(final @NonNull TruckDto truckDto,
                                                                           final @NonNull UserOrderDto userOrderDto) {
        CurrentPoint currentPoint = new CurrentPoint();
        currentPoint.setLatitude(truckDto.getLatitude());
        currentPoint.setLongitude(truckDto.getLongitude());

        final Iterable<List<CargoPair>> cargoPairIterator = () -> new Iterator<List<CargoPair>>() {
            final List<CargoPair> cargoPairList = userOrderDto.getCargoList()
                    .stream().map(CargoPair::new)
                    .collect(Collectors.toList());

            @Override
            public boolean hasNext() {
                return cargoPairList.stream()
                        .anyMatch(cargoPair -> !cargoPair.isUnloaded());
            }

            @Override
            public List<CargoPair> next() {
                return cargoPairList
                        .stream().filter(cargoPair -> !cargoPair.isUnloaded())
                        .collect(Collectors.toList());
            }
        };
        final AtomicDouble totalWeight = new AtomicDouble(0.0);
        try {
            final int approximatelyTotalDistance = StreamSupport.stream(cargoPairIterator.spliterator(), false)
                    .mapToInt(cargoPairs -> {
                        if (truckDto.getWeightCapacity() < totalWeight.get()) {
                            //truck overloaded
                            throw new TruckOverloadedException();
                        }
                        final CargoPair nearestCargo = getNearestCargo.apply(currentPoint, cargoPairs);
                        if (nearestCargo.isLoaded()) {
                            nearestCargo.setUnloaded(true);
                            currentPoint.setLatitude(nearestCargo.getUnloadingLatitude());
                            currentPoint.setLongitude(nearestCargo.getUnloadingLongitude());
                            totalWeight.getAndAdd(-nearestCargo.getWeight());
                        } else {
                            nearestCargo.setLoaded(true);
                            currentPoint.setLatitude(nearestCargo.getLoadingLatitude());
                            currentPoint.setLongitude(nearestCargo.getLoadingLongitude());
                            totalWeight.getAndAdd(+nearestCargo.getWeight());
                        }
                        return nearestCargo.getDistanceToCurrentPoint();
                    })
                    .sum();

            //adds distance between the last unloading point and the office
            final int distanceToOffice = getDistanceLength(
                    currentPoint.getLatitude(),
                    currentPoint.getLongitude(),
                    truckDto.getLatitude(),
                    truckDto.getLongitude()
            );
            return Optional.of(new TruckPair(truckDto, approximatelyTotalDistance + distanceToOffice));
        } catch (TruckOverloadedException e) {
            return Optional.empty();
        }
    }

    /**
     * Defines the distance between the current point and the cargo if the cargo is loaded
     */
    private final BiFunction<CurrentPoint, CargoPair, Integer> setIfLoaded
            = ((currentPoint, cargoPair) -> getDistanceLength(
            currentPoint.getLatitude(),
            currentPoint.getLongitude(),
            cargoPair.getUnloadingLatitude(),
            cargoPair.getUnloadingLongitude()));

    /**
     * Defines the distance between the current point and the cargo if the cargo is not loaded
     */
    private final BiFunction<CurrentPoint, CargoPair, Integer> setIfNotLoaded
            = ((currentPoint, cargoPair) -> getDistanceLength(
            currentPoint.getLatitude(),
            currentPoint.getLongitude(),
            cargoPair.getLoadingLatitude(),
            cargoPair.getLoadingLongitude()));

    /**
     * Determine the nearest cargo depending on whether it is loaded
     */
    private final BiFunction<CurrentPoint, List<CargoPair>, CargoPair> getNearestCargo
            = (point, cargoPairs) -> cargoPairs
            .stream().peek(cargoPair -> cargoPair.setDistanceToCurrentPoint(cargoPair.isLoaded()
                    ? setIfLoaded.apply(point, cargoPair)
                    : setIfNotLoaded.apply(point, cargoPair)))
            .reduce((a, b) -> a.getDistanceToCurrentPoint() > b.getDistanceToCurrentPoint()
                    ? b : a)
            .orElseThrow(() -> new IllegalArgumentException("Cargo should not be empty"));

    /**
     * Calculates hours of working for driver
     *
     * @param shiftStartTime - start shift size
     * @return hours of working
     */
    private double calculateTime(final @NonNull DateTime shiftStartTime) {
        final DateTime currentDate = new DateTime();
        final Seconds seconds = Seconds.secondsBetween(shiftStartTime, currentDate);
        final double secondsDouble = seconds.getSeconds();
        return secondsDouble / 3600;
    }
}

