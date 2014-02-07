package net.sf.mpango.game.core.events;

/**
 * Commodity class to hold the source object that generated the event.
 * An event by definition is immutable, so all its fields are final.
 * @author edvera
 *
 */
public abstract class AbstractEvent<T> implements Event<T> {

	private final T source;
    private final long occurrenceTime;
	/**
	 * All events need to have a source at instantiation time.
	 * The constructor stores the current occurrenceTime.
	 * @param source Object that produced the event.
	 */
	public AbstractEvent (final T source) {
		this.source = source;
		this.occurrenceTime = System.currentTimeMillis();
	}

	/**
	 * Getter method with the source of the event.
	 * @return source producing the event.
	 */
	public T getSource() {
		return source;
	}

	/**
	 * Method that returns the time on which the event occurred.
	 * @return time on which the event took place {@link System#currentTimeMillis()}.
	 */
	@Override
	public long getOccurrenceTime() {
		return this.occurrenceTime;
	}

    private static final long serialVersionUID = -1695677684170597109L;

}
