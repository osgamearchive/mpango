package net.sourceforge.mpango.builder;

import junit.framework.Assert;
import net.sourceforge.mpango.builder.CellBuilder;
import net.sourceforge.mpango.builder.GameBoardBuilder;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import net.sourceforge.mpango.TestUtils;

import org.junit.Test;

public class BoardBuilderTest {

	@Test
	public void testGameBoardFactory() {
		GameBoard board = TestUtils.getGameBoard(1L);
		GameBoardDTO dto = GameBoardBuilder.instance().build(board);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId().longValue(), 1L);
		Assert.assertNotNull(dto.getCells());

		for (int i = 0; i < dto.getRowSize(); i++) {
			for (int j = 0; j < dto.getColSize(); j++) {
				Assert.assertNotNull(dto.getCells()[i][j]);
			}
		}
	}

	@Test
	public void testCellBuilder() {
		Cell cell = TestUtils.getCell(1L);
		CellDTO dto = CellBuilder.instance().build(cell);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId().longValue(), 1L);
		Assert.assertEquals(dto.getConstructions().size(), 1);
	}
}
