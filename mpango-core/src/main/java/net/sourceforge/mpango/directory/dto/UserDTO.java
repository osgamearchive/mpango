package net.sourceforge.mpango.directory.dto;

import java.util.Date;
import java.util.List;

import net.sourceforge.mpango.dto.BaseDTO;
import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.entity.Player;
import net.sourceforge.mpango.enums.StateEnum;

/**
 * @author aplause
 * 
 */
public class UserDTO extends BaseDTO {

	private static final long serialVersionUID = 1173974818697158235L;

	private String email;
	private String username;
	private String firstname;
	private String surname;
	private String password;
	private String resetKey;
	private Date dateOfBirth;
	private String gender;
	private StateEnum state;
	private List<PlayerDTO> playerList;


	/**
	 * @return
	 */
	public String getEmail() {
		return email;
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
	public String getUsername() {
		return username;
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
	public String getResetKey() {
		return resetKey;
	}

	/**
	 * @param resetKey
	 */
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}	

	/**
	 * @return
	 */
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
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return
	 */
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
	public List<PlayerDTO> getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList
	 */
	public void setPlayerList(List<PlayerDTO> playerList) {
		this.playerList = playerList;
	}
	
	
	

}
