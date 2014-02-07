package net.sf.mpango.game.core.commands;

import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Construction;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.ConstructionAlreadyInPlaceException;
import net.sf.mpango.game.core.exception.ConstructionImpossibleException;

/**
 * Command that represents the construction commands.
 * 
 * @author edvera
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

    private final Construction construction;
    private final Cell cell;
	/**
	 * Command builder with all details needed in order to execute the command.
	 * @param unit Unit that is executing the commands.
	 * @param construction Construction to be constructed.
	 * @param cell Cell where the construction will be constructed.
	 * @throws CommandException in case it is not possible to construct.
	 */
	public ConstructCommand(final Unit unit, final Construction construction, final Cell cell) {
		this(null, unit, construction, cell);
	}

	/**
	 * Construction builder in order to separate the Timer from the unit involved in the construction.
	 * Thought for unit testing but could be interesting if instead of unit based timers, we want to use player based timers.
	 * @param executorService
	 * @param unit
	 * @param construction
	 * @param cell
	 */
	public ConstructCommand(final ExecutorService executorService, final Unit unit, final Construction construction, final Cell cell) {
        //In this case, the interested parties in receiving events are the cell and the unit.
		super(executorService, unit, cell);
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
	public CommandEvent execute() {;
		try {
			this.cell.addConstruction(construction);
			this.unit.improveConstructionSkills(SKILLS_UPGRADE);
            LOGGER.log(Level.INFO, "constructed " + construction.getClass());
		} catch (final ConstructionAlreadyInPlaceException e) {
            LOGGER.log(Level.FINEST, LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.CONSTRUCTION_ALREADY_IN_PLACE, construction.getId()));
		}
		return new CommandExecutedEvent(this);
	}

	/**
	 * Method that evaluates the execution of the command prior to it actually happening.
	 * @throws CommandException in case the command can not be executed.
	 */
	@Override
	public void evaluateExecution() throws CommandException {
		if (cell.containsConstruction(construction)) throw new ConstructionAlreadyInPlaceException(construction);
		if ((unit.getCity() == null) & (construction.getType() != ConstructionType.CITY) )throw new ConstructionImpossibleException(construction);
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

	public Unit getUnit() {
		return unit;
	}

	public Construction getConstruction() {
		return construction;
	}

	public Cell getCell() {
		return cell;
	}

    private static final Logger LOGGER = Logger.getLogger(ConstructCommand.class.getName());
}
