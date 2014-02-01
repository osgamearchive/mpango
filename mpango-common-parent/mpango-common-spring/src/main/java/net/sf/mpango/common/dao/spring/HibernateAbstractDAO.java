package net.sf.mpango.common.dao.spring;

import java.io.Serializable;

import net.sf.mpango.common.dao.DAO;
import net.sf.mpango.common.dao.Identified;
import net.sf.mpango.common.dao.NotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Class that all Hibernate based DAOs should inherit from.
 * It provides convenient methods in order to get the [@link HibernateTemplate} from Spring which is created by the {@link SessionFactory} that is injected.
 * @author etux
 *
 */
public abstract class HibernateAbstractDAO<T extends Identified<K>, K extends Serializable> implements DAO<T,K> {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
    private Class<T> clazz;

    public HibernateAbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

	/**
	 * Method that returns the existing {@link HibernateTemplate}. 
	 * In case there isn't any, it creates an instance based on the {@link SessionFactory} available. 
	 * @return
	 */
	public HibernateTemplate getHibernateTemplate() {
		if (hibernateTemplate == null) {
			hibernateTemplate = new HibernateTemplate(sessionFactory);
		}
		return hibernateTemplate;
	}

	/**
	 * Method that sets the hibernateTemplate to use. Specially interesting for Unit Testing.
	 * @param template
	 */
	public void setHibernateTemplate(final HibernateTemplate template) {
		this.hibernateTemplate = template;
	}
	
	/**
	 * Method that returns the {@link SessionFactory} available.
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Method that sets the {@link SessionFactory}. Used to inject the Spring available one.
	 * @param sessionFactory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

    @Override
    public T load(final K identifier) throws NotFoundException {
        return hibernateTemplate.load(clazz, identifier);
    }

    @Override
    public T save(final T entity) {
        hibernateTemplate.save(entity);
        return entity;
    }

    @Override
    public void update(final T entity) {
        hibernateTemplate.update(entity);
    }
}