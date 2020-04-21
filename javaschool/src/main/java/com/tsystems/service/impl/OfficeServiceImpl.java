package com.tsystems.service.impl;

import com.tsystems.dao.api.OfficeDao;
import com.tsystems.dto.OfficeDto;
import com.tsystems.entity.Office;
import com.tsystems.service.api.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Finds all offices
     *
     * @return List<OfficeDto>
     */
    @Override
    @Transactional
    public List<OfficeDto> findAll() {
        List<Office> offices = officeDao.findAll();
        return offices.stream()
                .map(office -> modelMapper.map(office, OfficeDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Finds office by id
     *
     * @param id - office id
     * @return OfficeDto
     */
    @Override
    @Transactional
    public OfficeDto findById(long id) {
        return modelMapper.map(officeDao.findById(id), OfficeDto.class);
    }

    /**
     * Saves new office
     *
     * @param officeDto - office
     * @return true if office was saved
     */
    @Override
    @Transactional
    public boolean save(OfficeDto officeDto) {
        Office office = modelMapper.map(officeDto, Office.class);
        officeDao.save(office);
        return true;
    }

    /**
     * Deletes office by id
     *
     * @param id - office id
     * @return true if office was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        Office office = officeDao.findById(id);
        if (!office.getTruckList().isEmpty()) {
            return false;
        }
        officeDao.delete(office);
        return true;
    }
}
