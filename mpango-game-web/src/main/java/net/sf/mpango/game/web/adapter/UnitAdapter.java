package net.sf.mpango.game.web.adapter;


import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.UnitDTO;
import net.sf.mpango.game.core.entity.Unit;

/**
 * 
 * @author etux
 *
 */
public class UnitAdapter extends BaseAdapter <Unit, UnitDTO> {

	private UnitAdapter() {
		super();
	}
	
	public static UnitAdapter instance() {
		return new UnitAdapter();
	}

	@Override
	public UnitDTO toDTO(Unit unit) {
		if (unit == null) {
			return null;
		}
		UnitDTO dto = new UnitDTO();
		dto.setAttackPoints(unit.getEffectiveAttackPoints());
		dto.setHitPoints(unit.getHitPoints());		
		dto.setShield(ShieldAdapter.instance().toDTO(unit.getShield()));		
		dto.setWeapon(WeaponAdapter.instance().toDTO(unit.getWeapon()));
		dto.setTechnologies(TechnologyAdapter.instance().toDTOList(unit.getTechnologies()));
		dto.setConstructionSkills(unit.getConstructionSkills());
		dto.setCollectionSkills(unit.getCollectionSkills());
		dto.setTimer(unit.getTimer());
		dto.setCity(CityAdapter.instance().toDTO(unit.getCity()));		
		return dto;
	}

	@Override
	public Unit fromDTO(UnitDTO dto) {
		if (dto == null) {
			return null;
		}
        Unit unit = new Unit();
        unit.setCity(CityAdapter.instance().fromDTO(dto.getCity()));
        unit.setCollectionSkills((Float)dto.getCollectionSkills());
        unit.setConstructionSkills((Float)dto.getConstructionSkills());
        unit.setHitPoints(dto.getHitPoints());
        unit.setId(dto.getId());
        unit.setShield(ShieldAdapter.instance().fromDTO(dto.getShield()));
        unit.setTechnologies(TechnologyAdapter.instance().fromDTOList(dto.getTechnologies()));
        unit.setWeapon(WeaponAdapter.instance().fromDTO(dto.getWeapon()));		
		return unit;
	}

}
