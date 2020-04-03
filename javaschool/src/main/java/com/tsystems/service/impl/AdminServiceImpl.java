package com.tsystems.service.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.LocationDao;
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

    private final UserDao userDao;

    private final DriverDao driverDao;

    private final LocationDao locationDao;

    private final ModelMapper modelMapper;

    @Autowired
    public AdminServiceImpl(UserDao userDao, DriverDao driverDao, LocationDao locationDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.locationDao = locationDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        User user = userDao.findById(id);
        locationDao.delete(user.getLocation());
        LOGGER.info("Deleted a user with username " + user.getUsername());
        return true;
    }

    @Override
    @Transactional
    public void appointAsAdmin(long id) {
        User user = userDao.findById(id);
        grantRole(user, new Role(2L, "ROLE_ADMIN"));
        userDao.update(user);

        LOGGER.info("A user with username " + user.getUsername()+" was appointed as admin.");
    }

    @Override
    @Transactional
    public void appointAsManager(long id) {
        User user = userDao.findById(id);
        grantRole(user, new Role(3L, "ROLE_MANAGER"));
        userDao.update(user);

        LOGGER.info("A user with username " + user.getUsername()+" was appointed as manager.");
    }

    @Override
    @Transactional
    public void appointAsDriver(long userId) {
        String uniqueNumber=generatePersonalNumber();
        outer:
        for(Driver driver:driverDao.findAll()){
            if (driver.getPersonalNumber().equals(uniqueNumber)){
                uniqueNumber=generatePersonalNumber();
                break outer;
            }
        }

        User user = userDao.findById(userId);
        grantRole(user, new Role(4L, "ROLE_DRIVER"));

        Driver driver =new Driver();
        driver.setHoursThisMonth(0.0);
        driver.setStatus(DriverStatus.REST);
        driver.setPersonalNumber(uniqueNumber);
        driver.setShiftStartTime(new DateTime());
        driver.setUser(user);

        user.setDriver(driver);
        userDao.update(user);

        LOGGER.info("A user with username " + user.getUsername()+" was appointed as driver.");
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private void grantRole(User user, Role role){
        user.setRoles(null);
        user.setRoles(Collections.singleton(role));
    }

    private String generatePersonalNumber(){
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
