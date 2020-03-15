package com.tsystems.dao.impl;

import com.tsystems.dao.api.UserDao;
import com.tsystems.entity.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDaoImpl extends AbstractGenericDao<User> implements UserDao {

    @SuppressWarnings("unchecked")
    @Override
    public User findByUsername(String username) {

        List<User> userList= getSession()
                .createQuery("from User where username=?0")
                .setParameter(0, username)
                .list();
       return userList.isEmpty()? null:userList.get(0);
    }
}
