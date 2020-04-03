package com.tsystems.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

    private long id;

    @Size(min = 1, message = "At least 1 characters")
    private String firstName;

    @Size(min = 1, message = "At least 1 characters")
    private String lastName;

    @Size(min = 1, message = "At least 1 characters")
    private String phoneNumber;

    private String email;

    @Size(min=2, message = "At least 2 characters")
    private String username;

    @Size(min=5, message = "At least 5 characters")
    private String password;

    private String passwordConfirm;

    private Set<RoleDto> roles;

    private DriverDto driver;

    private LocationDto location;

}