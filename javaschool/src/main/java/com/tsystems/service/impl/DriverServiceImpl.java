package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.entity.*;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.exception.DataChangingException;
import com.tsystems.exception.NoSuchUserException;
import com.tsystems.exception.UserIsNotDriverException;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.api.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger LOGGER = LogManager.getLogger(DriverServiceImpl.class);

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private CountingService countingService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Finds driver by username
     *
     * @param username username
     * @return DriverDto
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
        return modelMapper.map(user.getDriver(), DriverDto.class);
    }

    /**
     * Finds driver by id
     *
     * @param id driver id
     * @return DriverDto
     */
    @Override
    @Transactional
    public DriverDto findById(long id) {
        Driver driver = driverDao.findById(id);
        return modelMapper.map(driver, DriverDto.class);
    }

    /**
     * Changes driver status to ON_SHIFT
     *
     * @param id driver id
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
            drivers.forEach(driver1 -> {
                driver1.setHoursThisMonth(0.0);
            });
        }

        //driver must have REST status & must have a truck
        if (!driver.getStatus().equals(DriverStatus.REST) && driver.getTruck() == null) {
            throw new DataChangingException("Data changing exception");
        }
        driver.setStatus(DriverStatus.ON_SHIFT);
        driver.setShiftStartTime(new DateTime());
        LOGGER.info("Driver with personal number "
                + driver.getPersonalNumber()
                + " changed his status to ON_SHIFT");
        return true;
    }

    /**
     * Changes driver status to REST
     *
     * @param id driver id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeDriverStatusToRest(long id) {
        Driver driver = driverDao.findById(id);
        if (driver.getStatus().equals(DriverStatus.REST)) {
            throw new DataChangingException("The driver already has a rest status");
        }
        double hoursThisMonth = driver.getHoursThisMonth();
        double hoursWorked = countingService.getDriverHours(driver);
        driver.setStatus(DriverStatus.REST);
        driver.setHoursThisMonth(hoursThisMonth + hoursWorked);
        LOGGER.info("Driver with personal number " + driver.getPersonalNumber() + " changed his status to REST");
        return true;
    }

    /**
     * Changes truck status to ON_DUTY
     *
     * @param id driver id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeTruckStatusToOnDuty(long id) {
        Driver driver = driverDao.findById(id);
        driver.getTruck().setStatus(TruckStatus.ON_DUTY);
        return true;
    }

    /**
     * Changes truck status to FAULTY
     *
     * @param id driver id
     * @return true if status is changed
     */
    @Override
    @Transactional
    public boolean changeTruckStatusToFaulty(long id) {
        Driver driver = driverDao.findById(id);
        driver.getTruck().setStatus(TruckStatus.FAULTY);
        return true;
    }

    /**
     * Complete order
     *
     * @param userOrderId order id
     * @return true if order status is changed
     */
    @Override
    @Transactional
    public boolean completeOrder(long userOrderId) {
        UserOrder userOrder = userOrderDao.findById(userOrderId);
        userOrder.getCargoList().forEach(cargo -> {
            if (!cargo.getStatus().equals(CargoStatus.DELIVERED)) {
                throw new DataChangingException("At least one of the cargo was not delivered");
            }
        });
        userOrder.setStatus(UserOrderStatus.COMPLETED);
        userOrder.setTruck(null);
        return true;
    }
}
