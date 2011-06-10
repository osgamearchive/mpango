package net.sourceforge.mpango.builder;


import net.sourceforge.mpango.dto.ShieldDTO;
import net.sourceforge.mpango.dto.UnitDTO;
import net.sourceforge.mpango.dto.WeaponDTO;

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
		//maybe better do builders
		WeaponDTO weapon = new WeaponDTO();
		weapon.setAttackBonus(unit.getWeapon().getAttackBonus());
		weapon.setId(unit.getIdentifier());
		ShieldDTO shield = new ShieldDTO();
		shield.setHitPoints(unit.getShield().getRemainingHitPoints());
		shield.setMaximumHitPoints(unit.getShield().getMaximumHitPoints());
		
		dto.setAttackPoints(unit.getAttackPoints());
		dto.setHitPoints(unit.getHitPoints());		
		dto.setShield(shield);		
		dto.setWeapon(weapon);
		dto.setTechnologies(TechnologyBuilder.instance().buildList(unit.getTechnologies()));
		dto.setConstructionSkills(unit.getConstructionSkills());
		dto.setCollectionSkills(unit.getCollectionSkills());
		dto.setTimer(unit.getTimer());
		dto.setCity(CityBuilder.instance().build(unit.getCity()));
		dto.setCommands(CommandBuilder.instance().buildList(unit.getCommands()));
		return dto;
	}

}
