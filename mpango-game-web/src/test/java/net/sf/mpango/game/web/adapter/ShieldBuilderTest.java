package net.sf.mpango.game.web.adapter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.Shield;
import net.sf.mpango.game.web.adapter.ShieldBuilder;
import net.sf.mpango.game.web.dto.ShieldDTO;

import org.junit.Test;

public class ShieldBuilderTest {

	@Test
	public void testBuildShield() {
		Shield shield = new Shield(1F);
		ShieldDTO dto = ShieldBuilder.instance().build(shield);
		assertNotNull(dto);
		assertEquals(shield.getIdentifier(), dto.getId());
		assertEquals(shield.getMaximumHitPoints(), dto.getMaximumHitPoints());
		assertEquals(shield.getRemainingHitPoints(), dto.getHitPoints());				
	}

	@Test
	public void testBuildList() {
		List<Shield> shieldList = new ArrayList<Shield>();
		shieldList.add(new Shield(1F));
		shieldList.add(new Shield(2F));
		shieldList.add(new Shield(3F));
		shieldList.add(new Shield(4F));
		shieldList.add(new Shield(5F));
		List<ShieldDTO> dtoList = ShieldBuilder.instance().buildList(shieldList);
		assertNotNull(dtoList);
		assertEquals(5, dtoList.size());
	}

}
