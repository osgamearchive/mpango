package net.sf.mpango.game.web.adapter;

import junit.framework.Assert;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.web.TestUtils;
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import net.sf.mpango.game.web.adapter.CellAdapter;
import net.sf.mpango.game.web.adapter.GameBoardAdapter;

import org.junit.Test;

public class BoardBuilderTest {

	@Test
	public void testGameBoardFactory() {
		GameBoard board = TestUtils.getGameBoard(1L);
		GameBoardDTO dto = GameBoardAdapter.instance().toDTO(board);
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
		CellDTO dto = CellAdapter.instance().toDTO(cell);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId().longValue(), 1L);
		Assert.assertEquals(dto.getConstructions().size(), 1);
	}
}
