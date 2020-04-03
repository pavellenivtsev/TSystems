package com.tsystems.service.impl;

import com.tsystems.dao.api.CargoDao;
import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.dto.CargoDto;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.CargoStatus;
import com.tsystems.enumaration.TruckStatus;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.service.api.CargoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CargoServiceImpl implements CargoService {
    private static final Logger LOGGER = LogManager.getLogger(CargoServiceImpl.class);

    private final UserOrderDao userOrderDao;

    private final CargoDao cargoDao;

    private final ModelMapper modelMapper;

    @Autowired
    public CargoServiceImpl(UserOrderDao userOrderDao, CargoDao cargoDao, ModelMapper modelMapper) {
        this.userOrderDao = userOrderDao;
        this.cargoDao = cargoDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CargoDto findById(long id) {
        return convertToDto(cargoDao.findById(id));
    }

    /**
     * Adds cargo to order by order id
     *
     * @param cargoDto
     * @param orderId
     * @return true if cargo is added
     */
    @Override
    @Transactional
    public boolean addCargoToOrder(CargoDto cargoDto, long orderId) {
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }

        //create new cargo
        Cargo cargo = convertToEntity(cargoDto);
        cargo.setStatus(CargoStatus.PREPARED);
        cargo.setUserOrder(userOrder);
        cargoDao.save(cargo);

        LOGGER.info("Created a new cargo with name " + cargo.getName() + " for order with unique number " + userOrder.getUniqueNumber());
        return true;
    }

    /**
     * Edit cargo
     *
     * @param cargoDto
     * @param orderId
     * @return true if cargo was edited
     */
    @Override
    @Transactional
    public boolean editCargo(CargoDto cargoDto, long orderId) {
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }

        //change cargo
        Cargo cargo = cargoDao.findById(cargoDto.getId());
        cargo.setName(cargoDto.getName());
        cargo.setWeight(cargoDto.getWeight());
        cargoDao.update(cargo);
        return true;
    }

    /**
     * Delete cargo
     *
     * @param cargoId
     * @param orderId
     * @return true if cargo was deleted
     */
    @Override
    @Transactional
    public boolean deleteCargo(long cargoId, long orderId) {
        UserOrder userOrder = userOrderDao.findById(orderId);
        if (userOrder.getStatus().equals(UserOrderStatus.TAKEN)) {
            return false;
        }
        Cargo cargo = cargoDao.findById(cargoId);
        LOGGER.info("Deleted the cargo with name " + cargo.getName() + " for order with unique number " + userOrder.getUniqueNumber());
        cargoDao.delete(cargo);
        return true;
    }

    /**
     * Changes the cargo status to "delivered"
     * @param id
     * @return true if status was changed
     */
    @Override
    @Transactional
    public boolean setStatusDelivered(long id) {
        Cargo cargo= cargoDao.findById(id);
        if (cargo.getUserOrder().getStatus().equals(UserOrderStatus.TAKEN)&&
                cargo.getStatus().equals(CargoStatus.SHIPPED)&&
                cargo.getUserOrder().getTruck().getStatus().equals(TruckStatus.ON_DUTY)){

            cargo.setStatus(CargoStatus.DELIVERED);
            cargoDao.update(cargo);
            return true;
        }
        return false;
    }

    /**
     * Changes the cargo status to "shipped"
     * @param id
     * @return true if status was changed
     */
    @Override
    @Transactional
    public boolean setStatusShipped(long id) {
        Cargo cargo= cargoDao.findById(id);
        if (cargo.getUserOrder().getStatus().equals(UserOrderStatus.TAKEN)&&
                cargo.getStatus().equals(CargoStatus.PREPARED)&&
                cargo.getUserOrder().getTruck().getStatus().equals(TruckStatus.ON_DUTY)){

            cargo.setStatus(CargoStatus.SHIPPED);
            cargoDao.update(cargo);
            return true;
        }
        return false;
    }

    private Cargo convertToEntity(CargoDto cargoDto) {
        return modelMapper.map(cargoDto, Cargo.class);
    }

    private CargoDto convertToDto(Cargo cargo) {
        return modelMapper.map(cargo, CargoDto.class);
    }

}
