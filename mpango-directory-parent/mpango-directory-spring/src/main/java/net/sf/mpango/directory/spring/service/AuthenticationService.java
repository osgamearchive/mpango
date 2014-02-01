package net.sf.mpango.directory.spring.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.ConfigurationException;
import net.sf.mpango.common.dao.AlreadyExistsException;
import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import net.sf.mpango.directory.dao.UserDAO;
import net.sf.mpango.directory.dao.UserNotFoundException;
import net.sf.mpango.directory.entity.User;
import net.sf.mpango.directory.service.AuthenticationException;
import net.sf.mpango.directory.service.IAuthenticationService;

/**
 * Basic Authentication Service implementation.
 * 
 * @author etux
 * 
 */
public class AuthenticationService implements IAuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    private static final String SHA_1_PRNG = "SHA1PRNG";

    private UserDAO userDAO;
    private SecureRandom secureRandom;

    public AuthenticationService() {
        try {
   			// Create a secure random number generator
            secureRandom = SecureRandom.getInstance(SHA_1_PRNG);
            LOGGER.log(
                    Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_SERVICE_INITIALIZED, SHA_1_PRNG));
        } catch (final NoSuchAlgorithmException exception) {
            throw new ConfigurationException(
                                LocalizedMessageBuilder.getSystemMessage(
                                        this,
                                        MessageConstants.USER_GENERATE_NONCE_FAILURE_RNG_NOT_FOUND
                                ), exception);
        }
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.service.IAuthenticationService#loadByEmail(java.lang.String)
	 */
	@Override
	public User login(final String email, final String password) throws AuthenticationException {
        assert email != null;
        assert password != null;

        final User user;

        try {
            user = userDAO.loadByEmail(email);
            if ((user.getPassword() == null) || !(user.getPassword().equals(password))) {
                throw new AuthenticationException(
                        LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_WRONG_CREDENTIALS, email));
            }
            LOGGER.log(Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_LOGIN_SUCCESSFUL, user.getEmail()));
            return user;
        } catch (final NotFoundException e) {
            throw new AuthenticationException(
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_WRONG_CREDENTIALS,
                            email
                    ), e);
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
        } catch (final AlreadyExistsException e) {
            throw new AuthenticationException(
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_REGISTRATION_FAILURE_USER_ALREADY_EXISTS
                    ), e);
        }
	}

	@Override
	public List<User> list() {
		return userDAO.list();
	}

	@Override
	public String generateResetKey(final String email) throws AuthenticationException {
        try {
            final User user = getUserDAO().loadByEmail(email);
            user.setResetKey(UUID.randomUUID().toString());
            getUserDAO().update(user);
            return user.getResetKey();
        } catch (final NotFoundException e) {
            throw new AuthenticationException(
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_GENERATE_RESET_KEY_FAILURE_USER_NOT_FOUND,
                            email
                    ), e);
        }
	}

	@Override
	public User getUserByResetKey(final String resetKey) throws AuthenticationException {
        try {
            return userDAO.lookUpByResetKey(resetKey);
        } catch (final UserNotFoundException e) {
            throw new AuthenticationException(
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_GET_USER_BY_RESET_KEY_FAILURE_USER_NOT_FOUND,
                            resetKey
                    ), e);
        }

	}

	@Override
	public void changeUserPassword(final User user, final String newPassword) throws AuthenticationException {
        assert user != null;
        assert newPassword != newPassword;

        try {
            user.setPassword(newPassword);
            getUserDAO().update(user);
        } catch (final NotFoundException e) {
            throw new AuthenticationException(
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_CHANGE_PASSWORD_FAILURE_USER_NOT_FOUND,
                            user.getEmail()
                    ), e);
        }
	}

    private String generateNonce() {
   		final int seedByteCount = 24;
        // Get 1024 random bits
        final byte[] bytes = new byte[1024 / 8];
        secureRandom.nextBytes(bytes);
        return new String (secureRandom.generateSeed(seedByteCount));
   	}

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}