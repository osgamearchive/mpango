package net.sf.mpango.game.web.adapter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.Position;
import net.sf.mpango.game.web.adapter.PositionAdapter;
import net.sf.mpango.game.web.dto.PositionDTO;

import org.junit.Test;

public class PositionAdapterTest {

	@Test
	public void testBuildPosition() {
		Position position = new Position(2, 3);
		PositionDTO dto = PositionAdapter.instance().toDTO(position);
		assertNotNull(dto);
		assertEquals(dto.getColNumber(), position.getColNumber());
		assertEquals(dto.getRowNumber(), position.getRowNumber());
		}

	@Test
	public void testBuildList() {
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(new Position(1, 1));
		positionList.add(new Position(1, 2));
		positionList.add(new Position(2, 1));
		positionList.add(new Position(2, 2));
		List<PositionDTO> dtoList = PositionAdapter.instance().toDTOList(positionList);
		assertNotNull(dtoList);
		assertEquals(dtoList.size(), 4);
	}

}
