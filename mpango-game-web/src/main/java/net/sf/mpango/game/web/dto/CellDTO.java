package net.sf.mpango.game.web.dto;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.dto.BaseDTO;
import net.sf.mpango.game.core.terrains.Terrain;

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
	private Integer altitude;
	private Terrain terrain;	

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
    
	public int getAltitude() {
		return altitude;
	}
	
	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}
	
    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }	

}
