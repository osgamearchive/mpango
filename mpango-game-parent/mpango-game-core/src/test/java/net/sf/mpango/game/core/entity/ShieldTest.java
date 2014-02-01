package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.exception.UselessShieldException;
import junit.framework.TestCase;

/**
 * Set of tests to demonstrate the behaviour of the Shield.
 * @author etux
 *
 */
public class ShieldTest extends TestCase {

	private static final Float MAXIMUM_SHIELD_HIT_POINTS = 5f;
	private Shield shield;

	public void setUp() {
		this.shield = new Shield(MAXIMUM_SHIELD_HIT_POINTS);
	}
	
	public void tearDown() {
		this.shield = null;
	}
	
	/**
	 * This test demonstrates how the shield takes damage decreasing its hit points. 
	 * @throws UselessShieldException
	 */
	public void testDamage() throws UselessShieldException {
		shield.receiveDamage(1f);
		assertEquals("The remaining hit points should be the expected", MAXIMUM_SHIELD_HIT_POINTS - 1f, this.shield.getRemainingHitPoints());
	}

	/**
	 * This test demonstrates that the repair implementation works.
	 * @throws UselessShieldException
	 */
	public void testRepair() throws UselessShieldException {
		shield.receiveDamage(1f);
		assertEquals("The remaining hit points should be the expected", MAXIMUM_SHIELD_HIT_POINTS - 1f, this.shield.getRemainingHitPoints());
		Float cost = shield.repair();
		assertEquals("The cost of the repair should be correct", 1f * 0.1f, cost);
		assertEquals("The shield should be repaired", MAXIMUM_SHIELD_HIT_POINTS, shield.getRemainingHitPoints());
	}
	
	/**
	 * This test demonstrates the raise of an exception in case of reusing a
	 * broken shield (a broken shield is one that has 0 hit points available).
	 * @throws UselessShieldException
	 */
	public void testInvalidReuse() throws UselessShieldException {
		try {
			shield.receiveDamage(5f);
		} catch (UselessShieldException unexpected) {
			throw unexpected;
		}
		try {
			shield.receiveDamage(1f);
			fail("Expected exception not raised");
		} catch (UselessShieldException expected) {
			//Expected behavior
		}
	}
}
