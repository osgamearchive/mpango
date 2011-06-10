package net.sourceforge.mpango.builder;


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
		
		dto.setAttackPoints(unit.getEffectiveAttackPoints());
		dto.setHitPoints(unit.getHitPoints());		
		dto.setShield(ShieldBuilder.instance().build(unit.getShield()));		
		dto.setWeapon(WeaponBuilder.instance().build(unit.getWeapon()));
		dto.setTechnologies(TechnologyBuilder.instance().buildList(unit.getTechnologies()));
		dto.setConstructionSkills(unit.getConstructionSkills());
		dto.setCollectionSkills(unit.getCollectionSkills());
		dto.setTimer(unit.getTimer());
		dto.setCity(CityBuilder.instance().build(unit.getCity()));		
		return dto;
	}

}
