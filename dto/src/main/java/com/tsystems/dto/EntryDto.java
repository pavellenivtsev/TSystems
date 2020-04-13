package com.tsystems.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryDto {
    private String name;

    private int value;

    public EntryDto(String name, int value) {
        this.name = name;
        this.value = value;
    }
}