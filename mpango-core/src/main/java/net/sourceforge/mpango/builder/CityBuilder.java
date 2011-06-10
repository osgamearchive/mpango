package net.sourceforge.mpango.builder;

import net.sourceforge.mpango.dto.CityDTO;
import net.sourceforge.mpango.entity.City;

public class CityBuilder extends BaseBuilder<City, CityDTO> {

	private CityBuilder() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link CityBuilder}
	 */
	public static CityBuilder instance() {
		return new CityBuilder();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.
	 * Object)
	 */
	public CityDTO build(City city) {

		if (null == city) {
			return null;
		}

		CityDTO dto = new CityDTO();
		dto.setId(city.getIdentifier());
		dto.setAttackBonus(city.getAttackBonus());
		dto.setConstructionTime(city.getConstructionTime());
		dto.setDefenseBonus(city.getDefenseBonus());
		dto.setHitPoints(city.getHitPoints());
		dto.setMaximumHitPoints(city.getMaximumHitPoints());

		return dto;
	}

}
