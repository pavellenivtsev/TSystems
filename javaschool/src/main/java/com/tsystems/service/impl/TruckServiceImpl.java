package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dto.CargoDto;
import com.tsystems.dto.LocationDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Location;
import com.tsystems.entity.Truck;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.TruckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {
    private static final Logger LOGGER = LogManager.getLogger(TruckServiceImpl.class);

    private final TruckDao truckDao;

    private final LocationDao locationDao;

    private final DriverDao driverDao;

    private final ModelMapper modelMapper;

    private final CountingService countingService;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao, LocationDao locationDao, DriverDao driverDao, ModelMapper modelMapper, CountingService countingService) {
        this.truckDao = truckDao;
        this.locationDao = locationDao;
        this.driverDao = driverDao;
        this.modelMapper = modelMapper;
        this.countingService = countingService;
    }

    /**
     * Finds all trucks
     *
     * @return List<TruckDto>
     */
    @Override
    @Transactional
    public List<TruckDto> findAll() {
        List<Truck> trucks = truckDao.findAll();
        return trucks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new truck
     *
     * @param truckDto
     * @param locationCity
     * @param latitude
     * @param longitude
     * @return true if truck was added
     */
    @Override
    @Transactional
    public boolean save(TruckDto truckDto, String locationCity, double latitude, double longitude) {
        if (truckDao.findByRegistrationNumber(truckDto.getRegistrationNumber()) == null) {
            Truck truck = convertToEntity(truckDto);
            Location location = new Location();
            location.setCity(locationCity);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            locationDao.save(location);
            truck.setLocation(location);
            truck.setStatus(TruckStatus.ON_DUTY);
            truckDao.save(truck);

            LOGGER.info("Added a new truck with registration number " + truck.getRegistrationNumber());
            return true;
        }
        return false;
    }

    /**
     * Updates the truck
     *
     * @param truckDto
     * @param locationCity
     * @return true if truck was updated
     */
    @Override
    @Transactional
    public boolean update(TruckDto truckDto, String locationCity, double latitude, double longitude) {
        Truck truck = truckDao.findById(truckDto.getId());
        if (!truck.getUserOrderList().isEmpty()) {
            return false;
        }
        if (truckDao.findByRegistrationNumber(truckDto.getRegistrationNumber()) == null || truck.getRegistrationNumber().equals(truckDto.getRegistrationNumber())) {
            truck.setRegistrationNumber(truckDto.getRegistrationNumber());
            truck.setWeightCapacity(truckDto.getWeightCapacity());
            truck.setDriverShiftSize(truckDto.getDriverShiftSize());
            truck.setStatus(truckDto.getStatus());

            Location location = truck.getLocation();
            location.setCity(locationCity);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            locationDao.update(location);
            truckDao.update(truck);
            return true;
        }
        return false;
    }

    /**
     * Delete truck by id
     *
     * @param id
     * @return true if truck was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        Truck truck = truckDao.findById(id);
        if (!truck.getUserOrderList().isEmpty()) {
            return false;
        }

        locationDao.delete(truck.getLocation());
        truckDao.delete(truck);
        LOGGER.info("Deleted a truck with registration number " + truck.getRegistrationNumber());
        return true;
    }

    /**
     * Finds a truck by id
     *
     * @param id
     * @return TruckDto
     */
    @Override
    @Transactional
    public TruckDto findById(long id) {
        return convertToDto(truckDao.findById(id));
    }

    /**
     * Finds all available trucks for this order and sort and sorts them in order of increasing distance
     *
     * @param userOrderDto
     * @return List<TruckDto>
     */
    @Override
    @Transactional
    public List<TruckDto> findAllAvailable(UserOrderDto userOrderDto) {
        if (!(userOrderDto.getTruck() == null)) {
            return null;
        }
        List<Truck> trucks = truckDao.findAll();
        Iterator<Truck> iterator = trucks.iterator();
        Truck truck;
        while (iterator.hasNext()) {
            truck = iterator.next();

            //the condition of the truck should be normal
            if (truck.getStatus().equals(TruckStatus.FAULTY)) {
                iterator.remove();
                continue;
            }

            //the truck must not have any other orders
            if (!truck.getUserOrderList().isEmpty()) {
                iterator.remove();
                continue;
            }

            //the truck must have at least 1 driver
            if (truck.getDriverList().isEmpty()) {
                iterator.remove();
                continue;
            }

            //the load must not exceed the load capacity of the truck
            if (truck.getWeightCapacity() < getTotalWeight(userOrderDto.getCargoList())) {
                iterator.remove();
                continue;
            }

            //checking that the time limit of 176 hours per month will not be exceeded for any driver
            if (isLimitForDriversExceeded(truck, userOrderDto)) {
                iterator.remove();
            }
        }
        if (trucks.isEmpty()) {
            return null;
        }

        //sorting by distance
        trucks = sortByDistance(trucks, userOrderDto.getWaypointList().get(0).getLocation());
        return trucks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds the driver to the truck
     *
     * @param truckDto
     * @param driverId
     * @return true if driver was added
     */
    @Override
    @Transactional
    public boolean addDriver(TruckDto truckDto, long driverId) {
        Driver driver = driverDao.findById(driverId);

        //the truck does not fulfill the order and the driver does not have a car
        if (truckDto.getUserOrderList().isEmpty()) {
            if (driver.getTruck() == null) {
                driver.setTruck(convertToEntity(truckDto));
                driverDao.update(driver);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the driver from the truck
     *
     * @param truckDto
     * @param driverId
     * @return true if driver was removed
     */
    @Override
    @Transactional
    public boolean deleteDriver(TruckDto truckDto, long driverId) {
        Driver driver = driverDao.findById(driverId);

        // checks that truck don't have any orders
        if (truckDto.getUserOrderList() == null) {
            driver.setTruck(null);
            driverDao.update(driver);
            return true;
        }
        return false;
    }

    private TruckDto convertToDto(Truck truck) {
        return modelMapper.map(truck, TruckDto.class);
    }

    private Truck convertToEntity(TruckDto truckDto) {
        return modelMapper.map(truckDto, Truck.class);
    }

    /**
     * Calculate total weight for order
     *
     * @param cargoDtoList
     * @return weight in kilograms
     */
    private double getTotalWeight(List<CargoDto> cargoDtoList) {
        double totalWeight = 0.0;
        for (CargoDto cargoDto : cargoDtoList) {
            totalWeight += cargoDto.getWeight();
        }
        return totalWeight;
    }

    /**
     * Sorts the list of trucks by truck location and order place of departure
     *
     * @param trucks
     * @param locationDto
     * @return List<Truck>
     */
    private List<Truck> sortByDistance(List<Truck> trucks, LocationDto locationDto) {
        TreeMap<Integer, Truck> truckDistanceTreeMap = new TreeMap<>();

        int distance;
        for (Truck truck : trucks) {
            distance = countingService.getDistanceLength(truck.getLocation().getLatitude(),
                    truck.getLocation().getLongitude(),
                    locationDto.getLatitude(),
                    locationDto.getLongitude());
            truckDistanceTreeMap.put(distance, truck);
        }
        return new ArrayList<>(truckDistanceTreeMap.values());
    }

    /**
     * Checks that the drivers is approach for this order
     *
     * @param truck
     * @param userOrderDto
     * @return true if one of the drivers has exceeded the shift limit
     */
    private boolean isLimitForDriversExceeded(Truck truck, UserOrderDto userOrderDto) {
        List<Driver> drivers = truck.getDriverList();

        //the speed of the truck in kilometers per hour
        double truckSpeed = 40.0;
        double distanceBetweenTruckAndOrder = getDistanceBetweenTruckAndOrder(truck, userOrderDto);
        double hoursForOrder = (float) ((userOrderDto.getDistance() + distanceBetweenTruckAndOrder) / truckSpeed);

        double driverHoursThisMonth;
        for (Driver driver : drivers) {
            driverHoursThisMonth = driver.getHoursThisMonth();
            if ((driverHoursThisMonth + hoursForOrder) < 176) {
                return false;

                //checking if the month changes
            } else {
                DateTime currentDate = new DateTime();
                int month = currentDate.getMonthOfYear() + 1;
                int year = currentDate.getYear();
                if (month == 13) {
                    month = 1;
                    year++;
                }
                DateTime nextMonth = new DateTime(year, month, 1, 0, 0);
                Days days = Days.daysBetween(currentDate, nextMonth);
                double daysToEndThisMonth = days.getDays();

                //even if the driver goes out to order every day
                double maxHoursInThisMonth = daysToEndThisMonth * truck.getDriverShiftSize();
                return (driverHoursThisMonth + maxHoursInThisMonth) > 176;
            }
        }
        return true;
    }

    /**
     * Calculates the distance between the truck and the order
     *
     * @param truck
     * @param userOrderDto
     * @return distance in km
     */
    private double getDistanceBetweenTruckAndOrder(Truck truck, UserOrderDto userOrderDto) {
        return countingService.getDistanceLength(truck.getLocation().getLatitude(),
                truck.getLocation().getLongitude(),
                userOrderDto.getWaypointList().get(0).getLocation().getLatitude(),
                userOrderDto.getWaypointList().get(0).getLocation().getLongitude());
    }
}
