package net.sf.mpango.game.core;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sf.mpango.game.core.battle.BattleEngineTest;
import net.sf.mpango.game.core.entity.ShieldTest;
import net.sf.mpango.game.core.events.EventChannelTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.sourceforge.romulan.battle");
		//$JUnit-BEGIN$
		suite.addTestSuite(ShieldTest.class);
		suite.addTestSuite(BattleEngineTest.class);
		suite.addTestSuite(EventChannelTest.class);
		//$JUnit-END$
		return suite;
	}

}
