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
import com.tsystems.exception.DataChangingException;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.JMSSenderService;
import com.tsystems.service.api.TruckService;
import com.tsystems.utils.TruckPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
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

    @Autowired
    private JMSSenderService jmsSenderService;

    /**
     * Saves a new truck
     *
     * @param truckDto - truck
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
            jmsSenderService.sendMessage();
            return true;
        }
        return false;
    }

    /**
     * Updates the truck
     *
     * @param truckDto - truck
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
     * @param id - truck id
     * @return true if truck was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        Truck truck = truckDao.findById(id);
        if (truck.getUserOrder() != null || !truck.getDriverList().isEmpty()) {
            throw new DataChangingException("Cant delete this truck");
        }
        truckDao.delete(truck);
        LOGGER.info("Deleted a truck with registration number " + truck.getRegistrationNumber());
        jmsSenderService.sendMessage();
        return true;
    }

    /**
     * Finds a truck by id
     *
     * @param id - truck id
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
     * @param orderId - order id
     * @return List<TruckDto>
     */
    @Override
    @Transactional
    public List<TruckDto> findAllAvailable(long orderId) {
        UserOrder userOrder = userOrderDao.findById(orderId);
        List<Truck> trucks = truckDao.findAllAvailable();
        Predicate<Truck> haveOrder = truck -> truck.getUserOrder() != null;
        trucks.removeIf(haveOrder);
        List<TruckDto> truckDtoList = trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        List<TruckPair> truckPairs = countingService.getApproximatelyTotalDistanceForTruckAndOrder(truckDtoList,
                modelMapper.map(userOrder, UserOrderDto.class));

        //checking that the time limit of 176 hours per month will not be exceeded for any driver
        Predicate<TruckPair> isLimitForDriversExceeded = this::isLimitForDriversExceeded;
        truckPairs.removeIf(isLimitForDriversExceeded);
        Collections.sort(truckPairs);
        return truckPairs.stream()
                .map(TruckPair::getTruckDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds order to truck
     *
     * @param truckId - truck id
     * @param orderId - order id
     * @return true if order was added
     */
    @Override
    @Transactional
    public boolean addOrder(long truckId, long orderId) {
        Truck truck = truckDao.findById(truckId);
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (!userOrder.getStatus().equals(UserOrderStatus.NOT_TAKEN) ||
                userOrder.getCargoList().isEmpty() ||
                truck.getStatus().equals(TruckStatus.FAULTY) ||
                truck.getUserOrder() != null) {
            throw new DataChangingException("Cant add this truck to this order");
        }
        userOrder.setTruck(truck);
        userOrder.setStatus(UserOrderStatus.TAKEN);
        LOGGER.info("The truck with the number " + truck.getRegistrationNumber() +
                " was assigned to the order with the number " + userOrder.getUniqueNumber());
        jmsSenderService.sendMessage();
        return true;
    }

    /**
     * Finds all available drivers for this truck
     *
     * @param truckId - truck id
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
     * @param truckId  - truck id
     * @param driverId - driver id
     * @return true if driver was added
     */
    @Override
    @Transactional
    public boolean addDriver(long truckId, long driverId) {
        Driver driver = driverDao.findById(driverId);
        Truck truck = truckDao.findById(truckId);

        //the truck does not fulfill the order and the driver does not have a car
        if (truck.getUserOrder() != null || driver.getTruck() != null) {
            throw new DataChangingException("Cant add this driver to this truck");
        }
        driver.setTruck(truck);
        LOGGER.info("The driver with the number " + driver.getPersonalNumber() +
                " was assigned to the truck with the registration number " + truck.getRegistrationNumber());
        return true;
    }

    /**
     * Removes the driver from the truck
     *
     * @param truckId  - truck id
     * @param driverId - driver id
     * @return true if driver was removed
     */
    @Override
    @Transactional
    public boolean removeDriver(long truckId, long driverId) {
        Truck truck = truckDao.findById(truckId);
        Driver driver = driverDao.findById(driverId);
        if (truck.getUserOrder() != null) {
            throw new DataChangingException("Cant remove this driver because this driver is carrying an order");
        }

        //change driver status to rest
        if (!driver.getStatus().equals(DriverStatus.REST)) {
            double hoursThisMonth = driver.getHoursThisMonth();
            double hoursWorked = countingService.getDriverHours(driver);
            driver.setHoursThisMonth(hoursThisMonth + hoursWorked);
            driver.setStatus(DriverStatus.REST);
            LOGGER.info("The status of the driver with the number " +
                    driver.getPersonalNumber() + " was changed to rest");
        }
        driver.setTruck(null);
        LOGGER.info("The driver with the number " + driver.getPersonalNumber() +
                " was removed from the truck with the registration number " + truck.getRegistrationNumber());
        return true;
    }

    /**
     * Checks that the drivers is approach for this order
     *
     * @param truckPair - truck and distance for this truck and order
     * @return true if one of the drivers has exceeded the shift limit
     */
    private boolean isLimitForDriversExceeded(TruckPair truckPair) {
        //the speed of the truck in kilometers per hour
        final double truckSpeed = 90.0;
        final double maxHoursPerMonth = 176.0;
        final double hoursForOrder = (float) (truckPair.getApproximatelyTotalDistanceForTruckAndOrder() / truckSpeed);
        double driverHoursThisMonth;
        for (DriverDto driverDto : truckPair.getTruckDto().getDriverList()) {
            driverHoursThisMonth = driverDto.getHoursThisMonth();
            if ((driverHoursThisMonth + hoursForOrder) < maxHoursPerMonth) {
                return false;
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
                double maxHoursInThisMonth = daysToEndThisMonth * truckPair.getTruckDto().getDriverShiftSize();
                return (driverHoursThisMonth + maxHoursInThisMonth) > maxHoursPerMonth;
            }
        }
        return true;
    }
}

