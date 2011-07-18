package net.sf.mpango.game.core.dao;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.orm.hibernate3.HibernateTemplate;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.entity.Player;

@NamedQuery(name="find_player_with_state", query="from Player p where p.state!=? and p.user.identifier=?")
public class HibernatePlayerDAOImpl implements PlayerDAO {

	private HibernateTemplate hibernateTemplate;

	public Player save(Player player) {
		if (null == player.getIdentifier()) {
			Long playerId = (Long) getHibernateTemplate().save(player);
			player.setIdentifier(playerId);
		} else {
			getHibernateTemplate().update(player);
		}
		return player;
	}
	
	public void update(Player player) {
		getHibernateTemplate().update(player);
	}

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


	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	

}
