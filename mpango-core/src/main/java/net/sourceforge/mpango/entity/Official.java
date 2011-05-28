package net.sourceforge.mpango.entity;

import java.util.List;

import net.sourceforge.mpango.exception.UnknownTechnologyException;

public class Official extends Unit {

	private static final long serialVersionUID = 1L;

	public Official(List<Technology> technologies, Float attackPoints, Float maximumHitPoints) throws UnknownTechnologyException {
		super(technologies, attackPoints, maximumHitPoints);
	}

}
