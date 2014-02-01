package net.sf.mpango.game.core.entity;

import javax.persistence.Entity;

import net.sf.mpango.common.entity.AbstractEntity;


/**
 * This class represents a offence weapon and increases the attack points of the unit that carries it.
 * @author etux
 *
 */
@Entity
public class Weapon extends AbstractEntity<Long> {

	private float attackBonus;

    public Weapon() {}

	public Weapon (float attackPoints) {
		this.attackBonus = attackPoints;	
	}
	public float getAttackBonus() {
		return this.attackBonus;
	}
	public void setAttackBonus(float attackBonus) {
		this.attackBonus = attackBonus;
	}

}
