package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.TechnologyDTO;
import net.sourceforge.mpango.entity.Technology;

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
