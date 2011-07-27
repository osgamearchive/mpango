package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.TechnologyDTO;
import net.sf.mpango.game.core.entity.Technology;

/**
 * 
 * @author etux
 *
 */
public class TechnologyAdapter extends BaseAdapter<Technology, TechnologyDTO>{
	
	private TechnologyAdapter() {
		super();
	}
	
	public static TechnologyAdapter instance() {
		return new TechnologyAdapter();
	}
	
	@Override
	public TechnologyDTO toDTO(Technology tech) {
		if (tech == null) {
			return null;
		}
		TechnologyDTO t = new TechnologyDTO();
		t.setId(tech.getIdentifier());
		t.setRequiredTechnologies(TechnologyAdapter.instance().toDTOList(tech.getRequiredTechnologies()));
		t.setTechnologyCost(tech.getTechnologyCost());
		return t;
	}

	@Override
	public Technology fromDTO(TechnologyDTO dto) {
		if (dto == null) {
			return null;
		}
        Technology tech = new Technology();
        tech.setIdentifier(dto.getId());
        tech.setRequiredTechnologies(TechnologyAdapter.instance().fromDTOList(dto.getRequiredTechnologies()));
        tech.setTechnologyCost(dto.getTechnologyCost());
        return tech;
	}

}
