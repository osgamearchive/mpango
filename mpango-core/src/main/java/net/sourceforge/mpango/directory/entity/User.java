package net.sourceforge.mpango.directory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>Class representing the information of a user.</p>
 * @author etux
 *
 */
@Entity
public class User {

	private Long identifier;
	private String email;
	private String username;
	private String password;
	private Date dateOfBirth;
	private String gender;
	private String nonceToken;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(unique=true)
	public String getEmail() {
		return email;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	@Id
	@GeneratedValue
	public Long getIdentifier() {
		return identifier;
	}
	public void setNonceToken(String nonceToken) {
		this.nonceToken = nonceToken;
	}
	@Column(unique=true)
	public String getNonceToken() {
		return nonceToken;
	}
	@Override
	public String toString() {
		return "User [identifier=" + identifier + ", email=" + email
				+ ", username=" + username + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
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
