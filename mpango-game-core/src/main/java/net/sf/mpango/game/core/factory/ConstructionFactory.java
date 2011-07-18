package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.dto.CityDTO;
import net.sf.mpango.game.core.dto.ConstructionDTO;
import net.sf.mpango.game.core.entity.Construction;

public class ConstructionFactory extends
		BaseFactory<ConstructionDTO, Construction> {

	private ConstructionFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return
	 */
	public static ConstructionFactory instance() {
		return new ConstructionFactory();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge
	 * .mpango.dto.BaseDTO)
	 */
	public Construction create(ConstructionDTO dto) {

		switch (dto.getType()) {
		case CITY:
			return CityFactory.instance().create((CityDTO) dto);

		default:
			return null;
		}
	}

}
