package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.ConstructionDTO;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.entity.Construction;

public class ConstructionBuilder extends
		BaseBuilder<Construction, ConstructionDTO> {

	private ConstructionBuilder() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link ConstructionBuilder}
	 */
	public static ConstructionBuilder instance() {
		return new ConstructionBuilder();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.
	 * Object)
	 */
	public ConstructionDTO build(Construction construction) {

		switch (construction.getType()) {
			case CITY:
				return CityAdapter.instance().toDTO((City) construction);
			default:
				return null;
		}
	}

	/**
	 * 
	 * @param cityList
	 * @return
	 */
	public List<ConstructionDTO> buildList(List<Construction> constructionList) {
		List<ConstructionDTO> dtoList = new ArrayList<ConstructionDTO>();
		for (Construction c : constructionList) {
			dtoList.add(build(c));
		}
		return dtoList;
	}
}
