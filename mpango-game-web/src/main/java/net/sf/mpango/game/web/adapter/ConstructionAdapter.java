package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.entity.Construction;
import net.sf.mpango.game.web.dto.CityDTO;
import net.sf.mpango.game.web.dto.ConstructionDTO;

/**
 * 
 * @author etux
 *
 */
public class ConstructionAdapter extends BaseAdapter<Construction, ConstructionDTO> {

	private ConstructionAdapter() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link ConstructionAdapter}
	 */
	public static ConstructionAdapter instance() {
		return new ConstructionAdapter();

	}

	@Override
	public Construction fromDTO(ConstructionDTO dto) {
		if (dto == null) {
			return null;
		}
		switch (dto.getType()) {
			case CITY:
				return CityAdapter.instance().fromDTO((CityDTO) dto);
	
			default:
				return null;
		}
	}

	@Override
	public ConstructionDTO toDTO(Construction construction) {
		if (construction == null) {
			return null;
		}
		switch (construction.getType()) {
			case CITY:
				return CityAdapter.instance().toDTO((City) construction);
			default:
				return null;
		}
	}
}