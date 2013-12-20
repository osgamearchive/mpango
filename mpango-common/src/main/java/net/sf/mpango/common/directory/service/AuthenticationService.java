package net.sf.mpango.common.directory.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.directory.dao.UserAlreadyExistsException;
import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.dao.UserNotFoundException;
import net.sf.mpango.common.directory.entity.User;

/**
 * Basic Authentication Service implementation.
 * 
 * @author etux
 * 
 */
public class AuthenticationService implements IAuthenticationService {

    private static final MessageFormat MSG_FORMAT = new MessageFormat("messages", Locale.getDefault());
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    protected static final String SHA_1_PRNG = "SHA1PRNG";

    private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#load(java.lang.String)
	 */
	@Override
	public User login(final String email, final String password) throws AuthenticationException {
        assert email != null;
        assert password != null;
        final User user;

        try {
            user = userDAO.load(email);
            if ((user.getPassword() == null) || !(user.getPassword().equals(password))) {
                throw new AuthenticationException(MessageFormat.format("user.wrong_credentials", email));
            }
            LOGGER.log(Level.INFO, MessageFormat.format("User {0} successfully logged in", user.getEmail()));
            return user;
        } catch (UserNotFoundException e) {
            throw new AuthenticationException( MSG_FORMAT.format("user.wrong_credentials", email), e);
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#save(net.sourceforge
	 * .mpango.entity.User)
	 */
	@Override
	public User register(final User user) throws AuthenticationException {
        try {
            final String nonce = generateNonce();
            user.setNonceToken(nonce);
            user.setState(User.State.CREATED);
            return userDAO.save(user);
        } catch (UserAlreadyExistsException e) {
            throw new AuthenticationException(e);
        }
	}

	@Override
	public List<User> list() {
		return userDAO.list();
	}

	@Override
	public String generateResetKey(final String email) throws AuthenticationException {
        try {
            final User user = getUserDAO().load(email);
            user.setResetKey(UUID.randomUUID().toString());
            getUserDAO().update(user);
            return user.getResetKey();
        } catch (final UserNotFoundException e) {
            throw new AuthenticationException(e);
        }
	}

	@Override
	public User getUserByResetKey(final String resetKey) throws AuthenticationException {
        try {
            return userDAO.lookUpByResetKey(resetKey);
        } catch (final UserNotFoundException e) {
            throw new AuthenticationException(e);
        }

	}

	@Override
	public void changeUserPassword(final User user, final String newPassword) throws AuthenticationException {
        try {
            user.setPassword(newPassword);
            getUserDAO().update(user);
        } catch (UserNotFoundException e) {
            throw new AuthenticationException(e);
        }
	}

    private String generateNonce() {
   		// Create two secure number generators with the same seed
   		int seedByteCount = 24;
   		byte[] seed = new byte[seedByteCount];
   		try {
   			// Create a secure random number generator
   			SecureRandom sr = SecureRandom.getInstance(SHA_1_PRNG);
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

}