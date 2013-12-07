package net.sf.mpango.game.web.dto;

import java.util.List;
import java.util.Timer;

import net.sf.mpango.common.dto.BaseDTO;

public class UnitDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Float maximumHitPoints;
	private Float attackPoints;
	private Float hitPoints;
	private ShieldDTO shield;
	private WeaponDTO weapon;
	private List<TechnologyDTO> technologies;
	private Float constructionSkills;
	private Float collectionSkills;
	private Timer timer;
	private CityDTO city;

	public Float getMaximumHitPoints() {
		return maximumHitPoints;
	}

	public void setMaximumHitPoints(Float maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
	}

	public Float getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(Float attackPoints) {
		this.attackPoints = attackPoints;
	}

	public Float getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(Float hitPoints) {
		this.hitPoints = hitPoints;
	}

	public ShieldDTO getShield() {
		return shield;
	}

	public void setShield(ShieldDTO shield) {
		this.shield = shield;
	}

	public WeaponDTO getWeapon() {
		return weapon;
	}

	public void setWeapon(WeaponDTO weapon) {
		this.weapon = weapon;
	}

	public List<TechnologyDTO> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<TechnologyDTO> technologies) {
		this.technologies = technologies;
	}

	public Float getConstructionSkills() {
		return constructionSkills;
	}

	public void setConstructionSkills(Float constructionSkills) {
		this.constructionSkills = constructionSkills;
	}

	public Float getCollectionSkills() {
		return collectionSkills;
	}

	public void setCollectionSkills(Float collectionSkills) {
		this.collectionSkills = collectionSkills;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

}
