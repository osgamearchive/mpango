package net.sf.mpango.game.core.dao;

import java.io.Serializable;
import java.util.List;

import net.sf.mpango.game.core.entity.GameContext;

/**
 * Data Access Object interface of the game context.
 * @author etux
 *
 */
public interface IGameContextDAO {

	GameContext save(GameContext context);
	void update(GameContext context);
	GameContext load(Serializable identifier);
	List<GameContext> list();
}
