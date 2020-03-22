package com.tsystems.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    private Set<UserDto> users;

}
