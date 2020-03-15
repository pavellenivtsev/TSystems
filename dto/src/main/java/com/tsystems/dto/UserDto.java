package com.tsystems.dto;

import java.io.Serializable;
import java.util.Set;

public class UserDto implements Serializable {

    private long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String username;

    private String password;

    private String passwordConfirm;

    private Set<RoleDto> rolesDto;

    private DriverDto driverDto;

}