package net.sourceforge.mpango.battle;

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
		//$JUnit-END$
		return suite;
	}

}
