package net.sourceforge.mpango.entity;

import javax.persistence.Entity;

import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.exception.EventNotSupportedException;

@Entity
public class City extends Construction {
	
	/** Constant stating the maximum number of hit points the city can take before being destroyed */
	private static final float MAX_HIT_POINTS = 1000;
	/** Constants stating the time it takes to construct a city */
	private static final int CONSTRUCTION_TIME = 10;
	
	public City() {
		super(Type.City, MAX_HIT_POINTS, CONSTRUCTION_TIME);
	}
	
	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		// TODO Auto-generated method stub
		
	}
}
