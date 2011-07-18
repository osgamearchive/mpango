package net.sf.mpango.game.core.factory;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sf.mpango.game.core.TestUtils;
import net.sf.mpango.game.core.dto.CellDTO;
import net.sf.mpango.game.core.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.factory.CellFactory;
import net.sf.mpango.game.core.factory.GameBoardFactory;

import org.junit.Test;

public class BoardFactoryTest extends TestCase {

	@Test
	public void testGameBoardFactory() {
		GameBoardDTO dto = TestUtils.getGameBoardDTO(1L);
		GameBoard board = GameBoardFactory.instance().create(dto);
		Assert.assertNotNull(board);
		Assert.assertEquals(board.getIdentifier().longValue(), 1L);
		Assert.assertNotNull(board.getCells());

		for (int i = 0; i < board.getRowSize(); i++) {
			for (int j = 0; j < board.getColSize(); j++) {
				Assert.assertNotNull(board.getCell(i, j));
			}
		}
	}

	@Test
	public void testCellFactory() {
		CellDTO dto = TestUtils.getCellDTO(1L);
		Cell cell = CellFactory.instance().create(dto);
		Assert.assertNotNull(cell);
		Assert.assertEquals(cell.getIdentifier().longValue(), 1L);
		Assert.assertEquals(cell.getConstructions().size(), 1);
	}

}
