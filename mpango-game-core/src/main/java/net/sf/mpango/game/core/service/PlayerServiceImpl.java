package net.sf.mpango.game.core.service;

import java.util.List;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;

public class PlayerServiceImpl implements IPlayerService {

	private PlayerDAO playerDAO;

	@Override
	public Player register(User user, GameContext context) {
		Player player = null;
		List<Player> players = playerDAO.findPlayersByUser(user);
		if ((players == null) || (players.size() == 0)) {
			player = new Player(user, context);
		}
		return player;
	}
	
	public PlayerDAO getPlayerDAO() {
		return playerDAO;
	}

	public void setPlayerDAO(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

}
