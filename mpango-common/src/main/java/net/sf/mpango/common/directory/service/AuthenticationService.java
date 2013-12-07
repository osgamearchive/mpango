package net.sf.mpango.common.directory.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;

/**
 * Basic Authentication Service implementation.
 * 
 * @author etux
 * 
 */
public class AuthenticationService implements IAuthenticationService {

	private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#load(java.lang.String)
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
	public User register(User user) {
		String nonce = generateNonce();
		user.setNonceToken(nonce);
		user.setState(StateEnum.CREATED);
		return userDAO.save(user);
	}

	private String generateNonce() {
		// Create two secure number generators with the same seed
		int seedByteCount = 24;
		byte[] seed = new byte[seedByteCount];
		try {
			// Create a secure random number generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			// Get 1024 random bits
			byte[] bytes = new byte[1024 / 8];
			sr.nextBytes(bytes);
			seed = sr.generateSeed(seedByteCount);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(seed);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public List<User> list() {
		return userDAO.list();
	}

	@Override
	public String generateResetKey(String email) {
		String resetKey = String.valueOf(UUID.randomUUID());
		User user = getUserDAO().load(email);
		user.setResetKey(resetKey);
		getUserDAO().update(user);
		return resetKey;
	}

	@Override
	public User getUserByResetKey(String resetKey) {
		return userDAO.lookUpByResetKey(resetKey);
	}

	@Override
	public void changeUserPassword(User user, String newPassword) {
		user.setPassword(newPassword);
		getUserDAO().update(user);
	}

}