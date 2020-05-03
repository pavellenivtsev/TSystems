package com.tsystems.service;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.DriverDto;
import com.tsystems.entity.*;
import com.tsystems.service.api.CountingService;
import com.tsystems.service.impl.CountingServiceImpl2;
import com.tsystems.service.impl.TruckServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class TruckServiceImplTest {

    @InjectMocks
    private TruckServiceImpl truckService;

    @Mock
    private UserOrderDao userOrderDao;

    @Mock
    private TruckDao truckDao;

    @Mock
    private DriverDao driverDao;

    private UserOrder userOrder;
    private List<Truck> truckList;
    private Truck truck;
    private List<Driver> driverList;

    private CountingService countingService = new CountingServiceImpl2();

//    @Before
    public void initData() {
        truck = Truck.builder()
                .office(Office.builder()
                        .address("Saint-Petersburg")
                        .latitude(59.938732)
                        .longitude(30.316229)
                        .build())
                .build();

        driverList = Arrays.asList(
                Driver.builder()
                        .user(User.builder()
                                .address("Vologda")
                                .latitude(59.218876)
                                .longitude(39.893276)
                                .build())
                        .id(1)
                        .build(),
                Driver.builder()
                        .user(User.builder()
                                .address("Saint-Petersburg")
                                .latitude(59.938732)
                                .longitude(30.316229)
                                .build())
                        .id(2)
                        .build(),
                Driver.builder()
                        .user(User.builder()
                                .address("Saint-Petersburg")
                                .latitude(59.938732)
                                .longitude(30.316229)
                                .build())
                        .id(3)
                        .build());
    }

//    @Test
    public void findAllAvailableDriversTest() {
        when(truckDao.findById(anyInt())).thenReturn(truck);
        when(driverDao.findAllDriversWithoutTruck()).thenReturn(driverList);

        assertArrayEquals(new Integer[]{2, 3}, truckService.findAllAvailableDrivers(anyInt())
                .stream().map(DriverDto::getId).toArray());
    }
}
