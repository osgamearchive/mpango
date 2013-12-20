package net.sf.mpango.common.directory.entity;

import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.sf.mpango.common.entity.AbstractPersistable;
import org.hibernate.validator.constraints.Email;


/**
 * <p>
 * Class representing the information of a user.
 * From this class the following fields need special consideration:
 * <ul>
 *     <li>{@link User#email} must be unique, no two users may have the same email.</li>
 *     <li>{@link User#username} must be unique, no two users may have the same username.</li>
 *     <li>{@link User#password} must be longer than 6 characters and shorter but including 16.</li>
 * </ul>
 * </p>
 * 
 * @author etux
 * 
 */
@Entity
@Table(name = "USERS")
@NamedQueries(
        {
                @NamedQuery(name = User.NAMED_QUERY_LIST_ALL_USERS, query = "from User"),
                @NamedQuery(name = User.NAMED_QUERY_FIND_USER_BY_RESET_KEY, query = "from User where resetKey= ?"),
                @NamedQuery(name = User.NAMED_QUERY_FIND_USER_BY_EMAIL, query = "from User where emaiL= ?")
        }
)
public class User extends AbstractPersistable<Long> {

    /** Named query to find all users without any filtering. */
    public static final String NAMED_QUERY_LIST_ALL_USERS = "list_all_users";
    /** Named query to find a user based on his reset key */
    public static final String NAMED_QUERY_FIND_USER_BY_RESET_KEY = "find_user_by_reset_key";
    /** Named query to find a user based on his email */
    public static final String NAMED_QUERY_FIND_USER_BY_EMAIL = "find_user_by_email";

    static final String VALIDATOR_MSG_INVALID_EMAIL         = "user.validator.invalid_email";
    static final String VALIDATOR_MSG_INVALID_USERNAME      = "user.validator.invalid_username";
    static final String VALIDATOR_MSG_INVALID_PASSWORD      = "user.validator.invalid_password";
    protected static final String VALIDATOR_PATTERN_USERNAME = "[A-Za-z0-9]+";
    protected static final int VALIDATOR_SIZE_MIN_USERNAME = 6;
    protected static final int VALIDATOR_SIZE_MAX_USERNAME = 16;
    protected static final int VALIDATOR_SIZE_MIN_EMAIL = 8;
    protected static final int VALIDATOR_SIZE_MAX_EMAIL = 128;
    protected static final int VALIDATOR_SIZE_MAX_PASSWORD = 16;
    protected static final int VALIDATOR_SIZE_MIN_PASSWORD = 6;
    protected static final String VALIDATOR_PATTERN_PASSWORD = "[A-Za-z0-9]+";

    public enum Gender {
        UNDEFINED,
        MALE,
        FEMALE;
    }

    public enum State {

        /** The user has been created but not yet activated. */
        CREATED(0),
        /** The user has been activated, at this stage the user can go into inactive to deleted states. */
        ACTIVE(1),
        /** The user has been deactivated, at this stage the user can either go back to activated or to deleted stages. */
        INACTIVE(2),
        /** The user has been deleted, no further transitions may take place. */
        DELETED(3);

        	private int value;

        	State(final int value) {
        		this.value = value;
        	}

        	public int getValue() {
        		return this.value;
        	}

        	public State fromValue(final int value) {
                State result = null;
        		for (State currentState : State.values()) {
        			if (currentState.getValue() == this.value) {
        				result = currentState;
        			}
        		}
        		return result;
        	}
    }

	private String email;
	private String username;
	private String password;
	private String resetKey;
	private Date dateOfBirth;
	private Gender gender;
	private String nonceToken;
	private State state;

    public User() {
        this.gender = Gender.UNDEFINED;
    }


    public static class UserBuilder {

        private static final Logger LOGGER = Logger.getLogger(UserBuilder.class.getName());

        private static final Validator VALIDATOR;

        static {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            VALIDATOR = validatorFactory.getValidator();
        }

        private User user;

        private UserBuilder() {

            user = new User();
        }

        /**
         * Creates the UserBuilder with the minimal mandatory information for the User to be created.
         * @param email {@link User#email} account of the user.
         * @param username {@link User#username} of the user.
         * @param password {@link User#password} of the user.
         * @return builder to be used to build a {@link User} instance.
         */
        public static UserBuilder createUser(final String email, final String username, final String password) {
            final UserBuilder builder = new UserBuilder();
            builder.setEmail(email).setUsername(username).setPassword(password);
            return builder;
        }

        public UserBuilder setEmail(final String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder setUsername(final String username) {
            user.setUsername(username);
            return this;
        }

        public UserBuilder setPassword(final String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder setGender(final Gender gender) {
            user.setGender(gender);
            return this;
        }

        public UserBuilder setState(final State state) {
            user.setState(state);
            return this;
        }

        public UserBuilder setDateOfBirth(final Date dateOfBirth) {
            user.setDateOfBirth(dateOfBirth);
            return this;
        }

        public UserBuilder setNonceToken(final String nonceToken) {
            user.setNonceToken(nonceToken);
            return this;
        }
        public UserBuilder setResetKey(final String resetKey) {
            user.setResetKey(resetKey);
            return this;
        }

        /**
         * Method that obtains the created {@link User} after having processed validation.
         * @return instance of the user created.
         * @throws ConstraintViolationException in case the user fields have invalid values.
         */
        public User build() {
            final Set<ConstraintViolation<User>> constraintViolationSet = VALIDATOR.validate(user);
            if ((constraintViolationSet != null) && (constraintViolationSet.size() > 0)) {
                LOGGER.log(Level.WARNING, constraintViolationSet.toString());
                throw new ConstraintViolationException(constraintViolationSet);
            }
            return user;
        }
    }

	/**
	 * @return
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	@Column(nullable = false)
    @NotNull(message = VALIDATOR_MSG_INVALID_PASSWORD)
    @Size(min = VALIDATOR_SIZE_MIN_PASSWORD, max = VALIDATOR_SIZE_MAX_PASSWORD, message = VALIDATOR_MSG_INVALID_PASSWORD)
    @Pattern(regexp = VALIDATOR_PATTERN_PASSWORD, message = VALIDATOR_MSG_INVALID_PASSWORD)
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 */
	@Column(nullable = true)
	public String getResetKey() {
		return resetKey;
	}

	/**
	 * @param resetKey for changing password
	 */
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	/**
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return
	 */
    @Enumerated(EnumType.ORDINAL)
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	@Column(nullable = false, unique = true)
    @NotNull (message = VALIDATOR_MSG_INVALID_USERNAME)
    @Size(min = VALIDATOR_SIZE_MIN_USERNAME, max = VALIDATOR_SIZE_MAX_USERNAME, message = VALIDATOR_MSG_INVALID_USERNAME)
    @Pattern(regexp = VALIDATOR_PATTERN_USERNAME, message= VALIDATOR_MSG_INVALID_USERNAME)
	public String getUsername() {
		return username;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 */
	@Column(unique = true)
    @NotNull (message = VALIDATOR_MSG_INVALID_EMAIL)
    @Size(min = VALIDATOR_SIZE_MIN_EMAIL, max = VALIDATOR_SIZE_MAX_EMAIL, message = VALIDATOR_MSG_INVALID_EMAIL)
    @Email(message = VALIDATOR_MSG_INVALID_EMAIL)
	public String getEmail() {
		return email;
	}

	/**
	 * @param nonceToken
	 */
	public void setNonceToken(String nonceToken) {
		this.nonceToken = nonceToken;
	}

	/**
	 * @return
	 */
	@Column(unique = true)
	public String getNonceToken() {
		return nonceToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [identifier=" + getId() + ", email=" + email
				+ ", username=" + username + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
