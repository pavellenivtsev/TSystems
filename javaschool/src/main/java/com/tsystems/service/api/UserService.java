package com.tsystems.service.api;

import com.tsystems.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findByUsername(String username);

//    List<UserDto> findAll();

    boolean save(UserDto userDto, String locationCity, double latitude, double longitude);

    void update(UserDto userDto);

//    boolean deleteById(long id);

    UserDto findById(long id);

//    void appointAsAdmin(long id);
//
//    void appointAsManager(long id);
//
//    boolean appointAsDriver(long userId, String personalNumber);
}
