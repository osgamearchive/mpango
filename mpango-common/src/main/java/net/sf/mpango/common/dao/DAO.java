package net.sf.mpango.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 27/11/13
 *         Time: 20:03
 * @param T Entity Object
 */
public interface DAO<T extends Identified> {
    /**
     * Saves data to database
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
	 * Updates
	 * @param entity
	 */
    void update(T entity);

    T load(Serializable identifier);

    List<T> list();
}
