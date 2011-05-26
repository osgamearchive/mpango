package net.sourceforge.mpango.web;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.model.SelectItem;

import net.sourceforge.mpango.directory.UserDAOHibernate;
import net.sourceforge.mpango.entity.User;

/** 
 * @author edvera
 */
public class HelloWorldBacking {

    private UserDAOHibernate userDAO;
    
    private String username;
    private String email;
	private Long identifier;
	private String message;
	private ArrayList<SelectItem>countries;
	private Date dateOfBirth;
	private String password;
	private String gender;
	
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

    public ArrayList<SelectItem> getCountries() 
    {
    	countries = new ArrayList<SelectItem>();
    	countries.add(new SelectItem("Country1","Country 1"));
    	countries.add(new SelectItem("Country2","Country 2",""));
    	countries.add(new SelectItem("Country3","Country 3",""));
    	countries.add(new SelectItem("Country4","Country 4",""));
		return countries;
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

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
    
    
}