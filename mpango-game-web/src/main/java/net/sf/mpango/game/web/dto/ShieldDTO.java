package net.sf.mpango.game.web.dto;

import net.sf.mpango.common.dto.BaseDTO;

public class ShieldDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -625113707525711808L;

	private float maximumHitPoints;
	private float hitPoints;

	public float getMaximumHitPoints() {
		return maximumHitPoints;
	}

	public void setMaximumHitPoints(float maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
	}

	public float getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}

}
