package net.sf.mpango.game.core.technology.entity;

import javax.persistence.Entity;

import net.sf.mpango.game.core.entity.Technology;
import net.sf.mpango.game.core.entity.Weapon;

@Entity
public abstract class WeaponTechnology extends Technology {

	public abstract Weapon createWeapon();

}
