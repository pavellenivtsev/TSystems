package com.tsystems.dao.impl;

import com.tsystems.dao.api.CargoDao;
import com.tsystems.entity.Cargo;
import com.tsystems.enumaration.UserOrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CargoDaoImpl extends AbstractGenericDao<Cargo> implements CargoDao {

    /**
     * Finds all cargo for completed or carried orders
     *
     * @return List<Cargo>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Cargo> findAllCargoForCompletedOrCarriedOrders() {
        return (List<Cargo>) getSession().
                createQuery("from Cargo where userOrder.status=?1 or userOrder.status=?2 " +
                        "order by userOrder.creationDate DESC")
                .setParameter(1, UserOrderStatus.COMPLETED)
                .setParameter(2,UserOrderStatus.TAKEN).list();
    }
}
