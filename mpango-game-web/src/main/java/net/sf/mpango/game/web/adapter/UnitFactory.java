/**
 * 
 */
package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.web.dto.UnitDTO;
import net.sf.mpango.game.core.entity.Unit;


/**
 * @author aplause
 *
 */
public class UnitFactory extends BaseFactory<UnitDTO, Unit>{

	
	private UnitFactory() {
		super();
	}

	public static UnitFactory instance() {
		return new UnitFactory();
	}

	@Override
	public Unit create(UnitDTO dto) {
		if (dto == null) {
			return null;
		}
        Unit unit = new Unit();
        unit.setCity(CityAdapter.instance().fromDTO(dto.getCity()));
        unit.setCollectionSkills((Float)dto.getCollectionSkills());
        unit.setConstructionSkills((Float)dto.getConstructionSkills());
        unit.setHitPoints(dto.getHitPoints());
        unit.setIdentifier(dto.getId());
        unit.setShield(ShieldFactory.instance().create(dto.getShield()));
        unit.setTechnologies(TechnologyFactory.instance().createList(dto.getTechnologies()));
        unit.setWeapon(WeaponFactory.instance().create(dto.getWeapon()));		
		return unit;
	}
	

}
