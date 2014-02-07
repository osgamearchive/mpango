package net.sf.mpango.game.core.turn;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.BaseObservable;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Observable;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.turn.entity.Turn;

/**
 * <p>The turn engine is the class that drives turns.
 * This engine has the responsibilities to notify the execution of turns.</p>
 * @author etux
 *
 */
public class TurnEngine implements Observable {

    protected static final int DEFAULT_TURN_CAPACITY_QUEUE = 100;

    private Observable baseObservable;
	private Turn currentTurn;
    private Timer timer;
    private Queue<Turn> turnQueue;
	
	public TurnEngine(final GameBoard gameBoard) {
        this(null, gameBoard);
	}

    //For testing purposes we have this package protected constructor.
    TurnEngine(final Observable baseObservable, final GameBoard gameBoard) {
        this.currentTurn = new Turn(0, new Date());
        turnQueue = new ArrayBlockingQueue<>(DEFAULT_TURN_CAPACITY_QUEUE);

        //If the baseObservable is null, create one.
        if (baseObservable == null)
            this.baseObservable = new BaseObservable();
        else
            this.baseObservable = baseObservable;

        baseObservable.addListener(gameBoard);
    }
	
	public TurnEvent passTurn() {
        this.currentTurn.setTurnFinished(new Date());
        turnQueue.add(currentTurn);
		this.currentTurn = new Turn(getNextTurn(this.currentTurn), new Date());
		final TurnEvent turnEvent = new TurnEvent(this.currentTurn);
        notifyListeners(turnEvent);
        return turnEvent;
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}

    private long getNextTurn(Turn currentTurn) {
        return currentTurn.getTurnNumber() + 1l;
    }

    //For testing purposes only
	void setTimer(final Timer timer) {
		this.timer = timer;
	}

    //For testing purposes only
    void setBaseObservable(final Observable baseObservable) {
        this.baseObservable = baseObservable;
    }

    @Override
    public void notifyListeners(Event event) {
        baseObservable.notifyListeners(event);
    }

    @Override
    public void addListener(Observer observer) {
        baseObservable.addListener(observer);
    }

    @Override
    public void removeListener(Observer observer) {
        baseObservable.removeListener(observer);
    }
}
