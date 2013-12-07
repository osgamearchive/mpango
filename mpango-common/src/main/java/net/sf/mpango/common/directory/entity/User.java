package net.sf.mpango.common.directory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.common.entity.AbstractPersistable;


/**
 * <p>
 * Class representing the information of a user.
 * </p>
 * 
 * @author etux
 * 
 */
@Entity
public class User extends AbstractPersistable<Long> {

    public enum Gender {
        UNDEFINED,
        MALE,
        FEMALE;
    }

	private String email;
	private String username;
	private String password;
	private String resetKey;
	private Date dateOfBirth;
	private Gender gender;
	private String nonceToken;
	private StateEnum state;

    public User() {
        this.gender = Gender.UNDEFINED;
    }

	/**
	 * @return
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	public StateEnum getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(StateEnum state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	@Column(nullable = false)
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
	@Column(nullable = false)
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
