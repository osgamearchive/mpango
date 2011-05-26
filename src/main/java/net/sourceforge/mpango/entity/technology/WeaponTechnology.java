package net.sourceforge.mpango.entity.technology;

import javax.persistence.Entity;

import net.sourceforge.mpango.entity.Technology;
import net.sourceforge.mpango.entity.Weapon;

@Entity
public abstract class WeaponTechnology extends Technology {

	public abstract Weapon createWeapon();

}
