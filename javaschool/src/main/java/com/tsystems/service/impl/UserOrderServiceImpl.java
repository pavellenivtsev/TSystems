package com.tsystems.service.impl;

import com.tsystems.dao.api.*;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.exception.DataChangingException;
import com.tsystems.service.api.GeneratorService;
import com.tsystems.service.api.JMSSenderService;
import com.tsystems.service.api.UserOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private JMSSenderService jmsSenderService;

    @Autowired
    private GeneratorService generatorService;

    /**
     * Finds all orders
     *
     * @return List<UserOrderDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserOrderDto> findAllSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllSortedByDate();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder, UserOrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Finds all completed orders
     *
     * @return List<UserOrderDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserOrderDto> findAllCompletedSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllCompletedSortedByDate();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder, UserOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserOrderDto> findAllTakenSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllTakenSortedByDate();
        return userOrderList.stream()
                .map(userOrder -> modelMapper.map(userOrder, UserOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserOrderDto> findAllNotTakenSortedByDate() {
        List<UserOrder> userOrderList = userOrderDao.findAllNotTakenSortedByDate();
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
        String uniqueNumber = generatorService.generateOrderUniqueNumber();
        while (userOrderDao.findByUniqueNumber(uniqueNumber) != null) {
            uniqueNumber = generatorService.generateOrderUniqueNumber();
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
    @Transactional(readOnly = true)
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
            throw new DataChangingException("Cant delete this order");
        }
        userOrderDao.delete(userOrder);
        LOGGER.info("An order with unique number " + userOrder.getUniqueNumber() + " was deleted ");
        jmsSenderService.sendMessage();
        return true;
    }
}
