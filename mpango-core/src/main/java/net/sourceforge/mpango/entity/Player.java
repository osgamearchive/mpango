package net.sourceforge.mpango.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.enums.StateEnum;

/**
 * 
 * @author aplause Entity contains data about {@link Player} connected with
 *         {@link User}
 */
@Entity(name = "Player")
public class Player {

	private Long identifier;
	private String name;
	private Position position;
	private List<Unit> units;
	private StateEnum state;
	private User user;

	/**
	 * @return
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

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
	@Transient
	// TODO: do mappings 
	public Position getPosition() {
		return this.position;
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
	//TODO: do mappings
	@Transient
	public List<Unit> getUnits() {
		return this.units;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
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
