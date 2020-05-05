package com.tsystems.service.impl;

import com.tsystems.dao.api.OfficeDao;
import com.tsystems.dto.OfficeDto;
import com.tsystems.entity.Office;
import com.tsystems.exception.DataChangingException;
import com.tsystems.service.api.OfficeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {
    private static final Logger LOGGER = LogManager.getLogger(OfficeServiceImpl.class);

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
    @Transactional(readOnly = true)
    public List<OfficeDto> findAll() {
        return officeDao.findAll().stream()
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
    @Transactional(readOnly = true)
    public OfficeDto findById(final long id) {
        final Office office = Optional.of(id)
                .map(officeDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Office with id: " + id + " does not exist"));
        return modelMapper.map(office, OfficeDto.class);
    }

    /**
     * Saves new office
     *
     * @param officeDto - office
     * @return true if office was saved
     */
    @Override
    @Transactional
    public boolean save(final @NonNull OfficeDto officeDto) {
        officeDao.save(modelMapper.map(officeDto, Office.class));
        LOGGER.info("Created a new office with name " + officeDto.getTitle());
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
    public boolean deleteById(final long id) {
        final Office office = Optional.of(id)
                .map(officeDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Office with id: " + id + " does not exist"));
        if (CollectionUtils.isNotEmpty(office.getTruckList())) {
            throw new DataChangingException("There are trucks in this office");
        }
        officeDao.delete(office);
        LOGGER.info("Deleted the office with name " + office.getTitle());
        return true;
    }
}
