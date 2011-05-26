package net.sourceforge.mpango.terrains;

import net.sourceforge.mpango.entity.Construction;

public class WaterTerrain implements Terrain{

	public boolean canBuildConstruction(Construction construction) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canEvolveTo(Terrain terrain) {
		boolean canEvolve = false;
		if (terrain instanceof DesertTerrain) {
			canEvolve = true;	
		} 
		return canEvolve;
	}

	public boolean canUpgradeTo(Upgrade upgrade) {
		return false;
	}

}
