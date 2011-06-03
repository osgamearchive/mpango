package net.sourceforge.mpango.dto;

import java.util.List;

public class FleetDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3446166023548553397L;

	private OfficialDTO official;
	private List<UnitDTO> units;

	public OfficialDTO getOfficial() {
		return official;
	}

	public void setOfficial(OfficialDTO official) {
		this.official = official;
	}

	public List<UnitDTO> getUnits() {
		return units;
	}

	public void setUnits(List<UnitDTO> units) {
		this.units = units;
	}

}
