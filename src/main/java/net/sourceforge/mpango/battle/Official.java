package net.sourceforge.mpango.battle;

import java.util.List;

import net.sourceforge.mpango.Technology;
import net.sourceforge.mpango.Unit;
import net.sourceforge.mpango.UnknownTechnologyException;

public class Official extends Unit {

	private static final long serialVersionUID = 1L;

	public Official(List<Technology> technologies, Float attackPoints, Float maximumHitPoints) throws UnknownTechnologyException {
		super(technologies, attackPoints, maximumHitPoints);
	}

}
