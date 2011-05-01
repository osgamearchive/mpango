package net.sourceforge.mpango.directory.web;

import java.util.List;

import net.sourceforge.mpango.directory.User;
import net.sourceforge.mpango.directory.UserDAO;

public class UserBackingBean {
	
	private List<User> users;
	private UserDAO userDAO;
	
	public List<User> getUsers() {
		return userDAO.list();
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	
}
