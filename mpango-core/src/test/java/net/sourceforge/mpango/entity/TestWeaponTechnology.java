package net.sourceforge.mpango.entity;

import java.util.List;

import net.sourceforge.mpango.entity.technology.WeaponTechnology;

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
	public Integer getTechnologyCost() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
