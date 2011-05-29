package net.sourceforge.mpango.turn;

import net.sourceforge.mpango.entity.GameBoard;
import net.sourceforge.mpango.events.TurnEvent;
import net.sourceforge.mpango.turn.entity.Turn;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnEngineTest {

	private TurnEngine engine;
	private GameBoard gameBoard;
	
	@Before
	public void setUp() {
		engine = new TurnEngine();
		gameBoard = createMock(GameBoard.class);
	}
	
	@Test
	public void testRollTurn() {
		Turn initialTurn = engine.getCurrentTurn();
		gameBoard.passTurn(isA(TurnEvent.class));
		replay(gameBoard);
		engine.passTurn(gameBoard);
		Turn currentTurn = engine.getCurrentTurn();
		assertNotNull(initialTurn.getTurnFinished());
		assertNotSame(initialTurn.getTurnStarted(), initialTurn.getTurnFinished());
		assertEquals(currentTurn.getTurnNumber().longValue() - 1, initialTurn.getTurnNumber().longValue());
		assertNotSame(currentTurn, initialTurn);
		verify(gameBoard);
	}
}
