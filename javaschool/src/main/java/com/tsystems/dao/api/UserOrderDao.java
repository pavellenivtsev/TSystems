package com.tsystems.dao.api;

import com.tsystems.entity.UserOrder;

import java.util.List;

public interface UserOrderDao extends GenericDao<UserOrder> {
    List<UserOrder> findAllSortedByDate();

    UserOrder findByUniqueNumber(String uniqueNumber);

    List<UserOrder> findAllCompletedOrCarried();
}
