package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.ShieldDTO;
import net.sf.mpango.game.core.entity.Shield;

public class ShieldAdapter extends BaseAdapter<Shield, ShieldDTO>{
	
	private ShieldAdapter() {
		super();
	}
	
	public static ShieldAdapter instance() {
		return new ShieldAdapter();
	}
	
	@Override
	public ShieldDTO toDTO(Shield shield) {
		if (shield == null) {
			return null;
		}
		ShieldDTO dto = new ShieldDTO();
		dto.setId(shield.getId());
		dto.setHitPoints(shield.getRemainingHitPoints());
		dto.setMaximumHitPoints(shield.getMaximumHitPoints());
		return dto;
	}

	@Override
	public Shield fromDTO(ShieldDTO dto) {
		if (dto == null) {
			return null;
		}
        Shield shield = new Shield(dto.getMaximumHitPoints());
        return shield;
	}
	

}
