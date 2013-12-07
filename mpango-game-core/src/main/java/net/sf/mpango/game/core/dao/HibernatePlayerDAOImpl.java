package net.sf.mpango.game.core.dao;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import net.sf.mpango.common.dao.HibernateAbstractDAOImpl;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.entity.Player;

/**
 * Hibernate Data Access Object implementation for the {@link Player} Entity.
 * @author etux
 *
 */
@NamedQueries(
        {
                @NamedQuery(name= HibernatePlayerDAOImpl.FIND_PLAYER_WITH_STATE, query="from Player p where p.state!=? and p.user.identifier=?"),
                @NamedQuery(name= HibernatePlayerDAOImpl.FIND_PLAYER, query ="select player from Player player where player.user.identifier=?"),
                @NamedQuery(name= HibernatePlayerDAOImpl.FIND_ALL_PLAYERS, query ="select player from Player player")
        }
)

public class HibernatePlayerDAOImpl extends HibernateAbstractDAOImpl<Player> implements PlayerDAO {

    protected static final String FIND_ALL_PLAYERS = "find_all_players";
    protected static final String FIND_PLAYER = "find_player";
    protected static final String FIND_PLAYER_WITH_STATE = "find_player_with_state";

    public HibernatePlayerDAOImpl(Class<Player> clazz) {
        super(clazz);
    }

	/**
	 * Method that finds a player based on the user passed.
	 * @param user to which the player is linked.
	 * @return {@link Player} in case the user has a player associated to it. Otherwise, it returns null in case no player exists for the user.
	 */
	@Override
	public Player findPlayer(User user) {
		Player result = null;
		List<Player> players = getHibernateTemplate().findByNamedQuery(FIND_PLAYER_WITH_STATE, StateEnum.ACTIVE, user.getId());
		if ((players != null) && (players.size() > 0)) {
			result = players.get(0);
		}
		return result;
	}

    public List<Player> list() {
        return getHibernateTemplate().findByNamedQuery(FIND_ALL_PLAYERS);
    }

}
