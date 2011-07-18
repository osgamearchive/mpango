package net.sf.mpango.game.core;

import net.sf.mpango.game.core.battle.BattleEngineTest;
import net.sf.mpango.game.core.entity.FleetTest;
import net.sf.mpango.game.core.entity.ShieldTest;
import net.sf.mpango.game.core.entity.UnitTest;
import net.sf.mpango.game.core.events.EventChannelTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.sourceforge.romulan.battle");
		//$JUnit-BEGIN$
		suite.addTestSuite(ShieldTest.class);
		suite.addTestSuite(UnitTest.class);
		suite.addTestSuite(FleetTest.class);
		suite.addTestSuite(BattleEngineTest.class);
		suite.addTestSuite(EventChannelTest.class);
		//$JUnit-END$
		return suite;
	}

}
