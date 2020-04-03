package com.tsystems.service.api;

import com.tsystems.dto.UserDto;

import java.util.List;

public interface AdminService {
    boolean deleteById(long id);

    List<UserDto> findAll();

    void appointAsAdmin(long id);

    void appointAsManager(long id);

    void appointAsDriver(long userId);
}
