package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.entity.GameBoard;

/**
 * Game board event.
 * @author etux
 *
 */
public class GameBoardEvent extends AbstractEvent {

	public GameBoardEvent(GameBoard source) {
		super(source);
	}
	
	public GameBoard getBoard() {
		return (GameBoard) this.getSource();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3985516876445203725L;
}
