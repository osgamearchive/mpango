package net.sf.mpango.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *
 */
public interface DAO<T extends Identified<K>, K extends Serializable> {
    /**
     * Saves data to database
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
