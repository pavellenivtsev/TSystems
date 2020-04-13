package com.tsystems.service.api;

import com.tsystems.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findByUsername(String username);

    boolean save(UserDto userDto);

    void update(UserDto userDto);

    UserDto findById(long id);
}
