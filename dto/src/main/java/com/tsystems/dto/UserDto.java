package com.tsystems.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope=UserDto.class)
@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {
    private long id;

    @NotBlank
    @Size(min = 1)
    private String firstName;

    @NotBlank
    @Size(min = 1)
    private String lastName;

    @NotBlank
    @Size(min = 1)
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=2, message = "At least 2 characters")
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(min=5, message = "At least 5 characters")
    private String password;

    @JsonIgnore
    @NotBlank
    private String passwordConfirm;

    @NotBlank
    private String address;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private Set<RoleDto> roles;

    private DriverDto driver;
}