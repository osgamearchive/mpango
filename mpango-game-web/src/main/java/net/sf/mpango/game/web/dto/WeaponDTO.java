package net.sf.mpango.game.web.dto;

import net.sf.mpango.common.dto.BaseDTO;

public class WeaponDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6959755504908661439L;

	private float attackBonus;

	public float getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(Float attackBonus) {
		this.attackBonus = attackBonus;
	}

}
