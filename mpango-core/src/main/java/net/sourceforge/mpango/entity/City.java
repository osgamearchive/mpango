package net.sourceforge.mpango.entity;

import javax.persistence.Entity;

import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.exception.EventNotSupportedException;

@Entity
public class City extends Construction {
	private static final float MAX_HIT_POINTS = 1000;
	
	public City() {
		super(MAX_HIT_POINTS);
	}
	
	public City(float maximumHitPoints) {
		super(maximumHitPoints);
	}
	
	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		// TODO Auto-generated method stub
		
	}
}
