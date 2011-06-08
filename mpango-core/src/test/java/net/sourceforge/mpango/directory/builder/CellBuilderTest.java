package net.sourceforge.mpango.directory.builder;

import static org.junit.Assert.*;

import net.sourceforge.mpango.TestUtils;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.entity.Cell;

import org.junit.Assert;
import org.junit.Test;

public class CellBuilderTest {

	@Test
	public void testBuildCell() {
		Cell cell = TestUtils.getCell(1L);
		CellDTO dto = new CellDTO();
		dto.setId(cell.getIdentifier());
		dto.setAttackBonus(cell.getAttackBonus());
		dto.setColumn(cell.getColumn());
		dto.setConstructions(ConstructionBuilder.instance().buildList(
				cell.getConstructions()));
		dto.setDefenseBonus(cell.getDefenseBonus());
		dto.setRow(cell.getRow());
		Assert.assertNotNull(dto);
	}

}
