package net.sf.mpango.game.core.dao;

import java.util.List;

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
@NamedQuery(name="find_player_with_state", query="from Player p where p.state!=? and p.user.identifier=?")
public class HibernatePlayerDAOImpl extends HibernateAbstractDAOImpl implements PlayerDAO {

	/**
	 * Method that saves or updates the {@link Player} entity.
	 * @param player to save or update depending if it already exists.
	 */
	public Player save(Player player) {
		if (null == player.getIdentifier()) {
			Long playerId = (Long) getHibernateTemplate().save(player);
			player.setIdentifier(playerId);
		} else {
			getHibernateTemplate().update(player);
		}
		return player;
	}
	
	/**
	 * Method that updates the {@link Player} entity.
	 * @param player to update in the database.
	 */
	public void update(Player player) {
		getHibernateTemplate().update(player);
	}

	/**
	 * Method that finds a player based on the user passed.
	 * @param user to which the player is linked.
	 * @return {@link Player} in case the user has a player associated to it. Otherwise, it returns null in case no player exists for the user.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Player findPlayer(User user) {
		Player result = null;
		List<Player> players = getHibernateTemplate().findByNamedQuery("find_player_with_state", StateEnum.DELETED, user.getIdentifier());
		if ((players != null) && (players.size() > 0)) {
			result = players.get(0);
		}
		return result;
	}
	

}
