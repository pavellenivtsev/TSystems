package com.tsystems.dao.impl;

import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.entity.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserOrderDaoImpl extends AbstractGenericDao<UserOrder> implements UserOrderDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllSortedByDate() {
        return (List<UserOrder>) getSession().createQuery("from UserOrder order by id DESC").list();
    }
}
