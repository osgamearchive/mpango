package net.sourceforge.mpango.directory.dao;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;

public interface UserDAO {

	public User load(String email);
	public User save(User user);
	public void update(User user);
	public void delete(User user);
	public List<User> list();
}
