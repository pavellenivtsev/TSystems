package com.tsystems.dao.impl;

import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.entity.UserOrder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserOrderDaoImpl extends AbstractGenericDao<UserOrder> implements UserOrderDao {
}
