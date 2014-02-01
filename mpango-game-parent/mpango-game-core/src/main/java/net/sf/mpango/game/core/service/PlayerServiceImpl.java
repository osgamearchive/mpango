package net.sf.mpango.game.core.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.dao.AlreadyExistsException;
import net.sf.mpango.directory.entity.User;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;

/**
 * Player service implementation. 
 * @author etux
 *
 */
public class PlayerServiceImpl implements IPlayerService {

    private static final Logger LOGGER = Logger.getLogger(PlayerServiceImpl.class.getName());

    private PlayerDAO playerDAO;

	@Override
	public Player join(final User user, final GameContext context) {
		Player player = playerDAO.findPlayer(user);
		if (player == null) {
			player = new Player(user, context);
			player.setPosition(context.generateRandomPosition());
			player.setUnits(context.generateStartingUnits());
            try {
                playerDAO.save(player);
            } catch (AlreadyExistsException e) {
                LOGGER.log(Level.WARNING, "The player already exists");
            }
        }
        player.setState(User.State.ACTIVE);
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
