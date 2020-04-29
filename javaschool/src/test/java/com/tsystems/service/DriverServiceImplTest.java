package com.tsystems.service;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Truck;
import com.tsystems.enumaration.DriverStatus;
import com.tsystems.service.api.DriverService;
import com.tsystems.service.impl.DriverServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class DriverServiceImplTest {

//    @InjectMocks
//    private DriverService driverService = new DriverServiceImpl();
//
//    @Mock
//    private DriverDao driverDao;
//
//    private DateTime currentDate;
//
//    private Driver driver;
//
//    private List<Driver> driverList;
//    @Before
//    public void init(){
//
//
//        currentDate = new DateTime("2018-06-05T10:11:12.123");
//
//        driver = Driver.builder()
//                .shiftStartTime(new DateTime("2018-05-05T10:11:12.123"))
//                .status(DriverStatus.REST)
//                .truck(new Truck())
//                .build();
//
//
//    }
//
//    @Test
//    public void changeDriverStatusToOnShiftTest(){
//        when(driverDao.findById(any())).thenReturn(driver);
//        when(new DateTime()).thenReturn(currentDate);
//
//    }
}
