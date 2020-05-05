package com.tsystems.service;


import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.TruckDto;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;
import com.tsystems.service.impl.TruckServiceImpl;
import com.tsystems.utils.TruckPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.anyInt;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(TruckServiceImpl.class)
public class TruckServiceImplTest2 {

    @Spy
    private TruckServiceImpl truckService;

    @Mock
    private UserOrderDao userOrderDao;

    @Mock
    private TruckDao truckDao;

    private UserOrder userOrder;
    private List<Truck> truckList;

//    @Before
    public void init() {
        userOrder = UserOrder.builder()
                .cargoList(Arrays.asList(
                        Cargo.builder()
                                .weight(100)
                                .loadingAddress("Saint-Petersburg")
                                .loadingLatitude(59.938732)
                                .loadingLongitude(30.316229)
                                .unloadingAddress("Ivanovo")
                                .unloadingLatitude(56.9984452)
                                .unloadingLongitude(40.9737394)
                                .build(),
                        Cargo.builder()
                                .weight(100)
                                .loadingAddress("Saint-Petersburg")
                                .loadingLatitude(59.938732)
                                .loadingLongitude(30.316229)
                                .unloadingAddress("Vologda")
                                .unloadingLatitude(59.218876)
                                .unloadingLongitude(39.893276)
                                .build()))
                .build();

        truckList = Arrays.asList(
                Truck.builder()
                        .registrationNumber("AA11111")
                        .weightCapacity(100)
                        .address("Vologda")
                        .latitude(59.218876)
                        .longitude(39.893276)
                        .build(),
                Truck.builder()
                        .registrationNumber("BB11111")
                        .weightCapacity(300)
                        .address("Saint-Petersburg")
                        .latitude(59.938732)
                        .longitude(30.316229)
                        .build(),
                Truck.builder()
                        .registrationNumber("CC11111")
                        .weightCapacity(250)
                        .address("Saint-Petersburg")
                        .latitude(59.938732)
                        .longitude(30.316229)
                        .build());
    }

//    @Test
    public void findAllAvailableTest() throws Exception {
        PowerMockito.when(userOrderDao.findById(anyInt())).thenReturn(userOrder);
        PowerMockito.when(truckDao.findAllAvailable()).thenReturn(truckList);
        PowerMockito.doReturn(true).when(truckService, "isLimitForDriversNotExceeded", Matchers.anyObject());
        assertArrayEquals(new String[]{"CC11111", "BB11111"},
                truckService.findAllAvailable(anyInt()).stream()
                        .map(TruckPair::getTruckDto).map(TruckDto::getRegistrationNumber).toArray());
    }
}
