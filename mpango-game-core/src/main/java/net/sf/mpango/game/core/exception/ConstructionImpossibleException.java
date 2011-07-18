package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.Construction;

public class ConstructionImpossibleException extends CommandException {

	private Construction construction;
	
	public ConstructionImpossibleException(Construction construction) {
		this.setConstruction(construction);
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
	}

	public Construction getConstruction() {
		return construction;
	}

	private static final long serialVersionUID = -2511720300526629703L;

}
