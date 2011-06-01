package net.sourceforge.mpango.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import net.sourceforge.mpango.events.Listener;

/**
 * <p>A construction is an element that can be created by a unit.</p>
 * <p>Constructions take time, resources and man power to build.</p>
 * <p>Constructions provide the player who owns the with different advantages:
 * 	<ol>
 * 		<li>- Defensive power: some constructions can increase the defensive power in case there is a battle in the Terrain where the construction is built.</li>
 * 		<li>- Science development: in order to be able to increase the speed of technology development, we need constructions such as Library, School, University, etc...</li>
 * 		<li>- Commerce development: </li>
 * 		<li>- Militar development: </li>
 * 	</ol>	
 * </p>
 * <p>A construction is equal to another constructions when they are of the same type.</p>
 * @author etux
 *
 */
@Entity
public abstract class Construction implements Damageable, Listener {

	private Long identifier;
	private float maximumHitPoints;
	private float hitPoints;
	private float defenseBonus;
	private float attackBonus;
	private float reparationCostPerPoint;
	private int constructionTime;
	private Type type;
	
	enum Type {
		City
		};
	
	public Construction (Type type, float maximumHitPoints, int constructionTime) {
		this.maximumHitPoints = maximumHitPoints;
		this.type = type;
		this.constructionTime = constructionTime;
	}
	
	@Transient
	public float getHealth() {
		return hitPoints/maximumHitPoints;
	}
	@Transient
	public boolean isDead() {
		return hitPoints <= 0;
	}

	/**
	 * <p>Method that takes the damage from an attack substracting it to the total amount of hit poinst that the construction has.</p>
	 * @param attackPoints
	 */
	public void receiveDamage(float attackPoints) {
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
	
	public int getConstructionTime() {
		return this.constructionTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Construction other = (Construction) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
}
