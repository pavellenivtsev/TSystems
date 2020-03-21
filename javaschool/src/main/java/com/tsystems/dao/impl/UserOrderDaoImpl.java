package com.tsystems.dao.impl;

import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.entity.UserOrder;
import org.springframework.stereotype.Repository;

@Repository
public class UserOrderDaoImpl extends AbstractGenericDao<UserOrder> implements UserOrderDao {
}
