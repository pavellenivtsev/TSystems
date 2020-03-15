package com.tsystems.dao.impl;

import com.tsystems.dao.api.TruckDao;
import com.tsystems.entity.Truck;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TruckDaoImpl extends AbstractGenericDao<Truck> implements TruckDao {
}
