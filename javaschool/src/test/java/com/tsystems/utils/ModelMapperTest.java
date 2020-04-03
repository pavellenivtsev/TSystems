package com.tsystems.utils;

import com.tsystems.dto.LocationDto;
import com.tsystems.dto.RoleDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserDto;
import com.tsystems.entity.Location;
import com.tsystems.entity.Role;

import com.tsystems.entity.Truck;
import com.tsystems.entity.User;
import com.tsystems.enumaration.TruckStatus;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class ModelMapperTest {

    private ModelMapper modelMapper=new ModelMapper();

    @Test
    public void testDtoEntityConverter(){
        User user =new User();
        user.setFirstName("Pavel");
        user.setLastName("Lenivtsev");
        user.setEmail("someMail@gmail.com");
        user.setPhoneNumber("+79523906348");
        user.setPassword("12345");
        Role role=new Role(1L,"ROLE_USER");
        user.setRoles(Collections.singleton(role));

        UserDto userDto=modelMapper.map(user,UserDto.class);

        Iterator<RoleDto> iterator=userDto.getRoles().iterator();
        RoleDto roleDto=new RoleDto();
        while(iterator.hasNext()){
            roleDto=iterator.next();
        }

        assertEquals(user.getFirstName(),userDto.getFirstName());
        assertEquals(user.getLastName(),userDto.getLastName());
        assertEquals(user.getEmail(),userDto.getEmail());
        assertEquals(user.getPhoneNumber(),userDto.getPhoneNumber());
        assertEquals(user.getPassword(),userDto.getPassword());
        assertEquals(roleDto.getName(),"ROLE_USER");
    }

    @Test
    public void testEntityDtoConverter(){
        LocationDto locationDto= new LocationDto();
        locationDto.setCity("Moscow");
        locationDto.setLatitude(55.755826);
        locationDto.setLongitude(37.6172999);
        TruckDto truckDto= new TruckDto();
        truckDto.setLocation(locationDto);
        truckDto.setRegistrationNumber("RR11111");
        truckDto.setWeightCapacity(1000);
        truckDto.setDriverShiftSize(8);
        truckDto.setStatus(TruckStatus.ON_DUTY);
        Truck truck =modelMapper.map(truckDto,Truck.class);

        Location location=truck.getLocation();
        assertEquals(truckDto.getRegistrationNumber(),truck.getRegistrationNumber());
        assertEquals(truckDto.getWeightCapacity(),truck.getWeightCapacity(),0.1);
        assertEquals(truckDto.getDriverShiftSize(),truck.getDriverShiftSize(),0.1);
        assertEquals(truckDto.getStatus(),truck.getStatus());
        assertEquals(locationDto.getCity(),location.getCity());
        assertEquals(locationDto.getLatitude(),location.getLatitude(),0.1);
        assertEquals(locationDto.getLongitude(),location.getLongitude(),0.1);
    }
}
