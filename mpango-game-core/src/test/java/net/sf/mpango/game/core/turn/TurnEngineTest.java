package net.sf.mpango.game.core.turn;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.turn.Timer;
import net.sf.mpango.game.core.turn.TurnEngine;
import net.sf.mpango.game.core.turn.entity.Turn;

import static org.easymock.classextension.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnEngineTest {

	private TurnEngine engine;
	private GameBoard gameBoard;
	
	@Before
	public void setUp() {
        Timer timer = new Timer();
		engine = new TurnEngine(timer);
		gameBoard = createMock(GameBoard.class);
	}
	
	@Test
	public void testRollTurn() {
		Turn initialTurn = engine.getCurrentTurn();
		gameBoard.receive(isA(TurnEvent.class));
		replay(gameBoard);
		TurnEvent turnEvent = engine.passTurn(gameBoard);
        assertEvent(initialTurn, turnEvent);
        assertTurn(initialTurn);
		verify(gameBoard);
	}

    private void assertTurn(Turn initialTurn) {
        Turn currentTurn = engine.getCurrentTurn();
        assertNotNull(initialTurn.getTurnFinished());
        assertNotSame(initialTurn.getTurnStarted(), initialTurn.getTurnFinished());
        assertEquals(currentTurn.getTurnNumber().longValue() - 1, initialTurn.getTurnNumber().longValue());
        assertNotSame(currentTurn, initialTurn);
    }

    private void assertEvent(Turn initialTurn, TurnEvent turnEvent) {
        assertNotNull(turnEvent);
        assertNotSame(turnEvent.getTurn(), initialTurn);
        assertEquals(turnEvent.getSource(), engine);
        assertNotNull(turnEvent.getTime());
    }
}
