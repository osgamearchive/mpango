package net.sf.mpango.game.web.adapter;

import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.CityDTO;

/**
 * 
 * @author etux
 *
 */
public class CityAdapter extends BaseAdapter<City, CityDTO> {

	private CityAdapter() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link CityAdapter}
	 */
	public static CityAdapter instance() {
		return new CityAdapter();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.
	 * Object)
	 */
	@Override
	public CityDTO toDTO(City city) {
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

	@Override
	public City fromDTO(CityDTO dto) {
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
