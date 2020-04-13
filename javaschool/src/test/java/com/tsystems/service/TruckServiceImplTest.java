package com.tsystems.service;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.dto.TruckDto;

import com.tsystems.entity.Driver;
import com.tsystems.service.impl.TruckServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class TruckServiceImplTest {

    @Mock
    private TruckDao truckDao;

    @InjectMocks
    private TruckServiceImpl truckService;

    @Before
    public void initData() {
        TruckDto truckDto=new TruckDto();
        Driver driver = new Driver();
        driver.setId(1L);
    }

    @Test
    public void testAddDriver(){
       List<TruckDto> truckList= truckService.findAllAvailable(12);
       System.out.println(truckList.isEmpty());
    }
}
