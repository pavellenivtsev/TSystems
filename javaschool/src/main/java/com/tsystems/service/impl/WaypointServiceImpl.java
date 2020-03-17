package com.tsystems.service.impl;

import com.tsystems.dao.api.WaypointDao;
import com.tsystems.dao.impl.WaypointDaoImpl;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.dto.WaypointDto;
import com.tsystems.entity.UserOrder;
import com.tsystems.entity.Waypoint;
import com.tsystems.service.api.WaypointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaypointServiceImpl implements WaypointService {
    private WaypointDao waypointDao=new WaypointDaoImpl();

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWaypointDao(WaypointDao waypointDao) {
        this.waypointDao = waypointDao;
    }

    @Override
    @Transactional
    public List<WaypointDto> findAll() {
        List<Waypoint> waypointList=waypointDao.findAll();
        List<WaypointDto> waypointDtoList=waypointList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return waypointDtoList;
    }

    @Override
    @Transactional
    public void save(WaypointDto waypointDto) {

    }

    @Override
    @Transactional
    public void update(WaypointDto waypointDto) {

    }

    @Override
    @Transactional
    public void delete(WaypointDto waypointDto) {

    }

    @Override
    @Transactional
    public WaypointDto findById(long id) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(long id) {

    }

    private WaypointDto convertToDto(Waypoint waypoint) {
        return modelMapper.map(waypoint, WaypointDto.class);
    }

    private Waypoint convertToEntity(WaypointDto waypointDto) {
        return modelMapper.map(waypointDto, Waypoint.class);
    }
}
