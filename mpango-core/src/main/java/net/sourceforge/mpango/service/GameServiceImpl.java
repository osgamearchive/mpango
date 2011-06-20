package net.sourceforge.mpango.service;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.entity.*;
import net.sourceforge.mpango.exception.CommandException;

/**
 * <p>{@link IGameService} implementation.</p>
 * @author edvera
 */
public class GameServiceImpl implements IGameService {

    private GameContext context;

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
}
