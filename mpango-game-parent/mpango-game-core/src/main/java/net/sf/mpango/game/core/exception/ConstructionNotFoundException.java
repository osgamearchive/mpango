package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.Construction;

public class ConstructionNotFoundException extends MPangoException {

	/** generated serial version uid */
	private static final long serialVersionUID = -6993066459898518099L;
	private Construction construction;
	
	public ConstructionNotFoundException (Construction construction) {
		super ();
		this.construction = construction;
	}
	
	public Construction getConstruction () {
		return this.construction;
	}
	
	public void setConstruction(Construction construction) {
		this.construction = construction;
	}
}
