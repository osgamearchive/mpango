package net.sf.mpango.game.core.terrains;

import net.sf.mpango.game.core.entity.Construction;

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
