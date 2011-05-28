package net.sourceforge.mpango.turn;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnEngineTest {

	private TurnEngine engine;
	
	@Before
	public void setUp() {
		engine = new TurnEngine();
	}
	
	@Test
	public void testRollTurn() {
		engine.passTurn();
		assertEquals(engine.getCurrentTurn(), Long.valueOf(1l));
	}
}
