package net.sf.mpango.game.core.events;

public interface TurnListener extends Listener {

	void receiveEvent (TurnEvent event);
}
