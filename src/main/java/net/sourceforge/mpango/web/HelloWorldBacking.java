package net.sourceforge.mpango.web;

import net.sourceforge.mpango.directory.User;
import net.sourceforge.mpango.directory.UserDAOHibernate;

/** 
 * @author edvera
 */
public class HelloWorldBacking {

    private UserDAOHibernate userDAO;
    
    private String username;
    private String email;
	private Long identifier;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelloWorldBacking() {
		System.out.println("Creating instance of "+HelloWorldBacking.class);
	}

    /**
     * Method that is backed to a submit button of a form.
     */
    public String send() {
    	User user = userDAO.load(email);
    	if (user != null) {
    		message = "User already exists with that email address.";
    		return "failure";
    	} else {
        	user = new User();
        	user.setEmail(email);
        	user.setUsername(username);
        	user.setIdentifier(userDAO.save(user));
        	identifier = user.getIdentifier();
        	message = "User registered succesfully";
            return "success";
    	}
    	
    }

    //-------------------getter & setter
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setUserDAO(UserDAOHibernate userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAOHibernate getUserDAO() {
		return userDAO;
	}
    
    
}