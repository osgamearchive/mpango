package net.sourceforge.mpango.actions;

import java.util.Timer;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Construction;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.ConstructionAlreadyInPlaceException;

/**
 * Command that represents the construction action.
 * 
 * @author etux
 *
 */
public class ConstructCommand extends AbstractTaskCommand {

	/** Constant containing the value of the skills upgrade for a unit every time it executes this command */
	protected static final float SKILLS_UPGRADE = 0.1f;
	/** Constant factor that allows to calculate the minimum construction time */
	protected static final float MINIMUM_FACTOR = 0.1f;
	/** Constant factor that determines the delay of the timer */
	protected static final long DELAY_FACTOR_MS = 1000;
	
	private Unit unit;
	private Construction construction;
	private Cell cell;
	
	/**
	 * Command builder with all details needed in order to execute the command.
	 * @param unit Unit that is executing the action.
	 * @param construction Construction to be constructed.
	 * @param cell Cell where the construction will be constructed.
	 * @throws CommandException in case it is not possible to construct.
	 */
	public ConstructCommand(Unit unit, Construction construction, Cell cell) {
		this(unit.getTimer(), unit, construction, cell);
	}
	
	/**
	 * Construction builder in order to separate the Timer from the unit involved in the construction.
	 * Thought for unit testing but could be interesting if instead of unit based timers, we want to use player based timers.
	 * @param timer
	 * @param unit
	 * @param construction
	 * @param cell
	 */
	public ConstructCommand(Timer timer, Unit unit, Construction construction, Cell cell) {
		super(timer, unit, cell);
		this.unit = unit;
		this.cell = cell;
		this.construction = construction;
	}
	/**
	 * Method that calculates the amount of time slices the command needs to execute.
	 * @param constructionTime
	 * @param constructionSkills
	 * @return
	 */
	private int calculateTotalTimeSlices (int constructionTime, float constructionSkills) {
		int unitFactor 		= (int) (constructionTime * constructionSkills);
		int minimumTime 	= (int) (constructionTime * MINIMUM_FACTOR);
		int timeSlices 		= ((constructionTime - unitFactor > 0) ? (constructionTime - unitFactor) : 0) + minimumTime;
		return timeSlices;
	}

	/**
	 * Method than contains the logic to execute for the command.
	 * @returns CommandExecutedEvent even with the this command.
	 */
	@Override
	public CommandExecutedEvent runExecute() {
		try {
			this.cell.addConstruction(construction);
			this.unit.improveConstructionSkills(SKILLS_UPGRADE);
		} catch (ConstructionAlreadyInPlaceException e) {
			e.printStackTrace();
		}
		CommandExecutedEvent event = new CommandExecutedEvent(this);
		return event;
	}

	/**
	 * Method that evaluates the execution of the command prior to it actually happening.
	 * @throws CommandException in case the command can not be executed.
	 */
	@Override
	public void evaluateExecution() throws CommandException {
		if (cell.containsConstruction(construction)) throw new ConstructionAlreadyInPlaceException(construction);
	}

	/**
	 * Method that calculates the total amount of time slices the command needs to execute.
	 * @return number of time slices
	 */
	@Override
	public int calculateTotalTimeSlices() {
		int constructionTime = construction.getConstructionTime();
		float constructionSkills = unit.getConstructionSkills();
		return calculateTotalTimeSlices(constructionTime, constructionSkills);
	}
}
