package com.tsystems.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntryDto {
    private String name;

    private Long value;

    public EntryDto(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}