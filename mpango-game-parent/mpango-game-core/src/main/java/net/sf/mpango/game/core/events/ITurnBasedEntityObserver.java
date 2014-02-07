package net.sf.mpango.game.core.events;

/**
 * Interface which entities that react on turns must implement.
 * @author etux
 *
 */
public interface ITurnBasedEntityObserver extends GameObserver {

	/**
	 * Method to tell the entity to react on a turn pass event.
	 * @param turnEvent
	 */
	void receive(TurnEvent turnEvent);
}
