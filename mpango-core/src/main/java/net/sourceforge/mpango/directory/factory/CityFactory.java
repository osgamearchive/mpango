package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.CityDTO;
import net.sourceforge.mpango.entity.City;

public class CityFactory extends BaseFactory<CityDTO, City> {

	private CityFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link CityFactory}
	 */
	public static CityFactory instance() {
		return new CityFactory();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge
	 * .mpango.dto.BaseDTO)
	 */
	public City create(CityDTO dto) {

		if (null == dto) {
			return null;
		}

		City city = new City();
		city.setIdentifier(dto.getId());
		city.setAttackBonus(dto.getAttackBonus());
		city.setDefenseBonus(dto.getDefenseBonus());
		city.setHitPoints(dto.getHitPoints());
		city.setMaximumHitPoints(dto.getMaximumHitPoints());

		return city;
	}

}
