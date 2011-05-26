package net.sourceforge.mpango.dto;

/**
 * @author aplause
 * 
 */
public class UserDTO extends BaseDTO {

	private static final long serialVersionUID = 1173974818697158235L;

	private Long userId;
	private String email;
	private String username;

	/**
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

}
