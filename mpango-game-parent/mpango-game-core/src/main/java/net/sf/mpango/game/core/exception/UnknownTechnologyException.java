package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.Technology;

public class UnknownTechnologyException extends Exception {

	private Technology technology;
	
	public UnknownTechnologyException(Technology technology) {
		this.technology = technology;
	}
	
	public Technology getUnknownTechnology() {
		return technology;
	}

	/** generate serial version uid */
	private static final long serialVersionUID = -7445060104212760931L;

}
