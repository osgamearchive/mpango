package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.turn.entity.Turn;

public class TurnEvent extends AbstractEvent {
	
	/** generated serial version uid */
	private static final long serialVersionUID = -867425216174015225L;
	
	private Turn turn;
	
	public TurnEvent(Object source, Turn turn) {
		super(source);
		this.turn = turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public Turn getTurn() {
		return turn;
	}
}
