package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.web.TestUtils;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GameBoardAdapterTest {

	@Test
	public void testBuildGameBoard() {
		GameBoard gb = TestUtils.getGameBoard(1L);		
		GameBoardDTO dto = GameBoardAdapter.instance().toDTO(gb);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), gb.getId());
		assertEquals(dto.getColSize(), (Integer)gb.getConfiguration().getColNumber());
		assertEquals(dto.getRowSize(), (Integer)gb.getConfiguration().getRowNumber());
	}
	
	@Test
	public void testBuildGameBoardFor() {
		GameBoard gb = TestUtils.getGameBoard(1L);
		GameBoardDTO dto = GameBoardAdapter.instance().toDTO(gb);
		assertNotNull(dto.getCells());
		if(dto.getCells().length == 0 || dto.getCells()[0].length == 0) {
			assertEquals(dto.getCells().length + dto.getCells()[0].length, gb.getCells());
		} else {
			assertEquals(dto.getCells().length * dto.getCells()[0].length , gb.getCells().size());
		}
		
				
	}

	@Test
	public void testBuildList() {
		List<GameBoard> gbList = new ArrayList<GameBoard>();
		gbList.add(TestUtils.getGameBoard(1L));
		gbList.add(TestUtils.getGameBoard(2L));
		gbList.add(TestUtils.getGameBoard(3L));
		gbList.add(TestUtils.getGameBoard(4L));
		gbList.add(TestUtils.getGameBoard(5L));
		List<GameBoardDTO> dtoList = GameBoardAdapter.instance().toDTOList(gbList);
		assertNotNull(dtoList);
		assertEquals(dtoList.size(), 5);		
	}

}
