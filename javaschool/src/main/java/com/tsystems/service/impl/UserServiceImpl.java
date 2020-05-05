package com.tsystems.service.impl;

import com.tsystems.dao.api.UserDao;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Role;
import com.tsystems.entity.User;
import com.tsystems.service.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Finds user by username
     *
     * @param username - username
     * @return UserDto
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findByUsername(final @NonNull String username) {
        final User user = Optional.of(username)
                .map(userDao::findByUsername)
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " does not exist"));
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Loads user by username
     *
     * @param username - username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final @NonNull String username) throws UsernameNotFoundException {
        final User user = Optional.of(username)
                .map(userDao::findByUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    /**
     * Saves new user
     *
     * @param userDto - user
     * @return true if user was saved
     */
    @Override
    @Transactional
    public boolean save(final @NonNull UserDto userDto) {
        final Optional<User> userFromDB = Optional.ofNullable(userDao.findByUsername(userDto.getUsername()));
        if (userFromDB.isPresent()||userDto.getUsername().equals("admin")) {
            return false;
        }
        final User user = modelMapper.map(userDto, User.class);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDao.save(user);
        LOGGER.info("Saved a new user with username " + user.getUsername());
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        final Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    /**
     * Edit user information
     *
     * @param userDto - user
     */
    @Override
    @Transactional
    public void update(final @NonNull UserDto userDto) {
        final User user = findUserById(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
    }

    /**
     * Finds user by id
     *
     * @param id - user id
     * @return UserDto
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findById(long id) {
        return modelMapper.map(findUserById(id), UserDto.class);
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
