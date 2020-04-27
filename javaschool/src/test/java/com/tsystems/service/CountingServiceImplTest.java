package com.tsystems.service;

import com.tsystems.dto.CargoDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.impl.CountingServiceImpl;
import com.tsystems.utils.TruckPair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CountingServiceImplTest {

    CountingServiceImpl service = new CountingServiceImpl();

    @Test
    public void testGetDistanceLength() {
        double lat1 = 59.9310709;
        double long1 = 30.3608153;
        double lat2 = 55.7562903;
        double long2 = 37.6170739;
        assertEquals(631, service.getDistanceLength(lat1, long1, lat2, long2));
    }

    @Test
    public void testGetApproximatelyTotalDistanceForTruckAndOrder() {
        TruckDto truckDto1 = TruckDto.builder()
                .registrationNumber("AA11111")
                .weightCapacity(500)
                .latitude(55.7504461)
                .longitude(37.6174943)
                .build();

        TruckDto truckDto2 = TruckDto.builder()
                .registrationNumber("BB11111")
                .weightCapacity(500)
                .latitude(59.938732)
                .longitude(30.316229)
                .build();

        TruckDto truckDto3 = TruckDto.builder()
                .registrationNumber("CC11111")
                .weightCapacity(120)
                .latitude(55.7504461)
                .longitude(37.6174943)
                .build();

        TruckDto truckDto4 = TruckDto.builder()
                .registrationNumber("DD11111")
                .weightCapacity(500)
                .latitude(57.35023748)
                .longitude(61.94091797)
                .build();

        List<TruckDto> truckDtoList = new ArrayList<>();
        truckDtoList.add(truckDto1);
        truckDtoList.add(truckDto2);
        truckDtoList.add(truckDto3);
        truckDtoList.add(truckDto4);

        CargoDto cargoDto1 = CargoDto.builder()
                .weight(50)
                .loadingAddress("Vologda")
                .loadingLatitude(59.218876)
                .loadingLongitude(39.893276)
                .unloadingAddress("Ivanovo")
                .unloadingLatitude(56.9984452)
                .unloadingLongitude(40.9737394)
                .build();

        CargoDto cargoDto2 = CargoDto.builder()
                .weight(100)
                .loadingAddress("Moscow")
                .loadingLatitude(55.7504461)
                .loadingLongitude(37.6174943)
                .unloadingAddress("Saint-Petersburg")
                .unloadingLatitude(59.938732)
                .unloadingLongitude(30.316229)
                .build();

        List<CargoDto> cargoDtoList = new ArrayList<>();
        cargoDtoList.add(cargoDto1);
        cargoDtoList.add(cargoDto2);
        UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setCargoList(cargoDtoList);
        List<TruckPair> truckPairs = service.getApproximatelyTotalDistanceForTruckAndOrder(
                truckDtoList,
                userOrderDto);
        Collections.sort(truckPairs);

        assertEquals("BB11111", truckPairs.get(0).getTruckDto().getRegistrationNumber());
        assertEquals(3, truckPairs.size());
    }
}
