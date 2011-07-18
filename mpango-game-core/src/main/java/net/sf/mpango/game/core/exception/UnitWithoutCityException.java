package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.Unit;

public class UnitWithoutCityException extends CommandException {

	private Unit unit;
	
	public UnitWithoutCityException(Unit unit) {
		this.setUnit(unit);
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Unit getUnit() {
		return unit;
	}
	
	private static final long serialVersionUID = 649443217554474462L;
}
