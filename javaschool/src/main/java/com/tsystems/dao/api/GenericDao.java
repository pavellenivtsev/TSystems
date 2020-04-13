package com.tsystems.dao.api;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<E> {

    /**
     * Find by primary key
     *
     * @param id
     * @return unique entity
     */
    E findById(Serializable id);

    /**
     * Find all records
     *
     * @return
     */
    List<E> findAll();

    /**
     * Save
     *
     * @param entity: entity to save
     * @return Identifier of saved entity
     */
    Serializable save(E entity);

    /**
     * Update
     *
     * @param entity:entity to update
     */
    void update(E entity);

    /**
     * Delete
     *
     * @param entity: entity to delete
     */
    void delete(E entity);

}
