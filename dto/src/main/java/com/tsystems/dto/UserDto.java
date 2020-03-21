package com.tsystems.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable {

    private long id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String phoneNumber;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=2, message = "At least 2 characters")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min=2, message = "At least 2 characters")
    private String password;

    private String passwordConfirm;

    private Set<RoleDto> roles;

    private DriverDto driver;

}