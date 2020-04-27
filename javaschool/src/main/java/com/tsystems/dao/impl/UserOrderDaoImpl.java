package com.tsystems.dao.impl;

import com.tsystems.dao.api.UserOrderDao;
import com.tsystems.entity.UserOrder;
import com.tsystems.enumaration.UserOrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserOrderDaoImpl extends AbstractGenericDao<UserOrder> implements UserOrderDao {

    /**
     * Finds all orders sorted by date
     *
     * @return List<UserOrder>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllSortedByDate() {
        return (List<UserOrder>) getSession().createQuery("from UserOrder order by creationDate DESC").list();
    }

    /**
     * Finds all completed orders sorted by date
     *
     * @return List<UserOrder>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllCompletedSortedByDate() {
        return (List<UserOrder>) getSession().createQuery("from UserOrder where status=?1 order by creationDate DESC")
                .setParameter(1, UserOrderStatus.COMPLETED)
                .list();
    }

    /**
     * Finds all taken orders sorted by date
     *
     * @return List<UserOrder>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllTakenSortedByDate() {
        return (List<UserOrder>) getSession().createQuery("from UserOrder where status=?1 order by creationDate DESC")
                .setParameter(1, UserOrderStatus.TAKEN)
                .list();
    }

    /**
     * Finds all not taken orders sorted by date
     *
     * @return List<UserOrder>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllNotTakenSortedByDate() {
        return (List<UserOrder>) getSession().createQuery("from UserOrder where status=?1 order by creationDate DESC")
                .setParameter(1, UserOrderStatus.NOT_TAKEN)
                .list();
    }

    /**
     * Finds order by unique number
     *
     * @param uniqueNumber - unique number
     * @return UserOrder
     */
    @SuppressWarnings("unchecked")
    @Override
    public UserOrder findByUniqueNumber(String uniqueNumber) {
        List<UserOrder> userOrderList = getSession()
                .createQuery("from UserOrder where uniqueNumber=?0")
                .setParameter(0, uniqueNumber)
                .list();
        return userOrderList.isEmpty() ? null : userOrderList.get(0);
    }

    /**
     * Finds all completed or carried orders
     *
     * @return List<UserOrder>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrder> findAllCompletedOrCarried() {
        return (List<UserOrder>) getSession()
                .createQuery("from UserOrder where status=?1 or status=?2 order by creationDate DESC")
                .setParameter(1, UserOrderStatus.COMPLETED)
                .setParameter(2, UserOrderStatus.TAKEN).list();
    }
}
