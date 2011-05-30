package net.sourceforge.mpango.directory.dao;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;

/**
 * Data Access Object Interface for {@link User}.
 * @author etux
 *
 */
public interface UserDAO {

	public User load(String email);
	public User save(User user);
	public void update(User user);
	public void delete(User user);
	public List<User> list();
}
