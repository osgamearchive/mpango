package net.sf.mpango.game.core.entity;

import java.util.List;

import net.sf.mpango.game.core.technology.entity.WeaponTechnology;

public class TestWeaponTechnology extends WeaponTechnology {
	
	public static final Float WEAPON_ATTACK_POINTS = 3f;
	
	public TestWeaponTechnology() {}
	
	public Weapon createWeapon() {
		return new Weapon(WEAPON_ATTACK_POINTS);
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
