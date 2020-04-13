package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;

import com.tsystems.dao.api.OfficeDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Office;
import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.TruckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {
    private static final Logger LOGGER = LogManager.getLogger(TruckServiceImpl.class);

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CountingService countingService;

    /**
     * Saves a new truck
     *
     * @param truckDto truck
     * @return true if truck was added
     */
    @Override
    @Transactional
    public boolean save(long officeId, TruckDto truckDto) {
        Office office = officeDao.findById(officeId);
        if (truckDao.findByRegistrationNumber(truckDto.getRegistrationNumber()) == null) {
            Truck truck = modelMapper.map(truckDto, Truck.class);
            truck.setStatus(TruckStatus.ON_DUTY);
            truck.setOffice(office);
            truck.setAddress(office.getAddress());
            truck.setLatitude(office.getLatitude());
            truck.setLongitude(office.getLongitude());
            truckDao.save(truck);
            LOGGER.info("Added a new truck with registration number " + truck.getRegistrationNumber());
            return true;
        }
        return false;
    }

    /**
     * Updates the truck
     *
     * @param truckDto truck
     * @return true if truck was updated
     */
    @Override
    @Transactional
    public boolean update(TruckDto truckDto) {
        Truck truck = truckDao.findById(truckDto.getId());
        if (truck.getRegistrationNumber().equals(truckDto.getRegistrationNumber())
                || truckDao.findByRegistrationNumber(truckDto.getRegistrationNumber()) == null) {
            truck.setRegistrationNumber(truckDto.getRegistrationNumber());
            truck.setWeightCapacity(truckDto.getWeightCapacity());
            truck.setDriverShiftSize(truckDto.getDriverShiftSize());
            return true;
        }
        return false;
    }

    /**
     * Delete truck by id
     *
     * @param id truck id
     * @return true if truck was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        Truck truck = truckDao.findById(id);
        truckDao.delete(truck);
        LOGGER.info("Deleted a truck with registration number " + truck.getRegistrationNumber());
        return true;
    }

    /**
     * Finds a truck by id
     *
     * @param id truck id
     * @return TruckDto
     */
    @Override
    @Transactional
    public TruckDto findById(long id) {
        return modelMapper.map(truckDao.findById(id), TruckDto.class);
    }

    /**
     * Finds all available trucks for this order and sort and sorts them in order of increasing distance
     *
     * @param orderId order id
     * @return List<TruckDto>
     */
    @Override
    @Transactional
    public List<TruckDto> findAllAvailable(long orderId) {
        UserOrder userOrder=userOrderDao.findById(orderId);
        List<Truck> trucks = truckDao.findAllAvailable();
        Predicate<Truck> haveOrder = truck -> truck.getUserOrder() == null;
        trucks.removeIf(haveOrder);
        List<TruckDto> truckDtoList=trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        return (List<TruckDto>) countingService.getApproximatelyTotalDistanceForTruckAndOrder(truckDtoList,
                modelMapper.map(userOrder, UserOrderDto.class)).values();
    }

    /**
     * Adds order to truck
     *
     * @param truckId truck id
     * @param orderId order id
     * @return true if order was added
     */
    @Override
    @Transactional
    public boolean addOrder(long truckId, long orderId) {
        Truck truck = truckDao.findById(truckId);
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (userOrder.getStatus().equals(UserOrderStatus.NOT_TAKEN) && !userOrder.getCargoList().isEmpty()) {
            userOrder.setTruck(truck);
            userOrder.setStatus(UserOrderStatus.TAKEN);
            return true;
        }
        return false;
    }

    /**
     * Finds all available drivers for this truck
     *
     * @param truckId truck id
     * @return List<DriverDto>
     */
    @Override
    @Transactional
    public List<DriverDto> findAllAvailableDrivers(long truckId) {
        Truck truck = truckDao.findById(truckId);
        List<Driver> drivers = driverDao.findAllDriversWithoutTruck();
        Predicate<Driver> notSameCity = driver -> countingService.getDistanceLength(
                driver.getUser().getLatitude(),
                driver.getUser().getLongitude(),
                truck.getOffice().getLatitude(),
                truck.getOffice().getLongitude()) > 60;
        drivers.removeIf(notSameCity);
        return drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Adds driver to the truck
     *
     * @param truckId  truck id
     * @param driverId driver id
     * @return true if driver was added
     */
    @Override
    @Transactional
    public boolean addDriver(long truckId, long driverId) {
        Driver driver = driverDao.findById(driverId);
        Truck truck = truckDao.findById(truckId);

        //the truck does not fulfill the order and the driver does not have a car
        if (truck.getUserOrder() == null && driver.getTruck() == null) {
            driver.setTruck(truck);
            return true;
        }
        return false;
    }

    /**
     * Removes the driver from the truck
     *
     * @param truckId  truck id
     * @param driverId driver id
     * @return true if driver was removed
     */
    @Override
    @Transactional
    public boolean removeDriver(long truckId, long driverId) {
        Truck truck = truckDao.findById(truckId);
        Driver driver = driverDao.findById(driverId);
        if (truck.getUserOrder() == null) {

            //change driver status to rest
            if (!driver.getStatus().equals(DriverStatus.REST)) {
                double hoursThisMonth = driver.getHoursThisMonth();
                double hoursWorked = countingService.getDriverHours(driver);
                driver.setHoursThisMonth(hoursThisMonth + hoursWorked);
                driver.setStatus(DriverStatus.REST);
            }
            driver.setTruck(null);
            return true;
        }
        return false;
    }

//    /**
//     * Finds all available trucks for this order and sort and sorts them in order of increasing distance
//     *
//     * @param userOrderDto
//     * @return List<TruckDto>
//     */
//    @Override
//    @Transactional
//    public List<TruckDto> findAllAvailable(UserOrderDto userOrderDto) {
//        if (!(userOrderDto.getTruck() == null)) {
//            return null;
//        }
//        List<Truck> trucks = truckDao.findAll();
//        Iterator<Truck> iterator = trucks.iterator();
//        Truck truck;
//        while (iterator.hasNext()) {
//            truck = iterator.next();
//
//            //the condition of the truck should be normal
//            if (truck.getStatus().equals(TruckStatus.FAULTY)) {
//                iterator.remove();
//                continue;
//            }
//
//            //the truck must not have any other orders
//            if (!truck.getUserOrderList().isEmpty()) {
//                iterator.remove();
//                continue;
//            }
//
//            //the truck must have at least 1 driver
//            if (truck.getDriverList().isEmpty()) {
//                iterator.remove();
//                continue;
//            }
//
//            //the load must not exceed the load capacity of the truck
//            if (truck.getWeightCapacity() < getTotalWeight(userOrderDto.getCargoList())) {
//                iterator.remove();
//                continue;
//            }
//
//            //checking that the time limit of 176 hours per month will not be exceeded for any driver
//            if (isLimitForDriversExceeded(truck, userOrderDto)) {
//                iterator.remove();
//            }
//        }
//        if (trucks.isEmpty()) {
//            return null;
//        }
//
//        //sorting by distance
//        trucks = sortByDistance(trucks, userOrderDto.getWaypointList().get(0).getLocation());
//        return trucks.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

//    /**
//     * Calculate total weight for order
//     *
//     * @param cargoDtoList
//     * @return weight in kilograms
//     */
//    private double getTotalWeight(List<CargoDto> cargoDtoList) {
//        double totalWeight = 0.0;
//        for (CargoDto cargoDto : cargoDtoList) {
//            totalWeight += cargoDto.getWeight();
//        }
//        return totalWeight;
//    }
//
//    /**
//     * Sorts the list of trucks by truck location and order place of departure
//     *
//     * @param trucks
//     * @param locationDto
//     * @return List<Truck>
//     */
//    private List<Truck> sortByDistance(List<Truck> trucks, LocationDto locationDto) {
//        TreeMap<Integer, Truck> truckDistanceTreeMap = new TreeMap<>();
//        int distance;
//        for (Truck truck : trucks) {
//            distance = countingService.getDistanceLength(truck.getLocation().getLatitude(),
//                    truck.getLocation().getLongitude(),
//                    locationDto.getLatitude(),
//                    locationDto.getLongitude());
//            truckDistanceTreeMap.put(distance, truck);
//        }
//        return new ArrayList<>(truckDistanceTreeMap.values());
//    }
//
//    /**
//     * Checks that the drivers is approach for this order
//     *
//     * @param truck
//     * @param userOrderDto
//     * @return true if one of the drivers has exceeded the shift limit
//     */
//    private boolean isLimitForDriversExceeded(Truck truck, UserOrderDto userOrderDto) {
//        //the speed of the truck in kilometers per hour
//        double truckSpeed = 40.0;
//        double maxHoursPerMonth = 176.0;
//        double distanceBetweenTruckAndOrder = getDistanceBetweenTruckAndOrder(truck, userOrderDto);
//        double hoursForOrder = (float) ((userOrderDto.getDistance() + distanceBetweenTruckAndOrder) / truckSpeed);
//        List<Driver> drivers = truck.getDriverList();
//        double driverHoursThisMonth;
//        for (Driver driver : drivers) {
//            driverHoursThisMonth = driver.getHoursThisMonth();
//            if ((driverHoursThisMonth + hoursForOrder) < maxHoursPerMonth) {
//                return false;
//
//                //checking if the month changes
//            } else {
//                DateTime currentDate = new DateTime();
//                int month = currentDate.getMonthOfYear() + 1;
//                int year = currentDate.getYear();
//                if (month == 13) {
//                    month = 1;
//                    year++;
//                }
//                DateTime nextMonth = new DateTime(year, month, 1, 0, 0);
//                Days days = Days.daysBetween(currentDate, nextMonth);
//                double daysToEndThisMonth = days.getDays();
//
//                //even if the driver goes out to order every day
//                double maxHoursInThisMonth = daysToEndThisMonth * truck.getDriverShiftSize();
//                return (driverHoursThisMonth + maxHoursInThisMonth) > maxHoursPerMonth;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Calculates the distance between the truck and the order
//     *
//     * @param truck
//     * @param userOrderDto
//     * @return distance in km
//     */
//    private double getDistanceBetweenTruckAndOrder(Truck truck, UserOrderDto userOrderDto) {
//        return countingService.getDistanceLength(truck.getLocation().getLatitude(),
//                truck.getLocation().getLongitude(),
//                userOrderDto.getWaypointList().get(0).getLocation().getLatitude(),
//                userOrderDto.getWaypointList().get(0).getLocation().getLongitude());
//    }
}
