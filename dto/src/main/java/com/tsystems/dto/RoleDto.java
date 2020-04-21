package com.tsystems.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope=RoleDto.class)
@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    private Set<UserDto> users;

}
