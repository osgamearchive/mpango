package net.sourceforge.mpango.entity;

import java.util.List;

import net.sourceforge.mpango.entity.technology.ShieldTechnology;

public class TestShieldTechnology extends ShieldTechnology {
	
	public static final Float MAXIMUM_SHIELD_HIT_POINTS = 5f;
	
	public TestShieldTechnology() {}
	
	public Shield createShield() {
		return new Shield(MAXIMUM_SHIELD_HIT_POINTS);
	}
	public List<Technology> getRequiredTechnologies() {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getTechnologyCost() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
