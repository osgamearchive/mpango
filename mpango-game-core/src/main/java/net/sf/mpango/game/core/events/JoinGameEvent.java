package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.entity.Player;

/**
 * Event that takes place whenever a player joins the game.
 * @author etux
 *
 */
public class JoinGameEvent extends PlayerEvent {

	public JoinGameEvent(Player source) {
		super(source);
	}

	private static final long serialVersionUID = 8546682378139848053L;

}
