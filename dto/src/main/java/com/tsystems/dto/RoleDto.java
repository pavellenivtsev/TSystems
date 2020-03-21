package com.tsystems.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RoleDto {

    private Long id;

    private String name;

    private Set<UserDto> users;

}
