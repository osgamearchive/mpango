package net.sourceforge.mpango.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.mpango.entity.technology.ShieldTechnology;
import net.sourceforge.mpango.entity.technology.WeaponTechnology;
import net.sourceforge.mpango.exception.UnknownTechnologyException;
import net.sourceforge.mpango.exception.UselessShieldException;

/**
 * Abstract class that contains the basic attributes and methods for all units.
 * Every kind of units has certain amount of attack points(AP) and hit points(HP), if the unit's HP reaches zero, it dies / blows up
 * Every unit is created using different technologies. So the attributes of the unit vary depending on the technologies that are used at creation time.
 * @author etux
 *
 */
@Entity
public abstract class Unit extends AbstractPersistable implements Damageable,Serializable {

	private static final Log logger = LogFactory.getLog(Unit.class);
	private final Float maximumHitPoints;
	private Float attackPoints;
	private Float hitPoints;
	private Shield shield;
	private Weapon weapon;
	private List<Technology> technologies;
	
	public Unit() {
		this.maximumHitPoints = 0f;
		this.attackPoints = 0f;
		this.shield = null;
		this.weapon = null;
		this.setTechnologies(new ArrayList<Technology>());
	}
	
	public Unit (List<Technology> technologies, Float attackPoints, Float maximumHitPoints) throws UnknownTechnologyException{
		this.maximumHitPoints = maximumHitPoints;
		this.hitPoints = maximumHitPoints;
		this.attackPoints = attackPoints;
		this.setTechnologies(technologies);
		Iterator<Technology> iterator = technologies.iterator();
		while (iterator.hasNext()) {
			applyTechnology(iterator.next());
		}
	}
	
	private void applyTechnology(ShieldTechnology technology) {
		this.shield = technology.createShield();
	}

	private void applyTechnology(WeaponTechnology technology) {
		this.weapon = technology.createWeapon();
	}
	
	private void applyTechnology(Technology technology) throws UnknownTechnologyException {
		if (technology instanceof WeaponTechnology) {
			applyTechnology((WeaponTechnology) technology);
		} else if (technology instanceof ShieldTechnology) {
			applyTechnology((ShieldTechnology) technology);
		} else {
			logger.error("Unknown Technology: " + technology);
			throw new UnknownTechnologyException();
		}
	}
	
	
	/**
	 * This method calculates the effective attack points the unit has.
	 * The three factors that are important are:
	 * - attackBonus from the weapon.
	 * - health of the unit.
	 * - attack points of the unit.
	 * @return
	 */
	@Transient
	public Float getEffectiveAttackPoints() {
		return attackPoints * weapon.getAttackBonus() * this.getHealth();
	}
	
	/**
	 * Method called when the unit is attacked. It first directs the attack to the possible shield and then, in case of resting hit points, it will
	 * target them to the unit.
	 * @param attackPoints Number of attack points of the attack (how strong the attack is)
	 */
	public void receiveDamage(Float attackPoints) {
		Float remainingDamage = attackPoints;
		if (this.shield != null) {
			try {
				remainingDamage = shield.receiveDamage(attackPoints);
				if (remainingDamage > 0) { //The shield has been destroyed on this attack
					this.shield = null;
				}
			} catch (UselessShieldException e) { //The shield has been destroyed previously (possible bug)
				this.shield = null;
			}
		}
		this.hitPoints -= remainingDamage;
	}
	
	/**
	 * Determines if the unit is dead.
	 * @return true if the unit is dead.
	 */
	@Transient
	public boolean isDead() {
		return (this.hitPoints <= 0);
	}
	
	/**
	 * Health is determined by the relation between maximum hitpoints and the actual hitpoints. Health is always less than 1 and more than 0; 
	 * @return
	 */
	@Transient
	public float getHealth() {
		float hitPoints = this.hitPoints;
		float maximumHitPoints = this.maximumHitPoints;
		float realHealth = hitPoints / maximumHitPoints;
		return realHealth > 0 ? realHealth : 0;
	}
	@Transient
	public float repair() {
		return 0;
	}
	public float getHitPoints() {
		return this.hitPoints;
	}
	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}
	@OneToOne
	public Weapon getWeapon() {
		return this.weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	@OneToOne
	public Shield getShield() {
		return this.shield;
	}
	public void setShield(Shield shield) {
		this.shield = shield;
	}
	@OneToMany
	public List<Technology> getTechnologies() {
		return technologies;
	}
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}
}