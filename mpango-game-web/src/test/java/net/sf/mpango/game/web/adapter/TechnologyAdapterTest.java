package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import net.sf.mpango.game.core.entity.Technology;
import net.sf.mpango.game.web.adapter.TechnologyAdapter;
import net.sf.mpango.game.web.dto.TechnologyDTO;
import net.sf.mpango.game.web.TestUtils;


public class TechnologyAdapterTest {

	@Test
	public void testBuildTechnology() {
		Technology tech = TestUtils.getTechnology(1L, 12);
		TechnologyDTO dto = TechnologyAdapter.instance().toDTO(tech);
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
		
		List<TechnologyDTO> dtoList = TechnologyAdapter.instance().toDTOList(techList);
		assertNotNull(dtoList);
		assertEquals(techList.size(), dtoList.size());
		assertEquals(techList.get(2).getIdentifier(), dtoList.get(2).getId());
		assertEquals(techList.get(4).getTechnologyCost(), dtoList.get(4).getTechnologyCost());
	}

}
