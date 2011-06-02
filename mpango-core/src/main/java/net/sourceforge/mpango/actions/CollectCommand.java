package net.sourceforge.mpango.actions;

import java.util.Timer;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Resources;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.exception.CellNotCollectableException;
import net.sourceforge.mpango.exception.CommandException;

public class CollectCommand extends AbstractTaskCommand {

	private Cell cell;
	private Unit unit;
	
	public CollectCommand(long millisPerSlice, Timer timer, Cell cell, Unit unit) {
		super(millisPerSlice, timer, cell, unit);
		this.cell = cell;
		this.unit = unit;
	}

	@Override
	public void evaluateExecution() throws CommandException {
		if (!cell.isCollectable()) throw new CellNotCollectableException(cell);
	}

	@Override
	public CommandExecutedEvent runExecute() {
		int foodCollected = (int) (unit.getCollectionSkills() * cell.getCollectionPoints());
		this.unit.putResources(Resources.FOOD, foodCollected);
		CommandExecutedEvent event = new CommandExecutedEvent(this);
		return event;
	}

	@Override
	public int calculateTotalTimeSlices() {
		int totalTimeSlices = (int) (cell.getCollectionPoints() * unit.getCollectionSkills());
		return totalTimeSlices;
	}
}