package com.tsystems.service;

import com.tsystems.service.impl.CountingServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountingServiceImplTest {

    CountingServiceImpl service = new CountingServiceImpl();

    @Test
    public void testGetDistanceLength() {
        double lat1=59.9310709;
        double long1=30.3608153;
        double lat2=55.7562903;
        double long2=37.6170739;
        assertEquals( 631, service.getDistanceLength(lat1, long1,lat2, long2));
    }
}
