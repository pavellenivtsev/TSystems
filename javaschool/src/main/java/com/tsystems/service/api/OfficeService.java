package com.tsystems.service.api;

import com.tsystems.dto.OfficeDto;

import java.util.List;

public interface OfficeService {
    List<OfficeDto> findAll();

    boolean save(OfficeDto officeDto);

    boolean deleteById(long id);

    OfficeDto findById(long id);
}
