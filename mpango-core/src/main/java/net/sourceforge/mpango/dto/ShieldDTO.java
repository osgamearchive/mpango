package net.sourceforge.mpango.dto;

public class ShieldDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -625113707525711808L;

	private Float maximumHitPoints;
	private Float hitPoints;

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

}
