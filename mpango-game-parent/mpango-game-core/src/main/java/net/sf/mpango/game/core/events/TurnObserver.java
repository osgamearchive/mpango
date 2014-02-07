package net.sf.mpango.game.core.events;

public interface TurnObserver extends Observer {

	void receiveEvent (TurnEvent event);
}
