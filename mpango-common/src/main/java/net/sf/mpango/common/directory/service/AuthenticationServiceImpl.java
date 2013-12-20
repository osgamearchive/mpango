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
public class AuthenticationServiceImpl implements IAuthenticationService {

    private static final MessageFormat MSG_FORMAT = new MessageFormat("messages", Locale.getDefault());
    private static final Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class.getName());
    protected static final String PRNG_ALGORYTHM = "SHA1PRNG";
    protected static final int SEED_BYTE_COUNT = 24;

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
	public User register(final User user) {
		final String nonce = generateNonce();
		user.setNonceToken(nonce);
		user.setState(User.State.CREATED);
        try {
            return userDAO.save(user);
        } catch (final UserAlreadyExistsException e) {
            e.printStackTrace();
            return null;
        }
    }

	private String generateNonce() {
		// Create two secure number generators with the same seed
		int seedByteCount = SEED_BYTE_COUNT;
		byte[] seed = null;
		try {
			// Create a secure random number generator
			final SecureRandom sr = SecureRandom.getInstance(PRNG_ALGORYTHM);
			// Get 1024 random bits
			final byte[] bytes = new byte[1024 / 8];
			sr.nextBytes(bytes);
			seed = sr.generateSeed(seedByteCount);
		} catch (final NoSuchAlgorithmException e) {
			LOGGER.log(Level.WARNING, MessageFormat.format("user.nonce.prng_algorythm_not_found", PRNG_ALGORYTHM));
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
	public User getUserByResetKey(String resetKey) throws AuthenticationException {
        try {
            return userDAO.lookUpByResetKey(resetKey);
        } catch (final UserNotFoundException e) {
            throw new AuthenticationException(e);
        }

	}

	@Override
	public void changeUserPassword(final User user, final String newPassword) {
		user.setPassword(newPassword);
        try {
            getUserDAO().update(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}