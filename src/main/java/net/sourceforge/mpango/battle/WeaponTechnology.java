package net.sourceforge.mpango.battle;

import javax.persistence.Entity;

import net.sourceforge.mpango.Technology;

@Entity
public abstract class WeaponTechnology extends Technology {

	public abstract Weapon createWeapon();

}
