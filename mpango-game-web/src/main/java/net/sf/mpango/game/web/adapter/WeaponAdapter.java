package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.WeaponDTO;
import net.sf.mpango.game.core.entity.Weapon;

public class WeaponAdapter extends BaseAdapter<Weapon, WeaponDTO>{
	
	private WeaponAdapter() {
		super();
	}
	
	public static WeaponAdapter instance() {
		return new WeaponAdapter();
	}

	@Override
	public WeaponDTO toDTO (Weapon weapon) {
		if (weapon == null) {
			return null;
		}
		WeaponDTO dto = new WeaponDTO();
		dto.setId(weapon.getIdentifier());
		dto.setAttackBonus(weapon.getAttackBonus());		
		return dto;
	}

	@Override
	public Weapon fromDTO(WeaponDTO dto) {
		if (dto == null) {
			return null;
		}
        return new Weapon(dto.getAttackBonus());     
	}

}
