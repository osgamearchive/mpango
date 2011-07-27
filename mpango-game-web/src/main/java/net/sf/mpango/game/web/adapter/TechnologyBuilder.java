package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.TechnologyDTO;
import net.sf.mpango.game.core.entity.Technology;

public class TechnologyBuilder extends BaseBuilder<Technology, TechnologyDTO>{
	
	private TechnologyBuilder() {
		super();
	}
	
	public static TechnologyBuilder instance() {
		return new TechnologyBuilder();
	}
	
	@Override
	public TechnologyDTO build(Technology tech) {		
		TechnologyDTO t = new TechnologyDTO();
		t.setId(tech.getIdentifier());
		t.setRequiredTechnologies(TechnologyBuilder.instance()
				.buildList(tech.getRequiredTechnologies()));
		t.setTechnologyCost(tech.getTechnologyCost());
		return t;
	}

}
