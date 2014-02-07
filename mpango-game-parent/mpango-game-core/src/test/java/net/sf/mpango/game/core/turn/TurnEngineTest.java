package net.sf.mpango.game.core.turn;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.Observable;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.turn.entity.Turn;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.verify;

public class TurnEngineTest {

	private TurnEngine testing;

    @Mock
	private GameBoard gameBoard;

    @Mock
    private Observable mockBaseObservable;
	
	@Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
        Timer timer = new Timer();

		testing = new TurnEngine(mockBaseObservable, gameBoard);
        testing.setTimer(timer);
	}
	
	@Test
	public void testRollTurn() {
		final Turn initialTurn = testing.getCurrentTurn();

		final TurnEvent turnEvent = testing.passTurn();

        assertEvent(initialTurn, turnEvent);
        assertTurn(initialTurn);
        verify(mockBaseObservable).addListener(gameBoard);
        verify(mockBaseObservable).notifyListeners(turnEvent);
	}

    private void assertTurn(final Turn initialTurn) {
        final Turn currentTurn = testing.getCurrentTurn();
        assertNotNull(initialTurn.getTurnFinished());
        assertNotSame(initialTurn.getTurnStarted(), initialTurn.getTurnFinished());
        assertEquals(currentTurn.getTurnNumber() - 1, initialTurn.getTurnNumber());
        assertNotSame(currentTurn, initialTurn);
    }

    private void assertEvent(final Turn initialTurn, final TurnEvent turnEvent) {
        assertNotNull(turnEvent);
        assertNotSame(turnEvent.getTurn(), initialTurn);
        assertEquals(turnEvent.getSource(), initialTurn);
        assertNotNull(turnEvent.getOccurrenceTime());
    }
}
