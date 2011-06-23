package net.sourceforge.mpango.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.enums.StateEnum;

/**
 * <p>Entity contains data about {@link Player} connecting a {@link User} to a {@link GameContext}.
 * This class has the following information:
 * <ol>
 *     <li><B>Name:</B> Name of the player as introduced when joining the game.</li>
 *     <li><b>Position:</b> Las position where the player was. So that she can be taken back to it.</li>
 *     <li><b>Units:</b> Units that belong to the player.</li>
 *     <li><b>State:</b> The state the player is in. Note that states in the case of players are different than
 *     in the case of Users:
 *          <ul>
 *              <li><b>Created</b>: a player has just joined the game.</li>
 *              <li><b>Active</b>: a player is online playing the game.</li>
 *              <li><b>Inactive</b>: a player is offline.</li>
 *              <li><b>Deleted</b>: the player has been removed from the game.</li>
 *          </ul>
 *     </li>
 *     <li><b>Game context:</b> The game context is the link to the actual game the player is involved in.</li>
 * </ol>
 * </p>
 * @author aplause
 * @author edvera
 */
@Entity(name = "Player")
public class Player {

	private Long identifier;
	private String name;
	private Position position;
	private List<Unit> units;
	private StateEnum state;
	private User user;
    private GameContext gameContext;
    
    public Player() { }

    public Player(String name, User user, GameContext gameContext) {
        this.name = name;
        this.units = new ArrayList<Unit>();
        this.state = StateEnum.CREATED;
        this.user = user;
        this.gameContext = gameContext;
    }
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

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    @ManyToOne
    public GameContext getGameContext() {
        return gameContext;
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }
}
