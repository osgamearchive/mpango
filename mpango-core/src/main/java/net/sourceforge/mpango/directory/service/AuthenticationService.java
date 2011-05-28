package net.sourceforge.mpango.directory.service;

import java.util.List;

import net.sourceforge.mpango.directory.dao.UserDAO;
import net.sourceforge.mpango.directory.entity.User;

public class AuthenticationService implements IAuthenticationService {

	UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#delete(net.sourceforge
	 * .mpango.entity.User)
	 */
	@Override
	public void delete(User user) {
		userDAO.delete(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.mpango.service.IAuthenticationService#list()
	 */
	@Override
	public List<User> list() {
		return userDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#load(java.lang.
	 * String)
	 */
	@Override
	public User load(String email) {
		return userDAO.load(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#save(net.sourceforge
	 * .mpango.entity.User)
	 */
	@Override
	public User save(User user) {
		return userDAO.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#update(net.sourceforge
	 * .mpango.entity.User)
	 */
	@Override
	public void update(User user) {
		userDAO.update(user);

	}

}
