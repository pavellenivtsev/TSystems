package com.tsystems.dao.impl;

import com.tsystems.dao.api.WaypointDao;
import com.tsystems.entity.Waypoint;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WaypointDaoImpl extends AbstractGenericDao<Waypoint> implements WaypointDao {
}
