package com.tsystems.dao.impl;

import com.tsystems.dao.api.LocationDao;
import com.tsystems.entity.Location;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocationDaoImpl extends AbstractGenericDao<Location> implements LocationDao {
}
