package com.tsystems.service.impl;

import com.tsystems.service.api.JMSSenderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JMSSenderServiceImpl implements JMSSenderService {
    private static final Logger LOGGER = LogManager.getLogger(JMSSenderServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage() {
        try {
            jmsTemplate.send(session -> session.createTextMessage("Update the tables"));
            LOGGER.info("jms was sent");
        } catch (RuntimeException e) {
            LOGGER.warn("Unable to send a jms");
            LOGGER.warn(e.getMessage());
        }
    }
}
