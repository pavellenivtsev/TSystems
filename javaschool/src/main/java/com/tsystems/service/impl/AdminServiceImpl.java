package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.exception.DataChangingException;
import com.tsystems.service.api.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JMSSenderServiceImpl jmsSenderService;

    /**
     * Get all users from DB
     *
     * @return List<UserDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Deletes user by id
     *
     * @param id - user id
     * @return true if user was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
        LOGGER.info("Deleted a user with username " + user.getUsername());
        return true;
    }

    /**
     * Appoints user as admin
     *
     * @param id - user id
     */
    @Override
    @Transactional
    public boolean appointAsAdmin(long id) {
        User user = userDao.findById(id);
        if (isDriver(user)) {
            try {
                deleteDriver(user);
            } catch (DataChangingException e) {
                return false;
            }
            jmsSenderService.sendMessage();
        }
        grantRole(user, new Role(2L, "ROLE_ADMIN"));
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as admin.");
        return true;
    }

    /**
     * Appoints user as manager
     *
     * @param id - user id
     */
    @Override
    @Transactional
    public boolean appointAsManager(long id) {
        User user = userDao.findById(id);
        if (isDriver(user)) {
            try {
                deleteDriver(user);
            } catch (DataChangingException e) {
                return false;
            }
            jmsSenderService.sendMessage();
        }
        grantRole(user, new Role(3L, "ROLE_MANAGER"));
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as manager.");
        return true;
    }

    /**
     * Appoints user as driver
     *
     * @param id - user id
     */
    @Override
    @Transactional
    public boolean appointAsDriver(long id) {
        String uniqueNumber = generatePersonalNumber();
        while (driverDao.findByPersonalNumber(uniqueNumber) != null) {
            uniqueNumber = generatePersonalNumber();
        }
        User user = userDao.findById(id);
        if (isDriver(user)) {
            throw new DataChangingException("This user is already a driver");
        }
        grantRole(user, new Role(4L, "ROLE_DRIVER"));
        Driver driver = new Driver();
        driver.setHoursThisMonth(0.0);
        driver.setStatus(DriverStatus.REST);
        driver.setPersonalNumber(uniqueNumber);
        driver.setShiftStartTime(new DateTime());
        driver.setUser(user);
        driverDao.save(driver);
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as driver.");
        jmsSenderService.sendMessage();
        return true;
    }

    /**
     * Appoints user as user
     *
     * @param id - user id
     */
    @Override
    @Transactional
    public boolean appointAsUser(long id) {
        User user = userDao.findById(id);
        if (isDriver(user)) {
            try {
                deleteDriver(user);
            } catch (DataChangingException e) {
                return false;
            }
            jmsSenderService.sendMessage();
        }
        grantRole(user, new Role(1L, "ROLE_USER"));
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as manager.");
        return true;
    }

    /**
     * Deletes driver
     *
     * @param user - user
     */
    private void deleteDriver(User user) {
        if (user.getDriver().getTruck() != null &&
                user.getDriver().getTruck().getUserOrder() != null) {
            throw new DataChangingException("The driver completes the order");
        }
        Driver driver = user.getDriver();
        user.setDriver(null);
        driverDao.delete(driver);
    }

    /**
     * Grants role to user
     *
     * @param user - user
     * @param role - role
     */
    private void grantRole(User user, Role role) {
        user.setRoles(null);
        user.setRoles(Collections.singleton(role));
    }

    /**
     * Generates personal number for driver
     *
     * @return personal number
     */
    private String generatePersonalNumber() {
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /**
     * Checks whether the user is a driver
     *
     * @param user - user
     * @return true if user is a driver
     */
    private boolean isDriver(User user) {
        return user.getDriver() != null;
    }
}

