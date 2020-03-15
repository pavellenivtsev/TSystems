package com.tsystems.dao.api;

import com.tsystems.entity.User;

public interface UserDao extends GenericDao<User> {
    User findByUsername(String username);

}
