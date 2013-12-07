package net.sf.mpango.game.core.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import net.sf.mpango.common.entity.AbstractPersistable;

/**
 * This interface determines the type of technology a player owns.
 * @author etux
 *
 */
@Entity
public class Technology extends AbstractPersistable<Long> {

	private int technologyCost;
	private List<Technology> requiredTechnologies;
	/**
	 * Method that returns the number of science units to be produced to achieve
	 * this technology.
	 * @return
	 */
	public int getTechnologyCost() {
		return technologyCost;
	}

	public void setTechnologyCost(int technologyCost) {
		this.technologyCost = technologyCost;
	}
	
	/**
	 * Returns the required technologies to be able to develop this one.
	 * @return list of required technologies.
	 */
	@ManyToMany
	public List<Technology> getRequiredTechnologies() {
		return requiredTechnologies;
	}

	public void setRequiredTechnologies(List<Technology> requiredTechnologies) {
		this.requiredTechnologies = requiredTechnologies;
	}
}