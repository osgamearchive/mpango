package net.sourceforge.mpango.directory.dao;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserDAOHibernate implements UserDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public User load(String email) {
		User user = null;
		List results = getHibernateTemplate().find("from User where email= ?",email);
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
		hibernateTemplate.update(user);
	}

	public void delete(User user) {
		hibernateTemplate.delete(user);

	}

	public HibernateTemplate getHibernateTemplate() {
		return new HibernateTemplate(sessionFactory);
	}

	public List<User> list() {
		return getHibernateTemplate().find("from User");
	}

}
