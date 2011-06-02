package net.sourceforge.mpango.dto;

import java.util.ArrayList;
import java.util.List;

public class CellDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2344214853416733486L;

	private List<ConstructionDTO> constructions = new ArrayList<ConstructionDTO>();
	private Float defenseBonus;
	private Float attackBonus;
	private Integer column;
	private Integer row;

	public List<ConstructionDTO> getConstructions() {
		return constructions;
	}

	public void setConstructions(List<ConstructionDTO> constructions) {
		this.constructions = constructions;
	}

	public Float getDefenseBonus() {
		return defenseBonus;
	}

	public void setDefenseBonus(Float defenseBonus) {
		this.defenseBonus = defenseBonus;
	}

	public Float getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(Float attackBonus) {
		this.attackBonus = attackBonus;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

}
