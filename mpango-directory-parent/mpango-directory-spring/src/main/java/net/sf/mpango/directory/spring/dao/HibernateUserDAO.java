package net.sf.mpango.directory.spring.dao;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.dao.spring.HibernateAbstractDAO;
import net.sf.mpango.directory.dao.UserDAO;
import net.sf.mpango.directory.dao.UserNotFoundException;
import net.sf.mpango.directory.entity.User;


/**
 * <p>Hibernate Data Access Object implementation for the User entity.</p>
 * @author etux
 * 
 */
public class HibernateUserDAO extends HibernateAbstractDAO<User, Long> implements UserDAO {

    private static final ResourceBundle MSG_BUNDLE = ResourceBundle.getBundle("net/sf/mpango/common/dao/messages", Locale.getDefault());

    public HibernateUserDAO() {
        super(User.class);
    }

	public User load(Long id) throws UserNotFoundException {
        final User user = (User) getHibernateTemplate().load(User.class, id);
        if (user == null) {
            final String pattern = MSG_BUNDLE.getString("user.exception_message.id_not_found");
            final String message = MessageFormat.format(pattern, id);
            throw new UserNotFoundException(message);
        }
        final String pattern = MSG_BUNDLE.getString("user.success_message.user_found");
        final String message = MessageFormat.format(pattern, user.getEmail());
        LOGGER.log(Level.INFO, message);
        return user;
	}

	public User loadByEmail(final String email) throws UserNotFoundException {
		final List<User> results = (List<User>) getHibernateTemplate().findByNamedQuery(User.NAMED_QUERY_FIND_USER_BY_EMAIL, email);
		if ((results != null) && (results.size() > 0)) {
            final String pattern = MSG_BUNDLE.getString("user.success_message.user_found");
            final String message = MessageFormat.format(pattern, email);
            LOGGER.log(Level.INFO, message);
			return results.get(0);
		} else {
            final String pattern = MSG_BUNDLE.getString("user.exception_message.email_not_found");
            final String message = MessageFormat.format(pattern, email);
            throw new UserNotFoundException(message);
        }
	}

	public User lookUpByResetKey(String resetKey) throws UserNotFoundException {
		@SuppressWarnings("unchecked")
		final List<User> results = (List<User>) getHibernateTemplate().findByNamedQuery(User.NAMED_QUERY_FIND_USER_BY_RESET_KEY, resetKey);
		if ((results != null) && (results.size() > 0)) {
            final User user = results.get(0);
            final String pattern = MSG_BUNDLE.getString("user.success_message.user_found");
            final String message = MessageFormat.format(pattern, user.getEmail());
            LOGGER.log(Level.INFO, message);
			return user;
        } else {
            final String pattern = MSG_BUNDLE.getString("user.exception_message.reset_key_not_found");
            final String message = MessageFormat.format(pattern, resetKey);
            throw new UserNotFoundException(message);
        }
	}

    @SuppressWarnings("unchecked")
   	public List<User> list() {
   		return getHibernateTemplate().findByNamedQuery(User.NAMED_QUERY_LIST_ALL_USERS);
   	}

	public User save(User user) {
		getHibernateTemplate().save(user);
		return user;
	}

	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

    private static final Logger LOGGER = Logger.getLogger(HibernateUserDAO.class.getName());
}