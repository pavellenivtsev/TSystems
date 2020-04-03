package com.tsystems.service.impl;

import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Location;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import com.tsystems.service.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

//    private final DriverDao driverDao;

    private final LocationDao locationDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, LocationDao locationDao, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.locationDao = locationDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDto findByUsername(String username) {
        return convertToDto(userDao.findByUsername(username));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role:user.getRoles()){
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }

//    @Override
//    @Transactional
//    public List<UserDto> findAll() {
//        List<User> users = userDao.findAll();
//        return users.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public boolean save(UserDto userDto, String locationCity,double latitude, double longitude) {

        if (userDao.findByUsername(userDto.getUsername())==null){
            Location location=new Location();
            location.setCity(locationCity);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            locationDao.save(location);

            User user = convertToEntity(userDto);
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setLocation(location);

            userDao.save(user);
            LOGGER.info("Saved a new user with username " + user.getUsername());

            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public void update(UserDto userDto) {
        User user = userDao.findById(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        userDao.update(user);
    }
//
//    @Override
//    @Transactional
//    public boolean deleteById(long id) {
//        User user = userDao.findById(id);
//        userDao.delete(user);
//        LOGGER.info("Deleted a user with username " + user.getUsername());
//
//        return true;
//    }
//
    @Override
    @Transactional
    public UserDto findById(long id) {
        User user = userDao.findById(id);
        return convertToDto(user);
    }
//
//    @Override
//    @Transactional
//    public void appointAsAdmin(long id) {
//        User user = userDao.findById(id);
//        grantRole(user, new Role(2L, "ROLE_ADMIN"));
//        userDao.update(user);
//
//        LOGGER.info("A user with username " + user.getUsername()+" was appointed as admin.");
//    }
//
//    @Override
//    @Transactional
//    public void appointAsManager(long id) {
//        User user = userDao.findById(id);
//        grantRole(user, new Role(3L, "ROLE_MANAGER"));
//        userDao.update(user);
//
//        LOGGER.info("A user with username " + user.getUsername()+" was appointed as manager.");
//    }
//
//    @Override
//    @Transactional
//    public boolean appointAsDriver(long userId, String personalNumber) {
//        for(Driver driver:driverDao.findAll()){
//            if (driver.getPersonalNumber().equals(personalNumber)){
//                return false;
//            }
//        }
//
//        User user = userDao.findById(userId);
//        grantRole(user, new Role(4L, "ROLE_DRIVER"));
//
//        Driver driver =new Driver();
//        driver.setHoursThisMonth(0.0);
//        driver.setStatus(DriverStatus.REST);
//        driver.setPersonalNumber(personalNumber);
//
//        user.setDriver(driver);
//        userDao.update(user);
//
//        LOGGER.info("A user with username " + user.getUsername()+" was appointed as driver.");
//        return true;
//    }
//
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
}
