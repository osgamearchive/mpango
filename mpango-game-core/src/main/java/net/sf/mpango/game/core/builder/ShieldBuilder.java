package net.sf.mpango.game.core.builder;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.core.dto.ShieldDTO;
import net.sf.mpango.game.core.entity.Shield;

public class ShieldBuilder extends BaseBuilder<Shield, ShieldDTO>{
	
	private ShieldBuilder() {
		super();
	}
	
	public static ShieldBuilder instance() {
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
