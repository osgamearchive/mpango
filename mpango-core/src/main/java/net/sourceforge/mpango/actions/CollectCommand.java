package net.sourceforge.mpango.actions;

import java.util.Timer;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Resources;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.exception.CellNotCollectableException;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.UnitWithoutCityException;

/**
 * Collection command used by Units employed to collect resources. 
 * The initial implementation is tied to Food, but new resources will be added in the future.
 * @author edvera
 *
 */
public class CollectCommand extends AbstractTaskCommand {

	private Cell cell;
	private Unit unit;
	
	/**
	 * Constructor for the Collect Command.
	 * @param millisPerSlice configuration parameter
	 * @param timer used to schedule the execution of the task
	 * @param cell Cell on which the collection is taking place.
	 * @param unit that executes the task.
	 */
	public CollectCommand(long millisPerSlice, Timer timer, Cell cell, Unit unit) {
		super(millisPerSlice, timer, cell, unit);
		this.cell = cell;
		this.unit = unit;
	}

	/**
	 * Method that evaluates of the cell is collectable.
	 * @Throws CommandException
	 */
	@Override
	public void evaluateExecution() throws CommandException {
		if (!cell.isCollectable()) throw new CellNotCollectableException(cell);
		if (unit.getCity() == null) throw new UnitWithoutCityException(unit);
	}

	/**
	 * Method that executes the logic of the command.
	 * @return CommandExecutedEvent Event representing the execution of the command.
	 */
	@Override
	public CommandExecutedEvent runExecute() {
		int foodCollected = (int) (unit.getCollectionSkills() * cell.getCollectionPoints());
		this.unit.putResources(Resources.FOOD, foodCollected);
		CommandExecutedEvent event = new CommandExecutedEvent(this);
		return event;
	}

	/**
	 * Method that calculates the total time slices the command needs in order to be completed.
	 * @return int number of time slices.
	 */
	@Override
	public int calculateTotalTimeSlices() {
		int totalTimeSlices = (int) (cell.getCollectionPoints() * unit.getCollectionSkills());
		return totalTimeSlices;
	}
}