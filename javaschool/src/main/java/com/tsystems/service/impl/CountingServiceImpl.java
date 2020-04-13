package com.tsystems.service.impl;

import com.tsystems.dto.CargoDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.service.api.CountingService;
import com.tsystems.utils.CargoPair;
import com.tsystems.utils.CurrentPoint;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
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
     * @param latitudeFrom
     * @param longitudeFrom
     * @param latitudeTo
     * @param longitudeTo
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
     * @param driver
     * @return hours of working
     */
    public double getDriverHours(Driver driver) {
        double hoursWorked = calculateTime(driver.getShiftStartTime());
        double maxHoursWorked = driver.getTruck().getDriverShiftSize();
        return Math.min(maxHoursWorked, hoursWorked);
    }

    /**
     * Calculates hours of working for driver
     *
     * @param shiftStartTime
     * @return hours of working
     */
    private double calculateTime(DateTime shiftStartTime) {
        DateTime currentDate = new DateTime();
        Seconds seconds = Seconds.secondsBetween(shiftStartTime, currentDate);
        double secondsDouble = seconds.getSeconds();
        return secondsDouble / 3600;
    }

    /**
     * Calculates approximately distance for this truck and this order
     *
     * @param truckDtoList
     * @param userOrderDto
     * @return mapping for this truck and this order
     */
    @Override
    public TreeMap<Integer, TruckDto> getApproximatelyTotalDistanceForTruckAndOrder(List<TruckDto> truckDtoList, UserOrderDto userOrderDto) {
        TreeMap<Integer, TruckDto> truckTreeMap = new TreeMap<>();
        List<CargoPair> cargoPairs = userOrderDto.getCargoList().stream().map(CargoPair::new).collect(Collectors.toList());
        final int[] approximatelyTotalDistance = {0};
        CurrentPoint currentPoint = new CurrentPoint();
        truckDtoList.forEach(truckDto -> {
            currentPoint.setLatitude(truckDto.getLatitude());
            currentPoint.setLongitude(truckDto.getLongitude());
            CargoPair minDistanceCargo = new CargoPair();

            while (!cargoPairs.isEmpty()) {
                for (CargoPair cargoPair : cargoPairs) {
                    if (cargoPair.isUnloaded()) {                              //доюавить проверку на массу груза
                        cargoPairs.remove(cargoPair);
                        continue;
                    }
                    if (cargoPair.isLoaded()) {
                        cargoPair.setDistanceToCurrentPoint(getDistanceLength(
                                currentPoint.getLatitude(),
                                currentPoint.getLongitude(),
                                cargoPair.getUnloadingLatitude(),
                                cargoPair.getUnloadingLongitude()
                        ));
                    }
                    if (!cargoPair.isLoaded()){
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
                approximatelyTotalDistance[0] += minDistanceCargo.getDistanceToCurrentPoint();
                if (minDistanceCargo.isLoaded()){
                    minDistanceCargo.setUnloaded(true);
                    currentPoint.setLatitude(minDistanceCargo.getUnloadingLatitude());
                    currentPoint.setLongitude(minDistanceCargo.getUnloadingLongitude());
                    minDistanceCargo.setDistanceToCurrentPoint(0);
                }
                if (!minDistanceCargo.isLoaded()){
                    minDistanceCargo.setLoaded(true);
                    currentPoint.setLatitude(minDistanceCargo.getLoadingLatitude());
                    currentPoint.setLongitude(minDistanceCargo.getLoadingLongitude());
                    minDistanceCargo.setDistanceToCurrentPoint(0);
                }
            }
            truckTreeMap.put(approximatelyTotalDistance[0], truckDto);
        });
        return truckTreeMap;
    }

    //            //for 1 loading
//            for (CargoPair cargoPair : cargoPairs) {
//                cargoPair.setDistanceToCurrentPoint(getDistanceLength(
//                        currentPoint.getLatitude(),
//                        currentPoint.getLongitude(),
//                        cargoPair.getLoadingLatitude(),
//                        cargoPair.getLoadingLongitude()
//                ));
//                if (minDistanceCargo.getDistanceToCurrentPoint() > cargoPair.getDistanceToCurrentPoint()) {
//                    minDistanceCargo = cargoPair;
//                }
//            }
//            approximatelyTotalDistance[0] += minDistanceCargo.getDistanceToCurrentPoint();
//            minDistanceCargo.setLoaded(true);
//            currentPoint.setLatitude(minDistanceCargo.getLoadingLatitude());
//            currentPoint.setLongitude(minDistanceCargo.getLoadingLongitude());
//            minDistanceCargo.setDistanceToCurrentPoint(0);
//
//            //for others points
    public static void main(String[] args) {
        CargoDto cargoDto = new CargoDto();
        System.out.println(cargoDto.getId());
        cargoDto.setName("cargo");
        List<CargoDto> cargoDtoList = new ArrayList<>();
        cargoDtoList.add(cargoDto);
        List<CargoPair> cargoPairs = cargoDtoList.stream().map(CargoPair::new).collect(Collectors.toList());
        for (CargoPair cargopair : cargoPairs) {
            System.out.println(cargopair.getCargoDto().getName());
        }

    }
}
//    public TreeMap<Integer, Truck> getApproximatelyTotalDistanceForTruckAndOrder(List<Truck> trucks, UserOrder userOrder) {
//        TreeMap<Integer, Truck> totalDistanceForTruckAndOrder = new TreeMap<>();
//        List<Cargo> cargoList = userOrder.getCargoList();
//
//        for (Truck truck : trucks) {
//            int totalDistance = 0;
//            int minDistanceBetweenLoadingCargoAndTruck;
//            int maxDistanceBetweenLoadingCargoAndTruck;
//            int maxDistanceBetweenUnloadingCargoAndTruck;
//            TreeMap<Integer, Cargo> distanceBetweenCargoAndTruckMap = new TreeMap<>();
//            int distance;
//
//            //for loading cargo
//            for (Cargo cargo : cargoList) {
//                distance = getDistanceLength(truck.getLocation(), cargo.getWaypointList().get(0).getLocation());
//                distanceBetweenCargoAndTruckMap.put(distance, cargo);
//            }
//            minDistanceBetweenLoadingCargoAndTruck = distanceBetweenCargoAndTruckMap.firstKey();
//            maxDistanceBetweenLoadingCargoAndTruck = distanceBetweenCargoAndTruckMap.lastKey();
//            distanceBetweenCargoAndTruckMap.clear();
//
//            //for unloading cargo
//            for (Cargo cargo : cargoList) {
//                distance = getDistanceLength(truck.getLocation(), cargo.getWaypointList().get(1).getLocation());
//                distanceBetweenCargoAndTruckMap.put(distance, cargo);
//            }
//            maxDistanceBetweenUnloadingCargoAndTruck = distanceBetweenCargoAndTruckMap.lastKey();
//            totalDistance += minDistanceBetweenLoadingCargoAndTruck;
//            int max = Math.max(maxDistanceBetweenUnloadingCargoAndTruck, maxDistanceBetweenLoadingCargoAndTruck);
//            totalDistance += max;
//            totalDistanceForTruckAndOrder.put(totalDistance, truck);
//        }
//        return totalDistanceForTruckAndOrder;
//    }

//    public TreeMap<Integer, Truck> getDistanceForTruckAndOrder(List<Truck> trucks, UserOrder userOrder) {
//        TreeMap<Integer, Truck> totalDistanceForTruckAndOrder = new TreeMap<>();

//        sorting trucks by distance to the nearest cargo
//        TreeMap<Integer, Truck> distanceForTruckAndNearestCargo=getDistanceForTruckAndNearestCargo(trucks,userOrder);

//        //loading cargo
//        List<Cargo> loadingCargoList = userOrder.getCargoList();
//
//        //loading
//        //sorting of cargo  by the distance to the truck
//        Map<Truck, List<Cargo>> truckSortedCargoMap = sortCargoByDistanceToTruck(loadingCargoList, trucks);
//
//        List<Cargo> cargos = new ArrayList<>();
//        for (Map.Entry<Truck, List<Cargo>> entry : truckSortedCargoMap.entrySet()) {
//            int totalDistance = 0;
//            Iterator<Cargo> cargoIterator = entry.getValue().iterator();
//            while (cargoIterator.hasNext()) {
//                Cargo cargo = cargoIterator.next();
//
//                //get distance between truck and nearest loading cargo
//                if (totalDistance == 0) {
//                    Location first = cargo.getWaypointList().get(0).getLocation();
//                    totalDistance += getDistanceLength(first, entry.getKey().getLocation());
//                    entry.getKey().setLocation(first);
//
//                }
//                Location from = cargo.getWaypointList().get(0).getLocation();
//                Location to = cargo.getWaypointList().get(1).getLocation();
//
//                //checking whether the loading or unloading is closer
//                if (checkWhatCloser(from, to, entry.getKey().getLocation())) {
//
//                } else {
//
//                }
//
//            }
//
//            totalDistanceForTruckAndOrder.put(totalDistance, entry.getKey());
//        }
//
//        //unloading cargo
//        List<Cargo> unloadingCargoList = userOrder.getCargoList();

//        //get all start points and end points
//        List<Location> startPoints = new ArrayList<>();
//        List<Location> endPoints = new ArrayList<>();
//        for (Cargo cargo:userOrder.getCargoList()){
//            startPoints.add(cargo.getWaypointList().get(0).getLocation());
//            endPoints.add(cargo.getWaypointList().get(1).getLocation());
//        }


//        for(Map.Entry<Integer,Truck> entry : distanceForTruckAndNearestCargo.entrySet()) {
//            int totalDistance=0;
//            totalDistance+=entry.getKey();
//            for (){
//
//            }
//            distanceForTruckAndOrder.put(totalDistance, entry.getValue());
//        }
//
//        return null;
//    }


//
//    //loading
//    public Map<Truck, List<Cargo>> sortCargoByDistanceToTruck(List<Cargo> cargoList, List<Truck> truckList) {
//        Map<Truck, List<Cargo>> truckCargoListMap = new HashMap<>();
//        for (Truck truck : truckList) {
//
//            //sort cargo by distance to truck
//            TreeMap<Integer, Cargo> distanceCargoTreeMap = new TreeMap<>();
//            for (Cargo cargo : cargoList) {
//                distanceCargoTreeMap.put(
//                        getDistanceLength(
//                                truck.getLocation(),
//                                cargo.getWaypointList().get(0).getLocation()),
//                        cargo);
//            }
//
//            //add sorted cargo list to truck-cargo list map
//            List<Cargo> sortedCargo = new ArrayList<>(distanceCargoTreeMap.values());
//            truckCargoListMap.put(truck, sortedCargo);
//        }
//        return truckCargoListMap;
//    }
//
//    //unloading
//    public Map<Truck, List<Cargo>> sortUnloadingCargoByDistanceToTruck(List<Cargo> cargoList, List<Truck> truckList) {
//        Map<Truck, List<Cargo>> truckCargoListMap = new HashMap<>();
//        for (Truck truck : truckList) {
//
//            //sort cargo by distance to truck
//            TreeMap<Integer, Cargo> distanceCargoTreeMap = new TreeMap<>();
//            for (Cargo cargo : cargoList) {
//                distanceCargoTreeMap.put(
//                        getDistanceLength(
//                                truck.getLocation(),
//                                cargo.getWaypointList().get(1).getLocation()),
//                        cargo);
//            }
//
//            //add sorted cargo list to truck-cargo list map
//            List<Cargo> sortedCargo = new ArrayList<>(distanceCargoTreeMap.values());
//            truckCargoListMap.put(truck, sortedCargo);
//        }
//        return truckCargoListMap;
//
//    }

//    public TreeMap<Integer, Truck> getDistanceForTruckAndNearestCargo(List<Truck> trucks, UserOrder userOrder){
//        TreeMap<Integer, Cargo> integerCargoTreeMap=new TreeMap<>();
//        TreeMap<Integer, Truck> integerTruckTreeMap=new TreeMap<>();
//        for (Truck truck : trucks) {
//            for (Cargo cargo : userOrder.getCargoList()) {
//                integerCargoTreeMap.put(
//                        getDistanceLength(cargo.getWaypointList().get(0).getLocation(),truck.getLocation()
//                        ),cargo);
//            }
//            integerTruckTreeMap.put(integerCargoTreeMap.firstKey(),truck);
//
//
//        }
//        return integerTruckTreeMap;
//    }
//
//    public List<Location> sortLocationByDistance(Location from, List<Location> locationList) {
//        List<Location> sortedLocationList = new ArrayList<>();
//        TreeMap<Integer, Location> distanceLocationTreeMap = new TreeMap<>();
//        for (Location location : locationList) {
//            distanceLocationTreeMap.put(getDistanceLength(from, location), location);
//        }
//
//        //add sorted locations to list
//        for (Map.Entry<Integer, Location> entry : distanceLocationTreeMap.entrySet()) {
//            sortedLocationList.add(entry.getValue());
//        }
//        return sortedLocationList;
//    }

//    public Cargo findNearestCargo(Map<Cargo,Integer> cargoIntegerMap){
//        Map.Entry<Cargo, Integer> min = null;
//        for (Map.Entry<Cargo, Integer> entry : cargoIntegerMap.entrySet()) {
//            if (min == null || min.getValue() > entry.getValue()) {
//                min = entry;
//            }
//        }
//        return null;
//    }
