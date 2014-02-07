package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.turn.entity.Turn;

public class TurnEvent extends AbstractEvent<Turn> {
	
	/** generated serial version uid */
	private static final long serialVersionUID = -867425216174015225L;
	
	public TurnEvent(Turn turn) {
		super(turn);
	}

	public Turn getTurn() {
		return getSource();
	}
}
