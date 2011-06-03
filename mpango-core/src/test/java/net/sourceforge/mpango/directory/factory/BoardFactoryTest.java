package net.sourceforge.mpango.directory.factory;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.mpango.TestUtils;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;

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
				Assert.assertEquals(board.getCell(i, j).getIdentifier()
						.intValue(), i * j);
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
