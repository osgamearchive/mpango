package net.sf.mpango.game.core.commands;

import java.util.List;
import java.util.concurrent.ExecutorService;

import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Observer;

public abstract class AbstractUnitTaskCommand extends AbstractTaskCommand<CommandEvent> implements UnitCommand<CommandEvent> {

	private Unit unit;

	public AbstractUnitTaskCommand(Unit unit, long millisPerSlice, ExecutorService executorService,
			List<Observer> observers) {
		super(millisPerSlice, executorService, observers);
		this.unit = unit;
	}

	public AbstractUnitTaskCommand(Unit unit, long millisPerSlice, ExecutorService executorService,
			Observer... observers) {
		super(millisPerSlice, executorService, observers);
		this.unit = unit;
	}

	public AbstractUnitTaskCommand(Unit unit, ExecutorService executorService, Observer... observers) {
		this(unit, 0, executorService, observers);
	}

	public Unit getUnit() {
		return unit;
	}
}
