package net.sourceforge.mpango.builder;

import net.sourceforge.mpango.dto.ShieldDTO;
import net.sourceforge.mpango.entity.Shield;

public class ShieldBuilder extends BaseBuilder<Shield, ShieldDTO>{
	
	private ShieldBuilder() {
		super();
	}
	
	public ShieldBuilder instance() {
		return new ShieldBuilder();
	}
	
	@Override
	public ShieldDTO build(Shield shield) {		
		ShieldDTO dto = new ShieldDTO();
		dto.setId(shield.getIdentifier());
		dto.setHitPoints(shield.getRemainingHitPoints());
		dto.setMaximumHitPoints(shield.getMaximumHitPoints());
		return dto;
	}

}
