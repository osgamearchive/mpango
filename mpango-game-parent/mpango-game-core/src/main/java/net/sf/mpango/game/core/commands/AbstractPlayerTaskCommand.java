package net.sf.mpango.game.core.commands;

import java.util.List;
import java.util.concurrent.ExecutorService;

import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Observer;

/**
 * Abstract player task command.
 * @author etux
 *
 */
public abstract class AbstractPlayerTaskCommand extends AbstractTaskCommand<CommandEvent> implements PlayerCommand<CommandEvent> {

	private final Player player;
	
	public AbstractPlayerTaskCommand(final Player player, final long millisPerSlice, final ExecutorService executorService,
			final List<Observer> observers) {
		super(millisPerSlice, executorService, observers);
		this.player = player;
	}

	public AbstractPlayerTaskCommand(final Player player, final long millisPerSlice, final ExecutorService executorService,
			final Observer... observers) {
		super(millisPerSlice, executorService, observers);
		this.player = player;
	}

	public AbstractPlayerTaskCommand(final Player player, final ExecutorService executorService, final Observer... observers) {
		this(player, 0, executorService, observers);
	}

	public Player getPlayer() {
		return player;
	}

}
