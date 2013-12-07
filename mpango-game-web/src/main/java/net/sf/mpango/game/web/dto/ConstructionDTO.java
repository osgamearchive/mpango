package net.sf.mpango.game.web.dto;

import net.sf.mpango.common.dto.BaseDTO;
import net.sf.mpango.game.core.enums.ConstructionType;

/**
 * @author aplause
 * 
 */
public class ConstructionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 206151734588794004L;

	private Float maximumHitPoints;
	private Float hitPoints;
	private Float defenseBonus;
	private Float attackBonus;
	private Integer constructionTime;
	private ConstructionType type;

	public Float getMaximumHitPoints() {
		return maximumHitPoints;
	}

	public void setMaximumHitPoints(Float maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
	}

	public Float getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(Float hitPoints) {
		this.hitPoints = hitPoints;
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

	public Integer getConstructionTime() {
		return constructionTime;
	}

	public void setConstructionTime(Integer constructionTime) {
		this.constructionTime = constructionTime;
	}

	public ConstructionType getType() {
		return type;
	}

	public void setType(ConstructionType type) {
		this.type = type;
	}

}
