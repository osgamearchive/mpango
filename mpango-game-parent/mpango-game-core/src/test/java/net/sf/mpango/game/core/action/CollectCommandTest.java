package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.enums.Resources;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class CollectCommandTest {

	private static final long MILLIS_PER_SLICE = 100;
	private CollectCommand command;
	private Cell cell;
	private Unit unit;
	
	@Before
	public void setUp() {
		cell = EasyMock.createMock(Cell.class);
		unit = EasyMock.createMock(Unit.class);
		command = new CollectCommand(MILLIS_PER_SLICE, null, cell, unit);
	}
	
	@Test
	public void testRunExecute() {
		EasyMock.expect(cell.getCollectionPoints()).andReturn(10);
		EasyMock.expect(unit.getCollectionSkills()).andReturn(0.1f);
		unit.putResources(Resources.FOOD, 1);
		EasyMock.replay(cell);
		EasyMock.replay(unit);
		command.runExecute();
		EasyMock.verify(unit);
		EasyMock.verify(cell);
	}
}
