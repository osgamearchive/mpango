package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;

/**
 * Player service implementation. 
 * @author etux
 *
 */
public class PlayerServiceImpl implements IPlayerService {

	private PlayerDAO playerDAO;

	@Override
	public Player join(User user, GameContext context) {
		Player player = playerDAO.findPlayer(user);
		if (player == null) {
			player = new Player(user, context);
			player.setPosition(context.generateRandomPosition());
			player.setUnits(context.generateStartingUnits());
		}
		player.setState(StateEnum.ACTIVE);
		playerDAO.save(player);
		context.join(player);
		return player;
	}
	
	public PlayerDAO getPlayerDAO() {
		return playerDAO;
	}

	public void setPlayerDAO(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

}
