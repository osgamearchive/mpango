package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.WeaponDTO;
import net.sf.mpango.game.core.entity.Weapon;

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
