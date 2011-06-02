package net.sourceforge.mpango.exception;

import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.entity.Resources;

public class NotEnoughResourcesException extends Exception {

	private Resources resource;
	private City city;
	
	public NotEnoughResourcesException (Resources resource, City city) {
		this.resource = resource;
		this.city = city;
	}
	private static final long serialVersionUID = 5922522072273573959L;

}
