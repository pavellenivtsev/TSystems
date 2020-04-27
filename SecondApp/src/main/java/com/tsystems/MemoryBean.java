package com.tsystems;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dto.EntryDto;
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
    @Push
    private PushContext push;

    private List<UserOrderDto> userOrderDtoList;
    private List<EntryDto> countTable;

    @PostConstruct
    public void init() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)");
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
                        refreshData();
                    } catch (JMSException | IOException e) {
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
        push.send("updateOrders");

        //update count table
        countTable= new LinkedList<>();
        countTable.addAll(Arrays.asList(objectMapper.readValue(
                restService.executeRequest("http://localhost:8081/secondapp/count-table"),
                EntryDto[].class)));
        push.send("updateCountTable");
    }

    public List<UserOrderDto> getUserOrderDtoList() {
        return userOrderDtoList;
    }

    public void setUserOrderDtoList(List<UserOrderDto> userOrderDtoList) {
        this.userOrderDtoList = userOrderDtoList;
    }

    public List<EntryDto> getCountTable() {
        return countTable;
    }

    public void setCountTable(List<EntryDto> countTable) {
        this.countTable = countTable;
    }
}
