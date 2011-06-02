package net.sourceforge.mpango.entity;

import java.util.Hashtable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.events.NotEnoughResourcesException;
import net.sourceforge.mpango.exception.EventNotSupportedException;

@Entity
public class City extends Construction {
	
	/** Constant stating the maximum number of hit points the city can take before being destroyed */
	private static final float MAX_HIT_POINTS = 1000;
	/** Constants stating the time it takes to construct a city */
	private static final int CONSTRUCTION_TIME = 10;
	
	private Hashtable<Resources, Integer> resources;
	
	public City() {
		super(Type.City, MAX_HIT_POINTS, CONSTRUCTION_TIME);
		setResources(new Hashtable<Resources, Integer>());
		
	}
	
	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		// TODO Auto-generated method stub
		
	}

	public void addResources(Resources resource, int quantity) {
		Integer totalResources = resources.get(resource);
		if (totalResources == null) {
			totalResources = quantity;
		} else {
			totalResources += quantity;
		}
		resources.put(resource, totalResources);
	}
	
	public void substractResources(Resources resource, int quantity) throws NotEnoughResourcesException {
		Integer totalResources = resources.get(resource);
		if (totalResources == null) {
			throw new NotEnoughResourcesException(resource, this);
		} else {
			if (totalResources - quantity < 0) {
				throw new NotEnoughResourcesException(resource, this);
			} else {
				totalResources -= quantity;
			}
		}
		resources.put(resource, totalResources);
	}

	@ManyToOne
	public Hashtable<Resources, Integer> getResources() {
		return resources;
	}
	public void setResources(Hashtable<Resources, Integer> resources) {
		this.resources = resources;
	}
}
