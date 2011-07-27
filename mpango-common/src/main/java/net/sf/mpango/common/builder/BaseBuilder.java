package net.sf.mpango.common.builder;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.dto.BaseDTO;

public abstract class BaseBuilder<T extends Object, DTO extends BaseDTO> {

	/**
	 * builds dto from entity object
	 * 
	 * @param dto
	 * @return
	 */
	public abstract DTO build(T entity);

	/**
	 * creates {@link List} of {@link BaseDTO}
	 * 
	 * @param entityList
	 * @return {@link List} of {@link BaseDTO}
	 */
	public List<DTO> buildList(List<T> entities) {
		if (entities == null) {
			return null;
		}
		List<DTO> dtoList = new ArrayList<DTO>();
		for (T entity : entities) {
			dtoList.add(build(entity));
		}

		return dtoList;

	}
}
