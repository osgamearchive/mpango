package net.sourceforge.mpango.directory.builder;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.dto.ConstructionDTO;
import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.entity.Construction;

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
			return CityBuilder.instance().build((City) construction);

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
