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
		CellDTO dto = CellBuilder.instance().build(cell);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId().longValue(), 1L);
		Assert.assertEquals(dto.getConstructions().size(), 1);
		Assert.assertEquals(dto.getAttackBonus(), new Float(cell.getAttackBonus()));
		Assert.assertEquals(dto.getColumn(), new Integer(cell.getColumn()));
		Assert.assertEquals(dto.getDefenseBonus(), new Float(cell.getDefenseBonus()));
		Assert.assertEquals(dto.getRow(), new Integer(cell.getRow()));
		
	}

}
