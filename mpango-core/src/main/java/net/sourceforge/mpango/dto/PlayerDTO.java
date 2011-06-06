package net.sourceforge.mpango.dto;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.entity.Position;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.enums.StateEnum;

/**
 * data transfer object representing player
 * 
 * @author aplause
 * 
 */
public class PlayerDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6825923397531823816L;

	private String name;
	private Position position;
	private List<Unit> units;
	private StateEnum state;
	private User user;

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return
	 */
	public List<Unit> getUnits() {
		return units;
	}

	/**
	 * @param units
	 */
	public void setUnits(List<Unit> units) {
		this.units = units;
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
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
