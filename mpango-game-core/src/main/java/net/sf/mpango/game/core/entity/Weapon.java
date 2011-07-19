package net.sf.mpango.game.core.entity;

import javax.persistence.Entity;

import net.sf.mpango.common.entity.AbstractPersistable;


/**
 * This class represents a offence weapon and increases the attack points of the unit that carries it.
 * @author etux
 *
 */
@Entity
public class Weapon extends AbstractPersistable {

	private Float attackBonus;
	
	public Weapon (Float attackPoints) {
		this.attackBonus = attackPoints;	
	}
	public Float getAttackBonus() {
		return this.attackBonus;
	}
	public void setAttackBonus(Float attackBonus) {
		this.attackBonus = attackBonus;
	}

}
