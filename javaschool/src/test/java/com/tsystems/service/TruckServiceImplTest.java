package com.tsystems.service;


import com.tsystems.dao.api.TruckDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.entity.Truck;
import com.tsystems.entity.UserOrder;
import com.tsystems.service.impl.TruckServiceImpl;
import com.tsystems.utils.TruckPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class TruckServiceImplTest {

    @InjectMocks
    private TruckServiceImpl truckService;

    @Mock
    private UserOrderDao userOrderDao;

    @Mock
    private TruckDao truckDao;

    private UserOrder userOrder;
    private List<Truck> truckList;

    @Before
    public void initData() {

    }

    @Test
    public void isLimitForDriversExceededTest() {



    }
}
