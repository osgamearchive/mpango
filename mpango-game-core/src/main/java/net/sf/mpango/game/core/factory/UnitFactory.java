/**
 * 
 */
package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.builder.CityBuilder;
import net.sf.mpango.game.core.dto.UnitDTO;
import net.sf.mpango.game.core.entity.Unit;
import sun.tools.tree.FloatExpression;

import javax.mail.search.IntegerComparisonTerm;


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
            Unit unit = new Unit();
            unit.setCity(CityFactory.instance().create(dto.getCity()));
            unit.setCollectionSkills((Float)dto.getCollectionSkills());
            unit.setConstructionSkills((Float)dto.getConstructionSkills());
            unit.setHitPoints(dto.getHitPoints());
            unit.setIdentifier(dto.getId());
            unit.setShield(ShieldFactory.instance().create(dto.getShield()));
            unit.setTechnologies(TechnologyFactory.instance().createList(dto.getTechnologies()));
            unit.setWeapon(WeaponFactory.instance().create(dto.getWeapon()));
            
            
		//TODO: implementation

		
		return unit;
	}
	

}
