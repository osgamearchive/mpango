package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.core.exception.NotEnoughResourcesException;

@Entity
public class City extends Construction {
	
	/** Constant stating the maximum number of hit points the city can take before being destroyed */
	private static final float MAX_HIT_POINTS = 1000;
	/** Constants stating the time it takes to construct a city */
	private static final int CONSTRUCTION_TIME = 10;
	
	private Hashtable<Resources, Integer> resources;
	private List<Unit> units;
	
	public City() {
		super(ConstructionType.CITY, MAX_HIT_POINTS, CONSTRUCTION_TIME);
		setResources(new Hashtable<Resources, Integer>());
		units = new ArrayList<Unit>();
	}
	
	@Override
	public void receive(Event event) throws EventNotSupportedException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method that adds resources to the city.
	 * @param resource resource being added.
	 * @param quantity quantity of the resource being added.
	 */
	public void addResources(Resources resource, int quantity) {
		Integer totalResources = resources.get(resource);
		if (totalResources == null) {
			totalResources = quantity;
		} else {
			totalResources += quantity;
		}
		resources.put(resource, totalResources);
	}
	
	/**
	 * Method that takes resources from the city
	 * @param resource resource being taken.
	 * @param quantity quantity of the resource being taken.
	 * @throws NotEnoughResourcesException In case the quantity of the requested resources is higher than the actual one.
	 */
	public void substractResources(Resources resource, int quantity) throws NotEnoughResourcesException {
		Integer totalResources = resources.get(resource);
		if (totalResources == null || totalResources - quantity < 0) {
			throw new NotEnoughResourcesException(resource, this);
		} else {
			totalResources -= quantity;
		}
		resources.put(resource, totalResources);
	}

	@Override
	public String toString() {
		return "City [resources=" + resources + ", units=" + units + "]";
	}

	/**
	 * Getter
	 * @return
	 */
	@ManyToOne
	public Hashtable<Resources, Integer> getResources() {
		return resources;
	}
	public void setResources(Hashtable<Resources, Integer> resources) {
		this.resources = resources;
	}
	
	public void addUnit(Unit unit) {
		this.units.add(unit);
	}

	public void removeUnit(Unit unit) {
		this.units.remove(unit);		
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
}
