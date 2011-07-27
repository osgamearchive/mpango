package net.sf.mpango.game.web.adapter;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.web.adapter.GameBoardAdapter;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import net.sf.mpango.game.web.TestUtils;


public class GameBoardAdapterTest {

	@Test
	public void testBuildGameBoard() {
		GameBoard gb = TestUtils.getGameBoard(1L);		
		GameBoardDTO dto = GameBoardAdapter.instance().toDTO(gb);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), gb.getIdentifier());
		assertEquals(dto.getColSize(), (Integer)gb.getColSize());
		assertEquals(dto.getRowSize(), (Integer)gb.getRowSize());		
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
