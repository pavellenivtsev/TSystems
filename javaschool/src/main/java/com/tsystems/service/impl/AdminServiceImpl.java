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
import com.tsystems.service.api.GeneratorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private GeneratorService generatorService;

    /**
     * Get all users from DB
     *
     * @return List<UserDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userDao.findAll().stream()
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
    public boolean deleteById(final long id) {
        final User user = findUserById(id);
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
    public boolean appointAsAdmin(final long id) {
        final User user = findUserById(id);
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
    public boolean appointAsManager(final long id) {
        final User user = findUserById(id);
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
    public boolean appointAsDriver(final long id) {
        String uniqueNumber = generatorService.generateDriverPersonalNumber();
        while (driverDao.findByPersonalNumber(uniqueNumber) != null) {
            uniqueNumber = generatorService.generateDriverPersonalNumber();
        }
        final User user = findUserById(id);
        if (isDriver(user)) {
            throw new DataChangingException("This user is already a driver");
        }
        grantRole(user, new Role(4L, "ROLE_DRIVER"));
        final Driver driver = Driver.builder()
                .hoursThisMonth(0.0)
                .status(DriverStatus.REST)
                .personalNumber(uniqueNumber)
                .shiftStartTime(new DateTime())
                .user(user)
                .build();
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
    public boolean appointAsUser(final long id) {
        final User user = findUserById(id);
        if (isDriver(user)) {
            try {
                deleteDriver(user);
            } catch (DataChangingException e) {
                return false;
            }
            jmsSenderService.sendMessage();
        }
        grantRole(user, new Role(1L, "ROLE_USER"));
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as user.");
        return true;
    }

    /**
     * Deletes driver
     *
     * @param user - user
     */
    private void deleteDriver(final @NonNull User user) {
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
    private void grantRole(final @NonNull User user, final @NonNull Role role) {
        user.setRoles(null);
        user.setRoles(Collections.singleton(role));
    }

    /**
     * Checks whether the user is a driver
     *
     * @param user - user
     * @return true if user is a driver
     */
    private boolean isDriver(final @NonNull User user) {
        return user.getDriver() != null;
    }

    /**
     * Finds user by id
     *
     * @param id - user id
     * @return User
     */
    private User findUserById(final long id) {
        return Optional.of(id)
                .map(userDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " does not exist"));
    }
}

