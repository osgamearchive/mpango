package net.sf.mpango.game.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.sf.mpango.common.entity.AbstractEntity;
import net.sf.mpango.game.core.action.Command;
import net.sf.mpango.game.core.action.ConstructCommand;
import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.BaseListener;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.ConstructionAlreadyInPlaceException;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.core.exception.UnknownTechnologyException;
import net.sf.mpango.game.core.exception.UselessShieldException;
import net.sf.mpango.game.core.technology.entity.ShieldTechnology;
import net.sf.mpango.game.core.technology.entity.WeaponTechnology;

/**
 * <p>Abstract class that contains the basic attributes and methods for all units.</p>
 * <p>Every kind of units has certain amount of attack points(AP) and hit points(HP), if the unit's HP reaches zero, it dies / blows up</p>
 * <p>Every unit is created using different technologies. So the attributes of the unit vary depending on the technologies that are used at creation time.</p>
 * @author edvera
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Unit extends AbstractEntity<Long> implements Damageable, Listener, Serializable {
    
	private static final long serialVersionUID = -3825620941652893699L;
	private float maximumHitPoints;
	private Float attackPoints;
	private Float hitPoints;
	private Shield shield;
	private Weapon weapon;
	private List<Technology> technologies;
	private float constructionSkills;
	private float collectionSkills;
	private List<Command> commands;
	private Timer timer;
	private City city;

    private BaseListener baseListener;
	
	/**
	 * At the beginning of the game the Unit can not belong to a city.
	 */
	public Unit() {}
	
	/**
	 * <p>Every time a unit is created it is done by assigning it a city.
	 * All units in the game belong to a city, to and from which resources flow.
	 * The only exception to this are the initial units, which don't belong to a city until the first city is founded.</p>
	 * @param city where the unit is born or has moved.
	 */
	protected Unit(final City city) {
		this (city, new ArrayList<Command>(), new ArrayList<Technology>(), 0f, 0f);
	}
	
	/**
	 * Convenient constructor for test classes.
	 * @param city
	 * @param commands
	 * @param attackPoints
	 * @param maximumHitPoints
	 */
	protected Unit (final City city,
                  final List<Command> commands,
                  final List<Technology> technologies,
                  final float attackPoints,
                  final float maximumHitPoints) {
		this.city = city;
		this.commands = commands;
		this.setTechnologies(technologies);
		this.hitPoints = maximumHitPoints;
		this.maximumHitPoints = maximumHitPoints;
		this.attackPoints = attackPoints;
		this.timer = new Timer();
        this.baseListener = new BaseListener();
	}
	
	/**
	 * Method that serves to apply a shield technology.
	 * @param technology
	 */
	private void applyTechnology(ShieldTechnology technology) {
		this.shield = technology.createShield();
	}

	/**
	 * Method that serves to aply a weapon technology.
	 * @param technology
	 */
	private void applyTechnology(WeaponTechnology technology) {
		this.weapon = technology.createWeapon();
	}
	
	/**
	 * Method that switches WeaponTechnology or ShieldTechnology. 
	 * @param technology
	 * @throws UnknownTechnologyException in case the technology to apply is aplicable to the unit.
	 */
	private void applyTechnology(Technology technology) throws UnknownTechnologyException {
		if (technology instanceof WeaponTechnology) {
			applyTechnology((WeaponTechnology) technology);
		} else if (technology instanceof ShieldTechnology) {
			applyTechnology((ShieldTechnology) technology);
		} else {
			throw new UnknownTechnologyException(technology);
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
	@Transient
	public void receiveDamage(float attackPoints) {
		float remainingDamage = attackPoints;
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
	 * <p>Health is determined by the relation between maximum hit points and the actual hit points. 
	 * Health is always less than 1 and more than 0;</p> 
	 * @return
	 */
	@Transient
	public float getHealth() {
		float hitPoints = this.hitPoints;
		float maximumHitPoints = this.maximumHitPoints;
		float realHealth = hitPoints / maximumHitPoints;
		return realHealth > 0 ? realHealth : 0;
	}
	/**
	 * <p>Action the Unit can perform in order to settle in a cell resulting in a new city construction.</p>
	 * @return
	 * @throws ConstructionAlreadyInPlaceException 
	 */
	public ConstructCommand settle(Cell cell) throws CommandException {
		return construct(cell, new City());
	}
	
	/**
	 * <p>Action to to a put to work in a specific construction. The unit will be busy for a certain time until the construction is finished.</p>
	 * @param cell
	 * @param construction
	 * @return 
	 * @throws ConstructionAlreadyInPlaceException
	 */
	protected ConstructCommand construct(Cell cell, Construction construction) throws CommandException {
		ConstructCommand command = new ConstructCommand(this, construction, cell);
		this.addCommand(command);
		return command;
	}
	
	/**
	 * <p>Method that adds a command to the command queue. If it is the first command, it also executes it after having it added.</p>
	 * @param command Command to be added and possibly executed.
	 * @throws CommandException In case there is a problem with the execution of the command.
	 */
	public void addCommand(Command command) throws CommandException {
		boolean executeCommand = false;
		if (commands.size() == 0)
			executeCommand = true;
		this.commands.add(command);
		if (executeCommand)
			executeCommand(command);
	}

	/**
	 * <p>Method that executes a given command</p>
	 * @param command to be executed
	 * @throws CommandException In case there is a problem with the execution of the command.
	 */
	private void executeCommand(Command command) throws CommandException {
		command.execute();
	}
	/**
	 * <p>Method that removes a command. This can happen in two cases:
	 * <ol>
	 * 	<li>The user decides to cancel a specific command</li>
	 *  <li>The command has been successfully executed and must be removed</li>
	 * <oL>
	 * </p>
	 * <p>This method is also responsible for executing the next available command.</p>
	 * @param command to be removed.
	 * @throws CommandException In case there is a problem with the removal of the command.
	 */
	public void removeCommand(Command command) throws CommandException {
		System.out.println("Removing command: "+command);
		this.commands.remove(command);
		if (commands.iterator().hasNext()) {
			executeCommand(commands.iterator().next());
		}
	}
	
	@Transient
	public float repair() {
		return 0;
	}
	
	@Transient
	public void putResources(Resources resource, int foodCollected) {
		city.addResources(resource, foodCollected);
	}
	public float getHitPoints() {
		return this.hitPoints;
	}
	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}
	@Column
	public float getMaximumHitPoints() {
		return maximumHitPoints;
	}

    public void setMaximumHitPoints(float maximumHitPoints) {
        this.maximumHitPoints = maximumHitPoints;
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
		Iterator<Technology> iterator = technologies.iterator();
		while (iterator.hasNext()) {
			try {
				applyTechnology(iterator.next());
			} catch (UnknownTechnologyException e) {
				//Do nothing
			}
		}
		this.technologies = technologies;
	}
	@Override
	public void receive(Event event) throws EventNotSupportedException {
		receive ((CommandEvent) event);
	}
	
	private void receive(CommandEvent commandEvent) {
		try {
			this.removeCommand(commandEvent.getCommand());
		} catch (CommandException e) {
			e.printStackTrace(); //If this happens is probably due to a code flaw.
		}
	}
	public float getConstructionSkills() {
		return this.constructionSkills;
	}
	public void setConstructionSkills(float constructionSkills) {
		this.constructionSkills = constructionSkills;
	}
	public void improveConstructionSkills(float skillsUpgrade) {
		this.constructionSkills+=skillsUpgrade;
	}
	@Transient
	public Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		return timer;
	}
	//For testing purposes.
	protected void setTimer(Timer timer) {
		this.timer = timer;
	}
	public float getCollectionSkills() {
		return this.collectionSkills;
	}
	public void setCollectionSkills(float collectionSkills) {
		this.collectionSkills = collectionSkills;
	}
	public void improveCollectionSkills(float skillsUpgrade) {
		this.collectionSkills += skillsUpgrade;
	}
    public City getCity() {
   		return city;
   	}
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Method that kills the unit.
	 */
	public void die() {
		this.timer.cancel();
		city.removeUnit(this);
		this.city = null;
	}
}