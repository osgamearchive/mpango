package net.sourceforge.mpango.builder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.dto.TechnologyDTO;
import net.sourceforge.mpango.entity.Technology;
import net.sourceforge.mpango.TestUtils;

import org.junit.Test;

public class TechnologyBuilderTest {

	@Test
	public void testBuildTechnology() {
		Technology tech = TestUtils.getTechnology(1L, 12);
		TechnologyDTO dto = TechnologyBuilder.instance().build(tech);
		assertNotNull(dto);
		assertEquals(tech.getIdentifier(), dto.getId());
		assertEquals(tech.getRequiredTechnologies(), dto.getRequiredTechnologies());
		assertEquals(tech.getTechnologyCost(), dto.getTechnologyCost());		
	}

	@Test
	public void testBuildList() {
		List<Technology> techList = new ArrayList<Technology>();
		techList.add(TestUtils.getTechnology(1L, 10));
		techList.add(TestUtils.getTechnology(2L, 12));
		techList.add(TestUtils.getTechnology(3L, 5));
		techList.add(TestUtils.getTechnology(4L, 17));
		techList.add(TestUtils.getTechnology(5L, 25));
		
		List<TechnologyDTO> dtoList = TechnologyBuilder.instance().buildList(techList);
		assertNotNull(dtoList);
		assertEquals(techList.size(), dtoList.size());
		assertEquals(techList.get(2).getIdentifier(), dtoList.get(2).getId());
		assertEquals(techList.get(4).getTechnologyCost(), dtoList.get(4).getTechnologyCost());
	}

}
