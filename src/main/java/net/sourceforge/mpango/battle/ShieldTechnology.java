package net.sourceforge.mpango.battle;

import javax.persistence.Entity;

import net.sourceforge.mpango.Technology;

@Entity
public abstract class ShieldTechnology extends Technology {
	
	public abstract Shield createShield();

}
