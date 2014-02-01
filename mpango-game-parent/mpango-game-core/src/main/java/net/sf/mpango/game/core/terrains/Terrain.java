package net.sf.mpango.game.core.terrains;

import java.util.Random;

import net.sf.mpango.game.core.entity.Cell;

/**
 * Represents the terrain of a cell.
 * Depending on the terrain, one can build different constructions.
 * Each terrain can evolve to a different type depending on an input (human action or RANDOM)
 * @author etux
 *
 */
public enum Terrain implements Comparable<Terrain> {

    WATER,
    DESERT,
    FIELD,
    MOUNTAIN,
    ICE;


    private Terrain() {}

    private static final Random RANDOM = new Random();

    public static Terrain createTerrain(final Cell cell, final int rowSize) {
            assert cell != null;

            Terrain terrain = null;
            boolean polar = (cell.getRow() < rowSize / 20 || cell.getRow() > rowSize * 19 / 20);
            boolean subPolar = ((cell.getRow() < rowSize / 11 || cell.getRow() > rowSize * 10 / 11) && RANDOM.nextInt(Math.abs(cell.getRow() - rowSize / 2)) < rowSize / 10);
            if (polar || subPolar || cell.getAltitude() >= 100) {
                terrain = Terrain.ICE;
            } else if (100 >= cell.getAltitude() && cell.getAltitude() > 20) {
                terrain = Terrain.MOUNTAIN;
            } else if (20 >= cell.getAltitude() && cell.getAltitude() > 1) {
                terrain = Terrain.FIELD;
            } else if (cell.getAltitude() == 1) {
                terrain = Terrain.DESERT;
            } else if (cell.getAltitude() <= 0) {
                terrain = Terrain.WATER;
            } else {
                terrain = Terrain.WATER;
            }

            assert terrain != null;

            return terrain;
    }


}
