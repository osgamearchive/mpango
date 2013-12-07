package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.Shield;
import net.sf.mpango.game.web.dto.ShieldDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShieldAdapterTest {

	@Test
	public void testBuildShield() {
		Shield shield = new Shield(1F);
		ShieldDTO dto = ShieldAdapter.instance().toDTO(shield);
		assertNotNull(dto);
		assertEquals(shield.getId(), dto.getId());
		assertEquals(shield.getMaximumHitPoints(), dto.getMaximumHitPoints(), 0.001);
		assertEquals(shield.getRemainingHitPoints(), dto.getHitPoints(), 0.001);
	}

	@Test
	public void testBuildList() {
		List<Shield> shieldList = new ArrayList<Shield>();
		shieldList.add(new Shield(1F));
		shieldList.add(new Shield(2F));
		shieldList.add(new Shield(3F));
		shieldList.add(new Shield(4F));
		shieldList.add(new Shield(5F));
		List<ShieldDTO> dtoList = ShieldAdapter.instance().toDTOList(shieldList);
		assertNotNull(dtoList);
		assertEquals(5, dtoList.size());
	}

}
