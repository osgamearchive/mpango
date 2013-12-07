package net.sf.mpango.game.web.dto;

public class ConstructCommandDTO extends CommandDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3397411190936096988L;

	private UnitDTO unit;
	private ConstructionDTO construction;
	private CellDTO cell;

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

	public ConstructionDTO getConstruction() {
		return construction;
	}

	public void setConstruction(ConstructionDTO construction) {
		this.construction = construction;
	}

	public CellDTO getCell() {
		return cell;
	}

	public void setCell(CellDTO cell) {
		this.cell = cell;
	}

}
