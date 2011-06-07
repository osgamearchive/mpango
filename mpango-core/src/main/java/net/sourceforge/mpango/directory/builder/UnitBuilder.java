package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.UnitDTO;
import net.sourceforge.mpango.entity.Unit;

public class UnitBuilder extends BaseBuilder<Unit, UnitDTO> {

	private UnitBuilder() {
		super();
	}
	
	public static UnitBuilder instance() {
		return new UnitBuilder();
	}

	@Override
	public UnitDTO build(Unit unit) {
		UnitDTO dto = new UnitDTO();
		//TODO: implementation
		return dto;
	}

}
