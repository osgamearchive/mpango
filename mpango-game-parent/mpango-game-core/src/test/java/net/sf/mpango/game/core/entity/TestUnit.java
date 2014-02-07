package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TestUnit extends Unit {

	public static final Float UNIT_ATTACK_POINTS = 10f;
	public static final Float UNIT_HIT_POINTS = 15f;
	public static final Float SHIELD_HIT_POINTS = 5f;
	
	public TestUnit() {
		super(
				new City(),
				createTechnologies(), 
				UNIT_ATTACK_POINTS, 
				UNIT_HIT_POINTS
			);
	}
	public float repair() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static List<Technology> createTechnologies() {
		List<Technology> technologies = new ArrayList<Technology>();
		technologies.add(new TestShieldTechnology());
		technologies.add(new TestWeaponTechnology());
		return technologies;
	}
	
}
