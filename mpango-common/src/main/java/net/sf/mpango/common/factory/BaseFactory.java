package net.sf.mpango.common.factory;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.dto.BaseDTO;

public abstract class BaseFactory<DTO extends BaseDTO, T extends Object> {

	/**
	 * creates entity from dto
	 * @param dto
	 * @return
	 */
	public abstract T create(DTO dto);

	/**
	 * creates {@link List} of {@link BaseDTO}
	 * 
	 * @param entityList
	 * @return {@link List} of {@link BaseDTO}
	 */
	public List<T> createList(List<DTO> dtoList) {
		List<T> entityList = new ArrayList<T>();
		for (DTO dto : dtoList) {
			entityList.add(create(dto));
		}
		return entityList;
	}
}
