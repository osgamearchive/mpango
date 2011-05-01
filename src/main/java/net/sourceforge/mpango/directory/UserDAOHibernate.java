package net.sourceforge.mpango.directory;

import java.util.List;

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

	public Long save(User user) {
		return (Long) getHibernateTemplate().save(user);
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
