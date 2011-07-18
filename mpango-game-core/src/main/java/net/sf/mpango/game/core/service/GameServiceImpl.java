package net.sf.mpango.game.core.service;

import java.util.List;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.BoardConfiguration;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameConfiguration;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.PlayerAlreadyExistsException;

/**
 * <p>{@link IGameService} implementation.</p>
 * @author edvera
 */
public class GameServiceImpl implements IGameService {

    private GameContext context;
    private PlayerDAO playerDAO;

	public GameServiceImpl() {
        BoardConfiguration boardConfiguration = new BoardConfiguration();
        GameConfiguration configuration = new GameConfiguration(boardConfiguration);
        context = new GameContext(configuration);
    }

    public void join(String name, User user) {
        Player player = new Player(name, user, context);
        context.join(player);
    }

    public void settleUnit(Unit unit, Cell cell) throws CommandException {
        unit.settle(cell);
    }

    @Override
	public Player createPlayer(User user, Player player) throws PlayerAlreadyExistsException {
		List<Player> players = playerDAO.findPlayersByUser(user);
		if (null != players && players.size() > 0) {
			throw new PlayerAlreadyExistsException();
		}
		player.setUser(user);
		return playerDAO.save(player);
	}

	@Override
	public void delete(Player player) {
		player.setState(StateEnum.DELETED);
		playerDAO.save(player);
	}

    public PlayerDAO getPlayerDAO() {
		return playerDAO;
	}

	public void setPlayerDAO(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}
}
