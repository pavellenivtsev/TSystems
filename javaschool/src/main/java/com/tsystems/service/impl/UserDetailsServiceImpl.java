package com.tsystems.service.impl;

import com.tsystems.dao.api.LocationDao;
import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Location;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//@Service
public class UserDetailsServiceImpl /*implements UserDetailsService*/{
//    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
//
//    private final UserDao userDao;
//
//    private final LocationDao locationDao;
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public UserDetailsServiceImpl(UserDao userDao, LocationDao locationDao, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
//        this.userDao = userDao;
//        this.locationDao = locationDao;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.findByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for(Role role:user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
//    }
//
//    @Transactional
//    public boolean save(UserDto userDto, String locationCity) {
//
//        if (userDao.findByUsername(userDto.getUsername())==null){
//            Location location=new Location();
//            location.setCity(locationCity);
//            locationDao.save(location);
//
//            User user = convertToEntity(userDto);
//            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
//            user.setLocation(location);
//
//            userDao.save(user);
//            LOGGER.info("Saved a new user with username " + user.getUsername());
//            return true;
//        }
//        return false;
//    }
//
//    private UserDto convertToDto(User user) {
//        return modelMapper.map(user, UserDto.class);
//    }
//
//    private User convertToEntity(UserDto userDto) {
//        return modelMapper.map(userDto, User.class);
//    }
//
//    private void grantRole(User user, Role role){
//        user.setRoles(null);
//        user.setRoles(Collections.singleton(role));
//    }
}
