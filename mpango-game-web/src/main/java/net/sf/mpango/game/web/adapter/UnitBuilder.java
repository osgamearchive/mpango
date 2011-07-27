package net.sf.mpango.game.web.adapter;


import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.UnitDTO;
import net.sf.mpango.game.core.entity.Unit;



public class UnitBuilder extends BaseBuilder<Unit, UnitDTO> {

	private UnitBuilder() {
		super();
	}
	
	public static UnitBuilder instance() {
		return new UnitBuilder();
	}

	@Override
	public UnitDTO build(Unit unit) {
		if (unit == null) {
			return null;
		}
		UnitDTO dto = new UnitDTO();
		dto.setAttackPoints(unit.getEffectiveAttackPoints());
		dto.setHitPoints(unit.getHitPoints());		
		dto.setShield(ShieldBuilder.instance().build(unit.getShield()));		
		dto.setWeapon(WeaponBuilder.instance().build(unit.getWeapon()));
		dto.setTechnologies(TechnologyBuilder.instance().buildList(unit.getTechnologies()));
		dto.setConstructionSkills(unit.getConstructionSkills());
		dto.setCollectionSkills(unit.getCollectionSkills());
		dto.setTimer(unit.getTimer());
		dto.setCity(CityAdapter.instance().toDTO(unit.getCity()));		
		return dto;
	}

}
