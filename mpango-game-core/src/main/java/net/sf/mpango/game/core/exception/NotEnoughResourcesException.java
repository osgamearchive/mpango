package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.enums.Resources;

public class NotEnoughResourcesException extends Exception {

	private Resources resource;
	private City city;
	
	public NotEnoughResourcesException (Resources resource, City city) {
		this.resource = resource;
		this.city = city;
	}
	private static final long serialVersionUID = 5922522072273573959L;

}
