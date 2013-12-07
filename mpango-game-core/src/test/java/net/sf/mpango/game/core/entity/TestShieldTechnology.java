package net.sf.mpango.game.core.entity;

import java.util.List;

import net.sf.mpango.game.core.technology.entity.ShieldTechnology;

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
	public int getTechnologyCost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
