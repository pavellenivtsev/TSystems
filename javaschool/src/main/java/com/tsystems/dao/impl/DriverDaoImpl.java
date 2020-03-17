package com.tsystems.dao.impl;

import com.tsystems.dao.api.DriverDao;
import com.tsystems.entity.Driver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DriverDaoImpl extends AbstractGenericDao<Driver> implements DriverDao {
}
