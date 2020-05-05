package com.tsystems.service.impl;

import com.tsystems.dao.api.CargoDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.CargoDto;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.exception.DataChangingException;
import com.tsystems.service.api.CargoService;
import com.tsystems.service.api.JMSSenderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CargoServiceImpl implements CargoService {
    private static final Logger LOGGER = LogManager.getLogger(CargoServiceImpl.class);

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private CargoDao cargoDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JMSSenderService jmsSenderService;

    /**
     * Finds cargo by id
     *
     * @param id - cargo id
     * @return CargoDto
     */
    @Override
    @Transactional(readOnly = true)
    public CargoDto findById(final long id) {
        return modelMapper.map(findCargoById(id), CargoDto.class);
    }

    /**
     * Adds cargo to order by order id
     *
     * @param cargoDto - cargo
     * @param orderId  - order id
     * @return true if cargo is added
     */
    @Override
    @Transactional
    public boolean addCargoToOrder(final @NonNull CargoDto cargoDto, final long orderId) {
        final UserOrder userOrder = Optional.of(orderId)
                .map(userOrderDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " does not exist"));
        if (!userOrder.getStatus().equals(UserOrderStatus.NOT_TAKEN)) {
            throw new DataChangingException("The order must not be taken");
        }
        Cargo cargo = modelMapper.map(cargoDto, Cargo.class);
        cargo.setStatus(CargoStatus.PREPARED);
        cargo.setUserOrder(userOrder);
        cargoDao.save(cargo);
        LOGGER.info("Created a new cargo with name " + cargo.getName() +
                " for order with unique number " + userOrder.getUniqueNumber());
        return true;
    }

    /**
     * Edit cargo
     *
     * @param cargoDto - cargo
     * @return true if cargo was edited
     */
    @Override
    @Transactional
    public boolean editCargo(final @NonNull CargoDto cargoDto) {
        final Cargo cargo = findCargoById(cargoDto.getId());
        UserOrder userOrder = cargo.getUserOrder();
        if (!userOrder.getStatus().equals(UserOrderStatus.NOT_TAKEN)) {
            throw new DataChangingException("The order must not be taken");
        }
        cargo.setName(cargoDto.getName());
        cargo.setWeight(cargoDto.getWeight());
        cargo.setLoadingAddress(cargoDto.getLoadingAddress());
        cargo.setLoadingLatitude(cargoDto.getLoadingLatitude());
        cargo.setLoadingLongitude(cargoDto.getLoadingLongitude());
        cargo.setUnloadingAddress(cargoDto.getUnloadingAddress());
        cargo.setUnloadingLatitude(cargoDto.getUnloadingLatitude());
        cargo.setUnloadingLongitude(cargoDto.getUnloadingLongitude());
        return true;
    }

    /**
     * Delete cargo
     *
     * @param cargoId - cargo id
     * @return true if cargo was deleted
     */
    @Override
    @Transactional
    public boolean deleteCargo(final long cargoId) {
        final Cargo cargo = findCargoById(cargoId);
        UserOrder userOrder = cargo.getUserOrder();
        if (!userOrder.getStatus().equals(UserOrderStatus.NOT_TAKEN)) {
            throw new DataChangingException("The order must not be taken");
        }
        cargoDao.delete(cargo);
        LOGGER.info("Deleted the cargo with name " + cargo.getName() +
                " for order with unique number " + userOrder.getUniqueNumber());
        return true;
    }

    /**
     * Changes the cargo status to "delivered"
     *
     * @param id - cargo id
     * @return true if status was changed
     */
    @Override
    @Transactional
    public boolean setStatusDelivered(final long id) {
        final Cargo cargo = findCargoById(id);
        if (!cargo.getUserOrder().getStatus().equals(UserOrderStatus.TAKEN) &&
                !cargo.getStatus().equals(CargoStatus.SHIPPED) &&
                !cargo.getUserOrder().getTruck().getStatus().equals(TruckStatus.ON_DUTY)) {
            throw new DataChangingException("Status changing error");
        }
        cargo.setStatus(CargoStatus.DELIVERED);
        cargo.getUserOrder().getTruck().setAddress(cargo.getUnloadingAddress());
        cargo.getUserOrder().getTruck().setLatitude(cargo.getUnloadingLatitude());
        cargo.getUserOrder().getTruck().setLongitude(cargo.getUnloadingLongitude());
        LOGGER.info("For an order with the number " + cargo.getUserOrder().getUniqueNumber() +
                ", a cargo with the name " + cargo.getName() + " was delivered");
        jmsSenderService.sendMessage();
        return true;
    }

    /**
     * Changes the cargo status to "shipped"
     *
     * @param id - cargo id
     * @return true if status was changed
     */
    @Override
    @Transactional
    public boolean setStatusShipped(final long id) {
        final Cargo cargo = findCargoById(id);
        if (!cargo.getUserOrder().getStatus().equals(UserOrderStatus.TAKEN) &&
                !cargo.getStatus().equals(CargoStatus.PREPARED) &&
                !cargo.getUserOrder().getTruck().getStatus().equals(TruckStatus.ON_DUTY)) {
            throw new DataChangingException("Status changing error");
        }
        cargo.setStatus(CargoStatus.SHIPPED);
        cargo.getUserOrder().getTruck().setAddress(cargo.getLoadingAddress());
        cargo.getUserOrder().getTruck().setLatitude(cargo.getLoadingLatitude());
        cargo.getUserOrder().getTruck().setLongitude(cargo.getLoadingLongitude());
        LOGGER.info("For an order with the number " + cargo.getUserOrder().getUniqueNumber() +
                ", a cargo with the name " + cargo.getName() + " was shipped");
        jmsSenderService.sendMessage();
        return true;
    }

    /**
     * Finds cargo by id
     *
     * @param id - cargo id
     * @return Cargo
     */
    private Cargo findCargoById(final long id) {
        return Optional.of(id)
                .map(cargoDao::findById)
                .orElseThrow(() -> new EntityNotFoundException("Cargo with id: " + id + " does not exist"));
    }
}
