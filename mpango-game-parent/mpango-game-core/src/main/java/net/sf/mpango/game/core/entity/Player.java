package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import net.sf.mpango.directory.entity.User;
import net.sf.mpango.common.entity.AbstractEntity;

/**
 * <p>Entity contains data about {@link Player} connecting a {@link User} to a {@link GameContext}.
 * This class has the following information:
 * <ol>
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
@NamedQuery(name="find_player_with_state", query="from Player p where p.state!=? and p.user.id=?")
public class Player extends AbstractEntity<Long> {

	private Position position;
	private List<Unit> units;
	private User.State state;
	private User user;
    private GameContext gameContext;
    
    public Player() {}

    public Player(User user, GameContext gameContext) {
        this.units = new ArrayList<Unit>();
        this.state = User.State.CREATED;
        this.user = user;
        this.gameContext = gameContext;
    }

	/**
	 * @return
	 */
	public User.State getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(User.State state) {
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
	@ManyToOne
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Player other = (Player) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
    
    
}
