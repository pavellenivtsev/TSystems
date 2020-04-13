package com.tsystems;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dto.DriverDto;
import com.tsystems.dto.EntryDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.api.RestService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Named("memoryBean")
@ApplicationScoped
public class MemoryBean {
    private static final Logger LOGGER = LogManager.getLogger(MemoryBean.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private RestService restService;

    @Inject
    @Push(channel = "someChannel")
    private PushContext pushContext;

    private List<UserOrderDto> userOrderDtoList;
    private List<DriverDto> driverDtoList;
    private List<TruckDto> truckDtoList;
    private List<EntryDto> countTable;

//    failover://(tcp://activemq:61616)?initialReconnectDelay=2000&maxReconnectAttempts=5
//    tcp://localhost:61616
    @PostConstruct
    public void init() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://(tcp://activemq:61616)?maxReconnectAttempts=5");
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("testQ");
            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    TextMessage msg = (TextMessage) message;
                    try {
                        LOGGER.info("Received Message from queue: " + msg.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    try {
                        refreshData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    LOGGER.warn("Message of wrong type: " + message.getClass().getName());
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void refreshData() throws IOException {

        //update orders
        userOrderDtoList = new LinkedList<>();
        userOrderDtoList.addAll(Arrays.asList(objectMapper.readValue(
                restService.executeRequest("http://localhost:8081/secondapp/orders"),
                UserOrderDto[].class)));
        pushContext.send("updateOrders");

        //update drivers
        driverDtoList = new LinkedList<>();
        driverDtoList.addAll(Arrays.asList(objectMapper.readValue(
                restService.executeRequest("http://localhost:8081/secondapp/drivers"),
                DriverDto[].class)));
        pushContext.send("updateDrivers");

        //update trucks
        truckDtoList = new LinkedList<>();
        truckDtoList.addAll(Arrays.asList(objectMapper.readValue(
                restService.executeRequest("http://localhost:8081/secondapp/trucks"),
                TruckDto[].class)));
        pushContext.send("updateTrucks");

        //update count table
        int totalDriversCount = driverDtoList.size();


        final int[] driversOnShift = {0};
        final int[] driversRest = {0};

        driverDtoList.forEach(driverDto -> {
            switch (driverDto.getStatus()) {
                case ON_SHIFT:
                    driversOnShift[0]++;
                    break;
                case REST:
                    driversRest[0]++;
                    break;
                default:
                    LOGGER.error("Driver status is incorrect");
            }
        });

        final int[] trucksCarryingCargo = {0};
        final int[] freeTrucks = {0};
        final int[] faultyTrucks = {0};

        truckDtoList.forEach(truckDto -> {
            switch (truckDto.getStatus()) {
                case ON_DUTY:
                    if (truckDto.getUserOrder() == null) {
                        freeTrucks[0]++;
                    } else {
                        trucksCarryingCargo[0]++;
                    }
                    break;
                case FAULTY:
                    faultyTrucks[0]++;
                    break;
                default:
                    LOGGER.error("Truck status is incorrect");
            }
        });

        countTable.add(new EntryDto("Total drivers count", totalDriversCount));
        countTable.add(new EntryDto("Drivers on shift", driversOnShift[0]));
        countTable.add(new EntryDto("Drivers on vacation", driversRest[0]));
        countTable.add(new EntryDto("Trucks carrying an order", trucksCarryingCargo[0]));
        countTable.add(new EntryDto("Free trucks", freeTrucks[0]));
        countTable.add(new EntryDto("Faulty trucks", faultyTrucks[0]));

        pushContext.send("updateCountTable");
    }

    public List<UserOrderDto> getUserOrderDtoList() {
        return userOrderDtoList;
    }

    public void setUserOrderDtoList(List<UserOrderDto> userOrderDtoList) {
        this.userOrderDtoList = userOrderDtoList;
    }

    public List<DriverDto> getDriverDtoList() {
        return driverDtoList;
    }

    public void setDriverDtoList(List<DriverDto> driverDtoList) {
        this.driverDtoList = driverDtoList;
    }

    public List<TruckDto> getTruckDtoList() {
        return truckDtoList;
    }

    public void setTruckDtoList(List<TruckDto> truckDtoList) {
        this.truckDtoList = truckDtoList;
    }

    public List<EntryDto> getCountTable() {
        return countTable;
    }

    public void setCountTable(List<EntryDto> countTable) {
        this.countTable = countTable;
    }
}
