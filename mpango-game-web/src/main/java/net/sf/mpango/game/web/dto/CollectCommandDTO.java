package net.sf.mpango.game.web.dto;

public class CollectCommandDTO extends CommandDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5654708915862670821L;

	private CellDTO cell;
	private UnitDTO unit;

	public CellDTO getCell() {
		return cell;
	}

	public void setCell(CellDTO cell) {
		this.cell = cell;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

}
