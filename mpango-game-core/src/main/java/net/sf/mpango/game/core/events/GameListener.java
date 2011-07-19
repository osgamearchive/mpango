package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

public interface GameListener {

	void receiveEvent(Event event) throws EventNotSupportedException;
}
