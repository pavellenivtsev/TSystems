package com.tsystems.dao.api;

import com.tsystems.entity.Cargo;

import java.util.List;

public interface CargoDao extends GenericDao<Cargo>{

    List<Cargo> findAllCargoForCompletedOrCarriedOrders();
}
