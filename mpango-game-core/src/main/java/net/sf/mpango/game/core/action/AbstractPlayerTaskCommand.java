package net.sf.mpango.game.core.action;

import java.util.List;
import java.util.Timer;

import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.events.Listener;

/**
 * Abstract player task command.
 * @author etux
 *
 */
public abstract class AbstractPlayerTaskCommand extends AbstractTaskCommand implements PlayerCommand {

	private Player player;
	
	public AbstractPlayerTaskCommand(Player player, long millisPerSlice, Timer timer,
			List<Listener> listeners) {
		super(millisPerSlice, timer, listeners);
		this.player = player;
	}

	public AbstractPlayerTaskCommand(Player player, long millisPerSlice, Timer timer,
			Listener... listeners) {
		super(millisPerSlice, timer, listeners);
		this.player = player;
	}

	public AbstractPlayerTaskCommand(Player player, Timer timer, Listener... listeners) {
		this(player, 0, timer, listeners);
	}

	public Player getPlayer() {
		return player;
	}

}
