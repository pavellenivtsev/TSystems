package com.tsystems.service.api;

import com.tsystems.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    boolean save(User user);
    void update(User user);
    void delete(User user);
    User findById(long id);
}
