package net.sourceforge.mpango.dto;

import net.sourceforge.mpango.enums.ConstructionType;

/**
 * @author aplause
 * 
 */
public class CityDTO extends ConstructionDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5066978318662760411L;

	public CityDTO() {
		setType(ConstructionType.CITY);
	}

}
