package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.service.api.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /**
     * Get all users from DB
     *
     * @return List<UserDto>
     */
    @Override
    @Transactional
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Deletes user by id
     *
     * @param id
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
     * @param id
     */
    @Override
    @Transactional
    public void appointAsAdmin(long id) {
        User user = userDao.findById(id);
        grantRole(user, new Role(2L, "ROLE_ADMIN"));
        userDao.update(user);

        LOGGER.info("A user with username " + user.getUsername() + " was appointed as admin.");
    }

    /**
     * Appoints user as manager
     *
     * @param id
     */
    @Override
    @Transactional
    public void appointAsManager(long id) {
        User user = userDao.findById(id);
        grantRole(user, new Role(3L, "ROLE_MANAGER"));
        userDao.update(user);

        LOGGER.info("A user with username " + user.getUsername() + " was appointed as manager.");
    }

    /**
     * Appoints user as driver
     *
     * @param id
     */
    @Override
    @Transactional
    public void appointAsDriver(long id) {
        String uniqueNumber = generatePersonalNumber();
        while (driverDao.findByPersonalNumber(uniqueNumber) != null) {
            uniqueNumber = generatePersonalNumber();
        }

        User user = userDao.findById(id);
        grantRole(user, new Role(4L, "ROLE_DRIVER"));

        Driver driver = new Driver();
        driver.setHoursThisMonth(0.0);
        driver.setStatus(DriverStatus.REST);
        driver.setPersonalNumber(uniqueNumber);
        driver.setShiftStartTime(new DateTime());
        driver.setUser(user);
        driverDao.save(driver);
        LOGGER.info("A user with username " + user.getUsername() + " was appointed as driver.");
    }

    /**
     * Grants role to user
     *
     * @param user
     * @param role
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
}
