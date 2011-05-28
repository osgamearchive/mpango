package net.sourceforge.mpango.directory;

import java.util.List;

import net.sourceforge.mpango.entity.User;

public interface UserDAO {

	public User load(String email);
	public User save(User user);
	public void update(User user);
	public void delete(User user);
	public List<User> list();
}
