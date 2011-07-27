package net.sf.mpango.game.web.adapter;

import junit.framework.Assert;
import junit.framework.TestCase;

import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;

import net.sf.mpango.game.web.adapter.CellAdapter;
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.web.dto.GameBoardDTO;

import net.sf.mpango.game.web.TestUtils;

import org.junit.Test;

public class BoardFactoryTest extends TestCase {

	@Test
	public void testGameBoardFactory() {
		GameBoardDTO dto = TestUtils.getGameBoardDTO(1L);
		try {
			GameBoardAdapter.instance().fromDTO(dto);
			fail("A runtime exception should have been thrown as the method is not supported on this version");
		} catch(RuntimeException expected) {
			//Factory method not supported at the moment
		}
	}

	// @Test Possible for future use in case of an interface board editor (i.e)
	public void futureGameBoardFactory() {
		GameBoardDTO dto = TestUtils.getGameBoardDTO(1L);
		GameBoard board = GameBoardAdapter.instance().fromDTO(dto);
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
		Cell cell = CellAdapter.instance().fromDTO(dto);
		Assert.assertNotNull(cell);
		Assert.assertEquals(cell.getIdentifier().longValue(), 1L);
		Assert.assertEquals(cell.getConstructions().size(), 1);
	}

}
