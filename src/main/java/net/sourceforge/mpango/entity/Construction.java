package net.sourceforge.mpango.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import net.sourceforge.mpango.events.Listener;

/**
 * A construction is an element that can be created during the game.
 * Constructions take time, resources and man power to build.
 * Constructions provide the player who owns the with different advantages:
 * - Defensive power: some constructions can increase the defensive power in case there is a battle in the Terrain where the construction is built.
 * - Technology development: in order to be able to increase the speed of technology development, we need constructions such as Library, School, University, etc...
 * - Unit fabric: there are specific constructions that allow the creation of units. Each different construction can build different types of units.
 * - ...
 * @author etux
 *
 */
@Entity
public abstract class Construction implements Damageable, Listener {

	private Long identifier;
	private float maximumHitPoints;
	private Float hitPoints;
	private float defenseBonus;
	private float attackBonus;
	private float reparationCostPerPoint;
	
	public Construction (float maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
	}
	@Transient
	public float getHealth() {
		return hitPoints/maximumHitPoints;
	}
	@Transient
	public boolean isDead() {
		return hitPoints <= 0;
	}

	public void receiveDamage(Float attackPoints) {
		hitPoints -= attackPoints;
	}
	/**
	 * Method that repairs the construction by reestablishing it's hit points to the maximum.
	 * @return cost of the repair
	 */
	public float repair() {
		float cost = (maximumHitPoints - hitPoints) * reparationCostPerPoint;
		hitPoints = maximumHitPoints;
		return cost;
	}
	@Column
	public float getHitPoints() {
		return hitPoints ;
	}
	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}
	@Column
	public Float getMaximumHitPoints() {
		return maximumHitPoints;
	}
	public void setMaximumHitPoints(float maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
	}
	@Column
	public float getDefenseBonus() {
		return defenseBonus;
		
	}
	public void setDefenseBonus(float defenseBonus) {
		this.defenseBonus = defenseBonus;
	}
	@Column
	public float getAttackBonus() {
		return attackBonus;
	}
	public void setAttackBonus(float attackBonus) {
		this.attackBonus = attackBonus;
	}
	@Id
	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
}
