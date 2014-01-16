package net.sf.mpango.game.core.dao;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import net.sf.mpango.common.dao.HibernateAbstractDAO;
import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Player;

/**
 * Hibernate Data Access Object implementation for the {@link Player} Entity.
 * @author etux
 *
 */
@NamedQueries(
        {
                @NamedQuery(name= HibernatePlayerDAO.FIND_PLAYER_WITH_STATE, query="from Player p where p.state!=? and p.user.identifier=?"),
                @NamedQuery(name= HibernatePlayerDAO.FIND_PLAYER, query ="select player from Player player where player.user.identifier=?"),
                @NamedQuery(name= HibernatePlayerDAO.FIND_ALL_PLAYERS, query ="select player from Player player")
        }
)

public class HibernatePlayerDAO extends HibernateAbstractDAO<Player, Long> implements PlayerDAO {

    protected static final String FIND_ALL_PLAYERS = "find_all_players";
    protected static final String FIND_PLAYER = "find_player";
    protected static final String FIND_PLAYER_WITH_STATE = "find_player_with_state";

    public HibernatePlayerDAO() {
        super(Player.class);
    }

	/**
	 * Method that finds a player based on the user passed.
	 * @param user to which the player is linked.
	 * @return {@link Player} in case the user has a player associated to it. Otherwise, it returns null in case no player exists for the user.
	 */
	@Override
	public Player findPlayer(final User user) {
		Player result = null;
		List<Player> players = getHibernateTemplate().findByNamedQuery(FIND_PLAYER_WITH_STATE, User.State.ACTIVE, user.getId());
		if ((players != null) && (players.size() > 0)) {
			result = players.get(0);
		}
		return result;
	}

    @Override
    public void delete(final Player entity) throws NotFoundException {
        getHibernateTemplate().delete(entity);
    }

    public List<Player> list() {
        return getHibernateTemplate().findByNamedQuery(FIND_ALL_PLAYERS);
    }

}
