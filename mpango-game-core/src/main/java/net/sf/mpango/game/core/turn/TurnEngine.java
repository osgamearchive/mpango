package net.sf.mpango.game.core.turn;

import java.util.Date;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.turn.entity.Turn;

/**
 * <p>The turn engine is the class that drives turns.
 * This engine has the responsibilities to notify the execution of turns.</p>
 * @author etux
 *
 */
public class TurnEngine {

	private Turn currentTurn;
    private Timer timer;
	
	public TurnEngine(Timer timer) {
		this.currentTurn = new Turn(0, new Date());
        this.setTimer(timer);
	}
	
	public TurnEvent passTurn(GameBoard gameBoard) {
        this.currentTurn.setTurnFinished(new Date());
		this.currentTurn = new Turn(getNextTurn(this.currentTurn), new Date());
		TurnEvent turnEvent = new TurnEvent(this, this.currentTurn);
		gameBoard.receive(turnEvent);
        return turnEvent;
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}

    private Long getNextTurn(Turn currentTurn) {
        Long nextTurnNumber = currentTurn.getTurnNumber() + Long.valueOf(1l);
        return nextTurnNumber;
    }

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
