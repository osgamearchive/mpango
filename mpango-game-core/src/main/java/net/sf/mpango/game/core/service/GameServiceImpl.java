package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * <p>{@link IGameService} implementation.</p>
 * @author edvera
 */
public class GameServiceImpl implements IGameService {

    private GameContext context;
    private IPlayerService playerService;

    public Player join(User user) {
    	return playerService.register(user, context);
    }

    public void settleUnit(Unit unit, Cell cell) throws CommandException {
        unit.settle(cell);
    }


	public IPlayerService getPlayerService() {
		return playerService;
	}

	public void setPlayerService(IPlayerService playerService) {
		this.playerService = playerService;
	}

	public void setGameContext(GameContext context) {
		this.context = context;
	}
}