package net.sourceforge.mpango.actions;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Construction;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.ConstructionAlreadyInPlaceException;
import net.sourceforge.mpango.exception.MPangoException;

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
	public void testCommand () throws MPangoException, InterruptedException {
		cell.addConstruction(construction);
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