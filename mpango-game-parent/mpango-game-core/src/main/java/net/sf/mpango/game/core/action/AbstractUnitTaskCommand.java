package net.sf.mpango.game.core.action;

import java.util.List;
import java.util.Timer;

import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.events.Listener;

public abstract class AbstractUnitTaskCommand extends AbstractTaskCommand implements UnitCommand {

	private Unit unit;

	public AbstractUnitTaskCommand(Unit unit, long millisPerSlice, Timer timer,
			List<Listener> listeners) {
		super(millisPerSlice, timer, listeners);
		this.unit = unit;
	}

	public AbstractUnitTaskCommand(Unit unit, long millisPerSlice, Timer timer,
			Listener... listeners) {
		super(millisPerSlice, timer, listeners);
		this.unit = unit;
	}

	public AbstractUnitTaskCommand(Unit unit, Timer timer, Listener... listeners) {
		this(unit, 0, timer, listeners);
	}

	public Unit getUnit() {
		return unit;
	}
}
