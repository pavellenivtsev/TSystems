package com.tsystems.service.impl;


import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import com.tsystems.service.api.UserService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
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
    public boolean save(UserDto userDto) {

        if (userDao.findByUsername(userDto.getUsername())==null){
            User user = convertToEntity(userDto);
            user.setRoles(Collections.singleton(new Role(1L, "USER")));
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userDao.save(user);
            log.info("Saved a new user with username " + user.getUsername());
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public void update(UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.update(user);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
        log.info("Deleted a user with username " + user.getUsername());
        return true;
    }

    @Override
    @Transactional
    public UserDto findById(long id) {
        User user = userDao.findById(id);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public void appointAsAdmin(long id) {
        User user = userDao.findById(id);
        user.setRoles(null);
        user.setRoles(Collections.singleton(new Role(2L, "ADMIN")));
        log.info("A user with username " + user.getUsername()+" was appointed as admin.");
    }

    @Override
    @Transactional
    public void appointAsManager(long id) {
        User user = userDao.findById(id);
        user.setRoles(null);
        user.setRoles(Collections.singleton(new Role(3L, "MANAGER")));
        log.info("A user with username " + user.getUsername()+" was appointed as manager.");
    }

    @Override
    @Transactional
    public void appointAsDriver(long id) {
        User user = userDao.findById(id);
        user.setRoles(null);
        user.setRoles(Collections.singleton(new Role(4L, "DRIVER")));
        log.info("A user with username " + user.getUsername()+" was appointed as driver.");
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
