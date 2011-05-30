package net.sourceforge.mpango.directory.dao;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Data Access Object for the User entity.
 * @author etux
 *
 */
public class UserDAOHibernate implements UserDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public User load(String email) {
		User user = null;
		@SuppressWarnings("unchecked")
		List<User> results = (List<User>) getHibernateTemplate().find("from User where email= ?",email);
		if ((results != null) && (results.size() > 0)) {
			user = (User) results.get(0);
		}
		return user;
	}

	public User save(User user) {
		Long userId = (Long) getHibernateTemplate().save(user);
		user.setIdentifier(userId);
		return user;
	}

	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);

	}

	@SuppressWarnings("unchecked")
	public List<User> list() {
		return getHibernateTemplate().find("from User");
	}

	public HibernateTemplate getHibernateTemplate() {
		if (hibernateTemplate == null) {
			hibernateTemplate = new HibernateTemplate(sessionFactory);
		}
		return hibernateTemplate;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
