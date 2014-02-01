package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.entity.Construction;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.ConstructionAlreadyInPlaceException;
import net.sf.mpango.game.core.exception.ConstructionImpossibleException;
import net.sf.mpango.game.core.exception.MPangoException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

public class ConstructCommandTest {

	private static final float TEST_UNIT_CONSTRUCTION_SKILLS = 0.5f;
	private static final int TEST_CONSTRUCTION_TIME_SLICES = 10;
	private static final long TEST_MILLES_SLICE = 1;
	
	private Unit unit;
	private Construction construction;
	private Cell cell;
	private ConstructCommand command;
	
	@Before
	public void setUp() {
		unit 			= createMock(Unit.class);
		construction 	= createMock(Construction.class);
		cell 			= createMock(Cell.class);
		command			= new ConstructCommand(null, unit, construction, cell);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testEvaluateFailingExecution() {
		expect(cell.containsConstruction(construction)).andReturn(true);
		try {
			replay(cell);
			command.evaluateExecution();
			fail("Expected Exception not raised");
		} catch (ConstructionAlreadyInPlaceException expected) {
			verify(cell);
		} catch (CommandException e) {
			fail("Wrong Exception Type");
		}
	}
	
	@Test
	public void testEvaluateConstructNewCity() throws CommandException {
		expect(cell.containsConstruction(construction)).andReturn(false);
		expect(unit.getCity()).andReturn(new City());
		expect(construction.getType()).andReturn(ConstructionType.CITY);
		replay(cell);
		replay(unit);
		replay(construction);
		command.evaluateExecution();
		verify(construction);
		verify(unit);
		verify(cell);
	}
	
	@Test
	public void testEvaluateConstructCity() throws CommandException {
		expect(cell.containsConstruction(construction)).andReturn(false);
		expect(unit.getCity()).andReturn(null);
		expect(construction.getType()).andReturn(ConstructionType.CITY);
		replay(cell);
		replay(unit);
		replay(construction);
		command.evaluateExecution();
		verify(construction);
		verify(unit);
		verify(cell);
	}
	
	@Test
	public void testEvaluateConstructNotCityWithoutCityExecution() {
		expect(unit.getCity()).andReturn(null);
		expect(construction.getType()).andReturn(ConstructionType.OTHER);
		try {
			replay(unit);
			replay(construction);
			command.evaluateExecution();
			fail("Expected exception not raised");
		} catch (ConstructionImpossibleException e) {
			verify(unit);
			verify(construction);
		} catch (CommandException e) {
			fail("Wrong exception type");
		}
	}
	
	@Test
	public void testCommand () throws MPangoException, InterruptedException {
		cell.addConstruction(construction);
		expect(construction.getType()).andReturn(ConstructionType.OTHER);
		unit.improveConstructionSkills(ConstructCommand.SKILLS_UPGRADE);
		replay(cell);
		replay(construction);
		replay(unit);
		command.runExecute();
		verify(cell);
		verify(construction);
		verify(unit);
	}
	
	@Test
	public void testCalculateTimeSlices() {
		expect(construction.getConstructionTime()).andReturn(TEST_CONSTRUCTION_TIME_SLICES);
		expect(unit.getConstructionSkills()).andReturn(TEST_UNIT_CONSTRUCTION_SKILLS);
		replay(construction);
		replay(unit);
		assertEquals("The time slices to use on the construction must be the expected", 
				command.calculateTotalTimeMillis(TEST_MILLES_SLICE), 
				(long) (TEST_MILLES_SLICE * (TEST_CONSTRUCTION_TIME_SLICES * TEST_UNIT_CONSTRUCTION_SKILLS) + (TEST_CONSTRUCTION_TIME_SLICES * ConstructCommand.MINIMUM_FACTOR)));
		verify(unit);
		verify(construction);
	}
}
