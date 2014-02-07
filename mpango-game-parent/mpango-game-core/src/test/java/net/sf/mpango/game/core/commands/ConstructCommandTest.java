package net.sf.mpango.game.core.commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.entity.Construction;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.ConstructionAlreadyInPlaceException;
import net.sf.mpango.game.core.exception.ConstructionImpossibleException;
import net.sf.mpango.game.core.exception.MPangoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConstructCommandTest {

	private static final float TEST_UNIT_CONSTRUCTION_SKILLS = 0.5f;
	private static final int TEST_CONSTRUCTION_TIME_SLICES = 10;
	private static final long TEST_MILLES_SLICE = 1;

    @Mock
	private Unit unit;
    @Mock
	private Construction construction;
    @Mock
	private Cell cell;
	private ConstructCommand testing;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
		testing = new ConstructCommand(executorService, unit, construction, cell);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testEvaluateFailingExecution() {
        when(cell.containsConstruction(construction)).thenReturn(true);
		try {
			testing.evaluateExecution();
			fail("whened Exception not raised");
		} catch (ConstructionAlreadyInPlaceException whened) {
			verify(cell).containsConstruction(construction);
		} catch (CommandException e) {
			fail("Wrong Exception Type");
		}
	}
	
	@Test
	public void testEvaluateConstructNewCity() throws CommandException {
        when(cell.containsConstruction(construction)).thenReturn(false);
		when(unit.getCity()).thenReturn(new City());
		when(construction.getType()).thenReturn(ConstructionType.CITY);
        
		testing.evaluateExecution();

        verify(construction).getType();
        verify(unit).getCity();
        verify(cell).containsConstruction(construction);
	}
	
	@Test
	public void testEvaluateConstructCity() throws CommandException {
		when(cell.containsConstruction(construction)).thenReturn(false);
		when(unit.getCity()).thenReturn(null);
		when(construction.getType()).thenReturn(ConstructionType.CITY);
		testing.evaluateExecution();
		verify(construction).getType();
		verify(unit).getCity();
		verify(cell).containsConstruction(construction);
	}
	
	@Test(expected = ConstructionImpossibleException.class)
	public void testEvaluateConstructNotCityWithoutCityExecution() throws CommandException {
		when(unit.getCity()).thenReturn(null);
		when(construction.getType()).thenReturn(ConstructionType.OTHER);
		testing.evaluateExecution();
	}
	
	@Test
	public void testCommand () throws MPangoException, InterruptedException {
		when(construction.getType()).thenReturn(ConstructionType.OTHER);
        when(construction.getConstructionTime()).thenReturn(1);
        when(unit.getConstructionSkills()).thenReturn(1f);

        final CommandEvent<ConstructCommand> resultingEvent = testing.call();

        assertThat(resultingEvent.getCommand(), is(equalTo(testing)));
		verify(cell).addConstruction(construction);
        verify(construction).getConstructionTime();
        verify(unit).improveConstructionSkills(ConstructCommand.SKILLS_UPGRADE);
        verify(unit).getConstructionSkills();
	}
	
	@Test
	public void testCalculateTimeSlices() {
		when(construction.getConstructionTime()).thenReturn(TEST_CONSTRUCTION_TIME_SLICES);
		when(unit.getConstructionSkills()).thenReturn(TEST_UNIT_CONSTRUCTION_SKILLS);
		assertEquals("The time slices to use on the construction must be the whened", 
				testing.calculateTotalTimeMillis(TEST_MILLES_SLICE),
				(long) (TEST_MILLES_SLICE * (TEST_CONSTRUCTION_TIME_SLICES * TEST_UNIT_CONSTRUCTION_SKILLS) + (TEST_CONSTRUCTION_TIME_SLICES * ConstructCommand.MINIMUM_FACTOR)));
		verify(unit).getConstructionSkills();
		verify(construction).getConstructionTime();
	}
}
