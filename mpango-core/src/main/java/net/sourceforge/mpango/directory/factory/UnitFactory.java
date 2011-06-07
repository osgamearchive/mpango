/**
 * 
 */
package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.UnitDTO;
import net.sourceforge.mpango.entity.Unit;

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
		
		//TODO: implementation
		
		return unit;
	}
	

}
