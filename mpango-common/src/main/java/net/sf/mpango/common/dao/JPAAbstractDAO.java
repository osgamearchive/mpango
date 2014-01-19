package net.sf.mpango.common.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public abstract class JPAAbstractDAO<I extends Identified<S>, S extends Serializable> implements DAO<I,S> {

    private Class<I> clazz;

    public JPAAbstractDAO(Class<I> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void delete(final I entity) throws NotFoundException {
        getEntityManager().remove(entity);
    }

    @Override
    public I save(final I entity) throws AlreadyExistsException {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public void update(final I entity) throws NotFoundException {
        getEntityManager().persist(entity);
    }

    @Override
    public I load(final S identifier) throws NotFoundException {
        return getEntityManager().find(clazz, identifier);
    }

    @Override
    public List<I> list() {
        final Query query = getEntityManager().createNamedQuery(getListNamedQueryName());
        final List<I> list = query.getResultList();
        return list;
    }

    public abstract EntityManager getEntityManager();

    public abstract String getListNamedQueryName();
}
