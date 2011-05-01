package net.sourceforge.mpango.terrains;

import net.sourceforge.mpango.Construction;

/**
 * Represents the terrain of a cell.
 * Depending on the terrain, one can build different constructions.
 * Each terrain can evolve to a different type depending on an input (human action or random)
 * @author etux
 *
 */
public interface Terrain {

	boolean canBuildConstruction (Construction construction);
	boolean canEvolveTo (Terrain terrain);
	boolean canUpgradeTo (Upgrade upgrade);
}
