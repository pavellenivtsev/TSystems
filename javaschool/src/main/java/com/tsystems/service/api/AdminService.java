package com.tsystems.service.api;

import com.tsystems.dto.UserDto;

import java.util.List;

public interface AdminService {
    boolean deleteById(long id);

    List<UserDto> findAll();

    boolean appointAsAdmin(long id);

    boolean appointAsManager(long id);

    boolean appointAsDriver(long userId);

    boolean appointAsUser(long id);
}
