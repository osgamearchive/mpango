package net.sourceforge.mpango;

/**
 * Every entity that can receive damage should implement this interface
 * @author etux
 *
 */
public interface Damageable {

	/**
	 * Method called when the unit is attacked. It first directs the attack to the possible shield and then, in case of resting hit points, it will
	 * target them to the unit.
	 * @param attackPoints Number of attack points of the attack (how strong the attack is)
	 */
	public void receiveDamage(Float attackPoints);

	/**
	 * Determines if the unit is dead.
	 * @return true if the unit is dead.
	 */
	public boolean isDead();

	/**
	 * Health is determined by the relation between maximum hitpoints and the actual hitpoints. Health is always less than 1 and more than 0; 
	 * @return
	 */
	public float getHealth();

	/**
	 * Returns the total number of resting hit points the entity has
	 * @return
	 */
	public float getHitPoints();
	/**
	 * Method used to set the hit points of the unit to the maximum
	 * @return the cost of the repair in terms of units.
	 */
	public float repair();
}
