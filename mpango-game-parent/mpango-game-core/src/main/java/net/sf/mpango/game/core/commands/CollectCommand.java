package net.sf.mpango.game.core.commands;

import java.util.concurrent.ExecutorService;

import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.exception.CellNotCollectableException;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.UnitWithoutCityException;

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
	 * @param executorService used to schedule the execution of the task
	 * @param cell Cell on which the collection is taking place.
	 * @param unit that executes the task.
	 */
	public CollectCommand(final long millisPerSlice, final ExecutorService executorService, final Cell cell, final Unit unit) {
		super(millisPerSlice, executorService, cell, unit);
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
	public CommandExecutedEvent execute() {
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