package net.sourceforge.mpango.turn;

import java.util.Date;

import net.sourceforge.mpango.entity.GameBoard;
import net.sourceforge.mpango.events.TurnEvent;
import net.sourceforge.mpango.turn.entity.Turn;

/**
 * <p>The turn engine is the class that drives turns. This engine has the responsibilities to notify the execution of turns.</p>
 * @author etux
 *
 */
public class TurnEngine {

	private Turn currentTurn;
	
	public TurnEngine() {
		currentTurn = new Turn(0, new Date());
	}
	
	
	public void passTurn(GameBoard gameBoard) {
		TurnEvent turnEvent = new TurnEvent(this, currentTurn);
		gameBoard.passTurn(turnEvent);
		currentTurn.setTurnFinished(new Date());
		Long nextTurnNumber = currentTurn.getTurnNumber() + 1;
		currentTurn = new Turn(nextTurnNumber, new Date());
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}

}
