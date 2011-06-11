package net.sourceforge.mpango.builder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.builder.PositionBuilder;
import net.sourceforge.mpango.dto.PositionDTO;
import net.sourceforge.mpango.entity.Position;

import org.junit.Test;

public class PositionBuilderTest {

	@Test
	public void testBuildPosition() {
		Position position = new Position(2, 3);
		PositionDTO dto = PositionBuilder.instance().build(position);
		
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
		
		List<PositionDTO> dtoList = PositionBuilder.instance().buildList(positionList);
		
		assertNotNull(dtoList);
		assertEquals(dtoList.size(), 4);
	}

}
