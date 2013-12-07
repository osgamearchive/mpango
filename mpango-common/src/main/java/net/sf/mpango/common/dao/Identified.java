package net.sf.mpango.common.dao;

import java.io.Serializable;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 27/11/13
 *         Time: 20:31
 */
public interface Identified<T extends Serializable> {

    T getId();
    void setId(T identifier);
}
