package net.sf.mpango.game.core.technology.entity;

import javax.persistence.Entity;

import net.sf.mpango.game.core.entity.Shield;
import net.sf.mpango.game.core.entity.Technology;

@Entity
public abstract class ShieldTechnology extends Technology {
	
	public abstract Shield createShield();

}
