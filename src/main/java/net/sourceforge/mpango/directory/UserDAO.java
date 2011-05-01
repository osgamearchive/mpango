package net.sourceforge.mpango.directory;

import java.util.List;

public interface UserDAO {

	public User load(String email);
	public Long save(User user);
	public void update(User user);
	public void delete(User user);
	public List<User> list();
}
