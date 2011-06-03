package net.sourceforge.mpango.dto;

import java.util.List;

public class TechnologyDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -494436229358238932L;

	private Integer technologyCost;
	private List<TechnologyDTO> requiredTechnologies;

	public Integer getTechnologyCost() {
		return technologyCost;
	}

	public void setTechnologyCost(Integer technologyCost) {
		this.technologyCost = technologyCost;
	}

	public List<TechnologyDTO> getRequiredTechnologies() {
		return requiredTechnologies;
	}

	public void setRequiredTechnologies(List<TechnologyDTO> requiredTechnologies) {
		this.requiredTechnologies = requiredTechnologies;
	}

}
