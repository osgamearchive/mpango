package net.sf.mpango.game.core.events;

/**
 * Commodity class to hold the source object that generated the event.
 * @author edvera
 *
 */
public abstract class AbstractEvent implements Event {

	private static final long serialVersionUID = -1695677684170597109L;
	private Object source;
	private long time;
	
	/**
	 * All events need to have a source at instantiation time.
	 * The constructor stores the current time.
	 * @param source Object that produced the event.
	 */
	public AbstractEvent (Object source) {
		this.source = source;
		this.time = System.currentTimeMillis();
	}
	
	/**
	 * Getter method with the source of the event.
	 * @return source producing the event.
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * Method that returns the time on which the event occurred.
	 * @return time on which the event took place {@link System#currentTimeMillis()}.
	 */
	@Override
	public long getTime() {
		return this.time;
	}

}
