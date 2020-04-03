package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.entity.*;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.exception.NoSuchUserException;
import com.tsystems.exception.UserIsNotDriverException;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger LOGGER = LogManager.getLogger(DriverServiceImpl.class);

    private final DriverDao driverDao;

    private final UserDao userDao;

    private final UserOrderDao userOrderDao;

    private final TruckDao truckDao;

    private final CountingService countingService;

    private final ModelMapper modelMapper;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao, UserDao userDao, UserOrderDao userOrderDao, TruckDao truckDao, CountingService countingService, ModelMapper modelMapper) {
        this.driverDao = driverDao;
        this.userDao = userDao;
        this.userOrderDao = userOrderDao;
        this.truckDao = truckDao;
        this.countingService = countingService;
        this.modelMapper = modelMapper;
    }

    /**
     * Finds driver by username
     *
     * @param username
     * @return
     */
    @Override
    @Transactional
    public DriverDto findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new NoSuchUserException("Username: " + username);
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            if (!grantedAuthority.getAuthority().equals("ROLE_DRIVER"))
                throw new UserIsNotDriverException("Username: " + username);
        }
        return convertToDto(user.getDriver());
    }

    /**
     * Finds all drivers
     *
     * @return
     */
    @Override
    @Transactional
    public List<DriverDto> findAll() {
        List<Driver> drivers = driverDao.findAll();
        return drivers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds driver by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public DriverDto findById(long id) {
        Driver driver = driverDao.findById(id);
        return convertToDto(driver);
    }

    /**
     * Finds all available drivers for this truck
     *
     * @param truckDto
     * @return
     */
    @Override
    @Transactional
    public List<DriverDto> findAllAvailable(TruckDto truckDto) {
        List<Driver> drivers = driverDao.findAllDriversWithoutTruck();
        Iterator<Driver> iterator = drivers.iterator();
        Driver driver;
        while (iterator.hasNext()) {
            driver = iterator.next();

            //driver must be in the same city as the truck
            if (countingService.getDistanceLength(
                    driver.getUser().getLocation().getLatitude(),
                    driver.getUser().getLocation().getLongitude(),
                    truckDto.getLocation().getLatitude(),
                    truckDto.getLocation().getLongitude()) > 60) {
                iterator.remove();
            }
        }
        return drivers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Changes driver status to ON_SHIFT
     *
     * @param id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeDriverStatusToOnShift(long id) {
        Driver driver = driverDao.findById(id);
        DateTime currentDate = new DateTime();

        //hours worked in this month are reset to zero for all drivers if at least one driver's status changes
        if (!currentDate.monthOfYear().equals(driver.getShiftStartTime().monthOfYear())) {
            List<Driver> drivers = driverDao.findAll();
            driver.setHoursThisMonth(0.0);
            for (Driver driver1 : drivers) {
                driver1.setHoursThisMonth(0.0);
                driverDao.update(driver1);
            }
        }

        //driver must have REST status
        if (driver.getStatus() == DriverStatus.REST) {

            //driver must have a truck
            if (driver.getTruck() != null) {
                driver.setStatus(DriverStatus.ON_SHIFT);
                driver.setShiftStartTime(new DateTime());
                driverDao.update(driver);
                LOGGER.info("Driver with personal number " + driver.getPersonalNumber() + " changed his status to ON_SHIFT");
                return true;
            }
        }
        return false;
    }

    /**
     * Changes driver status to REST
     *
     * @param id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeDriverStatusToRest(long id) {
        Driver driver = driverDao.findById(id);
        if (driver.getStatus().equals(DriverStatus.REST)) {
            return false;
        }
        double hoursThisMonth = driver.getHoursThisMonth();
        double hoursWorked = calculateTime(driver.getShiftStartTime());
        double maxHoursWorked = driver.getTruck().getDriverShiftSize();
        hoursWorked = Math.min(maxHoursWorked, hoursWorked);
        driver.setStatus(DriverStatus.REST);
        driver.setHoursThisMonth(hoursThisMonth + hoursWorked);
        LOGGER.info("Driver with personal number " + driver.getPersonalNumber() + " changed his status to REST");
        return true;
    }

    /**
     * Changes truck status to ON_DUTY
     *
     * @param id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeTruckStatusToOnDuty(long id) {
        Driver driver = driverDao.findById(id);
        driver.getTruck().setStatus(TruckStatus.ON_DUTY);
        driverDao.update(driver);
        return true;
    }

    /**
     * Changes truck status to FAULTY
     *
     * @param id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeTruckStatusToFaulty(long id) {

        //добавить функциональность
        Driver driver = driverDao.findById(id);
        driver.getTruck().setStatus(TruckStatus.FAULTY);
        driverDao.update(driver);
        return true;
    }

    /**
     * Complete order
     *
     * @param userOrderId
     * @return true if order status is changed
     */
    @Override
    @Transactional
    public boolean completeOrder(long userOrderId) {
        UserOrder userOrder = userOrderDao.findById(userOrderId);
        for (Cargo cargo : userOrder.getCargoList()) {
            if (!cargo.getStatus().equals(CargoStatus.DELIVERED)) {
                return false;
            }
        }
        userOrder.setStatus(UserOrderStatus.COMPLETED);
        userOrder.setTruck(null);
        userOrderDao.update(userOrder);
        return true;
    }

    private DriverDto convertToDto(Driver driver) {
        return modelMapper.map(driver, DriverDto.class);
    }

    private Driver convertToEntity(DriverDto driverDto) {
        return modelMapper.map(driverDto, Driver.class);
    }

    /**
     * Calculates hours of working for driver
     *
     * @param shiftStartTime
     * @return
     */
    private double calculateTime(DateTime shiftStartTime) {
        DateTime currentDate = new DateTime();
        Seconds seconds = Seconds.secondsBetween(shiftStartTime, currentDate);
        double secondsDouble = seconds.getSeconds();
        return secondsDouble / 3600;
    }
}
