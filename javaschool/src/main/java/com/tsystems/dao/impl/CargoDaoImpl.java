package com.tsystems.dao.impl;

import com.tsystems.dao.api.CargoDao;
import com.tsystems.entity.Cargo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CargoDaoImpl extends AbstractGenericDao<Cargo> implements CargoDao {

}
