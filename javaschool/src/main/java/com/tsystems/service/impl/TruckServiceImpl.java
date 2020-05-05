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
import com.tsystems.exception.NoTrucksWereFoundException;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.JMSSenderService;
import com.tsystems.service.api.TruckService;
import com.tsystems.utils.TruckPair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
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
        final Office office = Optional.of(officeId)
                .map(officeDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Office with id: " + officeId + " does not exist"));
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
        final Truck truck = findTruckById(truckDto.getId());
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
     * @return office id
     */
    @Override
    @Transactional
    public long deleteById(long id) {
        final Truck truck = findTruckById(id);
        final long officeId = truck.getOffice().getId();
        if (truck.getUserOrder() != null || CollectionUtils.isNotEmpty(truck.getDriverList())) {
            throw new DataChangingException("Cant delete this truck");
        }
        truckDao.delete(truck);
        LOGGER.info("Deleted a truck with registration number " + truck.getRegistrationNumber());
        jmsSenderService.sendMessage();
        return officeId;
    }

    /**
     * Finds a truck by id
     *
     * @param id - truck id
     * @return TruckDto
     */
    @Override
    @Transactional(readOnly = true)
    public TruckDto findById(long id) {
        return modelMapper.map(findTruckById(id), TruckDto.class);
    }

    /**
     * Finds all available trucks for this order and sort them in order of increasing distance
     *
     * @param orderId - order id
     * @return List<TruckPair>
     */
    @Override
    @Transactional(readOnly = true)
    public List<TruckPair> findAllAvailable(long orderId) {
        final int maximumDeltaDistanceForTrucks = 1000;
        final UserOrder userOrder = Optional.of(orderId)
                .map(userOrderDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " does not exist"));
        final List<Truck> trucks = truckDao.findAllAvailable();
        final List<TruckDto> truckDtoList = trucks.stream()
                .filter(truck -> truck.getUserOrder() == null)
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        final List<TruckPair> truckPairs = countingService.getApproximatelyTotalDistanceForTrucksAndOrder(truckDtoList,
                modelMapper.map(userOrder, UserOrderDto.class));
        final int bestEstimatedDistance;
        try {
            final TruckPair bestTruckPair = truckPairs.stream()
                    .sorted(Comparator.comparingInt(TruckPair::getApproximatelyTotalDistanceForTruckAndOrder))
                    .filter(this::isLimitForDriversNotExceeded)
                    .findFirst()
                    .orElseThrow(NoTrucksWereFoundException::new);
            bestEstimatedDistance = bestTruckPair.getApproximatelyTotalDistanceForTruckAndOrder();
        } catch (NoTrucksWereFoundException e) {
            return null;
        }
        return truckPairs.stream()
                .filter(this::isLimitForDriversNotExceeded)
                .filter(truckPair -> (truckPair.getApproximatelyTotalDistanceForTruckAndOrder() - bestEstimatedDistance)
                        < maximumDeltaDistanceForTrucks)
                .sorted(Comparator
                        .comparingInt(TruckPair::getApproximatelyTotalDistanceForTruckAndOrder)
                        .thenComparingDouble(truckPair -> truckPair.getTruckDto().getWeightCapacity()))
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
        final Truck truck = findTruckById(truckId);
        final UserOrder userOrder = Optional.of(orderId)
                .map(userOrderDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " does not exist"));
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
    @Transactional(readOnly = true)
    public List<DriverDto> findAllAvailableDrivers(long truckId) {
        final int cityDistance = 60;
        final Truck truck = truckDao.findById(truckId);
        final List<Driver> drivers = driverDao.findAllDriversWithoutTruck();
        return drivers.stream()
                .filter(driver -> countingService.getDistanceLength(
                        driver.getUser().getLatitude(),
                        driver.getUser().getLongitude(),
                        truck.getOffice().getLatitude(),
                        truck.getOffice().getLongitude()) < cityDistance)
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
        final Driver driver = Optional.of(driverId)
                .map(driverDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id: " + driverId + " does not exist"));
        final Truck truck = findTruckById(truckId);

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
        final Truck truck = findTruckById(truckId);
        final Driver driver = Optional.of(driverId)
                .map(driverDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id: " + driverId + " does not exist"));
        if (truck.getUserOrder() != null) {
            throw new DataChangingException("Cant remove this driver because this driver is carrying an order");
        }

        //change driver status to rest
        if (!driver.getStatus().equals(DriverStatus.REST)) {
            final double hoursThisMonth = driver.getHoursThisMonth();
            final double hoursWorked = countingService.getDriverHours(driver);
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
    private boolean isLimitForDriversNotExceeded(final @NonNull TruckPair truckPair) {
        //the speed of the truck in kilometers per hour
        final double truckSpeed = 90.0;
        final double maxHoursPerMonth = 176.0;
        final double hoursForOrder = (float) (truckPair.getApproximatelyTotalDistanceForTruckAndOrder() / truckSpeed);

        for (DriverDto driverDto : truckPair.getTruckDto().getDriverList()) {
            if ((driverDto.getHoursThisMonth() + hoursForOrder) < maxHoursPerMonth) {
                return true;
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
                return !((driverDto.getHoursThisMonth() + maxHoursInThisMonth) > maxHoursPerMonth);
            }
        }
        return false;
    }

    /**
     * Finds truck by id
     *
     * @param id - truck id
     * @return Truck
     */
    private Truck findTruckById(final long id) {
        return Optional.of(id)
                .map(truckDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Truck with id: " + id + " does not exist"));
    }
}

