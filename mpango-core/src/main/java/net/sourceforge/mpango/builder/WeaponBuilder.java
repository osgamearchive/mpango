package net.sourceforge.mpango.builder;

import net.sourceforge.mpango.dto.WeaponDTO;
import net.sourceforge.mpango.entity.Weapon;

public class WeaponBuilder extends BaseBuilder<Weapon, WeaponDTO>{
	
	private WeaponBuilder() {
		super();
	}
	
	public static WeaponBuilder instance() {
		return new WeaponBuilder();
	}

	@Override
	public WeaponDTO build(Weapon weapon) {
		WeaponDTO dto = new WeaponDTO();
		dto.setId(weapon.getIdentifier());
		dto.setAttackBonus(weapon.getAttackBonus());		
		return dto;
	}

}
