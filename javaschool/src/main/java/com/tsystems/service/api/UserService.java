package com.tsystems.service.api;

import com.tsystems.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> findAll();

    boolean save(UserDto userDto);

    void update(UserDto userDto);

    boolean deleteById(long id);

    UserDto findById(long id);

    void appointAsAdmin(long id);

    void appointAsManager(long id);

    void appointAsDriver(long id);
}
