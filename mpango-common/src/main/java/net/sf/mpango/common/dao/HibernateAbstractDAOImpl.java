package net.sf.mpango.common.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Class that all Hibernate based DAOs should inherit from.
 * It provides convenient methods in order to get the [@link HibernateTemplate} from Spring which is created by the {@link SessionFactory} that is injected.
 * @author etux
 *
 */
public abstract class HibernateAbstractDAOImpl {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

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
	public void setHibernateTemplate(HibernateTemplate template) {
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
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}