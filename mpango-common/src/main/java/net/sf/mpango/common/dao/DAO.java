package net.sf.mpango.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 27/11/13
 *         Time: 20:03
 *
 */
public interface DAO<T extends Identified<K>, K extends Serializable> {
    /**
     * Saves data to database
     *
     * @param entity
     * @return
     */
    T save(T entity) throws AlreadyExistsException;

    /**
	 * Updates
	 * @param entity
	 */
    void update(T entity) throws NotFoundException;

    void delete(T entity) throws NotFoundException;

    T load(K identifier) throws NotFoundException;

    List<T> list();
}
