package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.Weapon;
import net.sf.mpango.game.web.dto.WeaponDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class WeaponAdapterTest {
	
	@Test
	public void TestBuildWeapon() {
		Weapon weapon = new Weapon(1F);
		WeaponDTO dto = WeaponAdapter.instance().toDTO(weapon);
		
		assertNotNull(dto);
		assertEquals(weapon.getId(), dto.getId());
		assertEquals(weapon.getAttackBonus(), dto.getAttackBonus(), 0.001);
	}
	
	@Test
	public void TestBuildList() {
		List<Weapon> weaponList = new ArrayList<Weapon>();
		weaponList.add(new Weapon(1F));
		weaponList.add(new Weapon(2F));
		weaponList.add(new Weapon(3F));
		weaponList.add(new Weapon(4F));
		weaponList.add(new Weapon(5F));
		List<WeaponDTO> dtoList = WeaponAdapter.instance().toDTOList(weaponList);
		assertNotNull(dtoList);
		assertEquals(5, dtoList.size());
	}

}
