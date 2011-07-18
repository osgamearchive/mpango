package net.sf.mpango.game.core.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.entity.Player;

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
	public List<Player> findPlayersByUser(User user) {
		return (List<Player>) getHibernateTemplate().find(
				"from Player p where p.state!=? and p.user.identifier=?",
				StateEnum.DELETED, user.getIdentifier());
	}


	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	

}
