package com.tsystems.service.impl;

import com.tsystems.dao.api.*;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.service.api.UserOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    private static final Logger LOGGER = LogManager.getLogger(UserOrderServiceImpl.class);

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Finds all orders
     *
     * @return List<UserOrderDto>
     */
    @Override
    @Transactional
    public List<UserOrderDto> findAllSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllSortedByDate();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder, UserOrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Creates new order
     *
     * @return true if order was created
     */
    @Override
    @Transactional
    public boolean addOrder() {
        String uniqueNumber = generateUniqueNumber();
        while (userOrderDao.findByUniqueNumber(uniqueNumber) != null) {
            uniqueNumber = generateUniqueNumber();
        }
        UserOrder userOrder = new UserOrder();
        userOrder.setCreationDate(new DateTime());
        userOrder.setStatus(UserOrderStatus.NOT_TAKEN);
        userOrder.setUniqueNumber(uniqueNumber);
        userOrderDao.save(userOrder);
        LOGGER.info("Created a new order with unique number " + userOrder.getUniqueNumber());
        return true;
    }

    /**
     * Finds order by id
     *
     * @param id order id
     * @return UserOrderDto
     */
    @Override
    @Transactional
    public UserOrderDto findById(long id) {
        return modelMapper.map(userOrderDao.findById(id), UserOrderDto.class);
    }

    /**
     * Delete order by id
     *
     * @param id order id
     * @return true if order was deleted
     */
    @Override
    @Transactional
    public boolean deleteById(long id) {
        UserOrder userOrder = userOrderDao.findById(id);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }
        userOrderDao.delete(userOrder);
        LOGGER.info("An order with unique number " + userOrder.getUniqueNumber() + " was deleted ");
        return true;
    }

    /**
     * Generates a random sequence
     *
     * @return String
     */
    private String generateUniqueNumber() {
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
