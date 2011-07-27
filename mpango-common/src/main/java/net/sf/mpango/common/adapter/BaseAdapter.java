package net.sf.mpango.common.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.dto.BaseDTO;

public abstract class BaseAdapter <T extends Object, DTO extends BaseDTO> {

	/**
	 * Method that transforms a DTO to an entity.
	 * @param dto
	 * @return
	 */
	public abstract T fromDTO (DTO dto);
	
	/**
	 * Method that transforms an entity to its DTO.
	 * @param entity
	 * @return
	 */
	public abstract DTO toDTO (T entity);
	
	/**
	 * creates {@link List} of {@link BaseDTO}
	 * @param entityList {@link List} of entities.
	 * @return {@link List} of {@link BaseDTO}
	 */
	public List<DTO> toDTOList (List<T> entities) {
		if (entities == null) {
			return null;
		}
		List<DTO> dtoList = new ArrayList<DTO>();
		for (T entity : entities) {
			dtoList.add(toDTO(entity));
		}
		return dtoList;
	}
	
	/**
	 * Method that creates a {@link List} of entities.
	 * @param dtos dto {@link List}
	 * @return {@link List} of entities.
	 */
	public List<T> fromDTOList(List<DTO> dtos) {
		if (dtos == null) {
			return null;
		}
		List<T> entityList = new ArrayList<T>();
		for (DTO dto : dtos) {
			entityList.add(fromDTO(dto));
		}
		return entityList;
	}
}
