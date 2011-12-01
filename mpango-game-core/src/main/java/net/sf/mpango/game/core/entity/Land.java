package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.terrains.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: leoserg
 * Date: 15.06.11
 * Time: 14:18
 * This class creates an ArrayList containing the continents, equidistant distributed across the board.
 * The constructor of Land requires a GameBoard and variable of the ratio of land to the sea.
 * And then it set the cells terrain to this GameBoard.
 * 
 */
public class Land extends ArrayList<Continent> {

	private int rowSize;
    private int colSize;
    private int size;
    private Random rand;
    private GameBoard board;

    public Land() {
    }

    public Land(GameBoard board, double ratio) {
        this(board, (int) Math.ceil(ratio * board.getRowSize() * board.getColSize()));
    }

    /**
     * Ratio of land to the sea should be in the range of 0.05 to 0.95
     */
    public Land(GameBoard board, int size) {
        this.rowSize = board.getRowSize();
        this.colSize = board.getColSize();
        this.rand = new Random();
        this.board = board;
        setSize(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size <= rowSize * colSize * 5 / 100) {
            this.size = rowSize * colSize * 5 / 100;
        } else if (size >= rowSize * colSize * 95 / 100) {
            this.size = rowSize * colSize * 95 / 100;
        } else {
            this.size = size;
        }
    }

    public ArrayList<Continent> createLand(String... names) {
        for (int i = 0, namesLength = names.length; i < namesLength; i++) {
            String name = names[i];
            this.get(i).setName(name);
        }
        return this;
    }

    /**
     * Creates a several random continents over the board
     */
    public ArrayList<Continent> createLand(int continents) {
        int[] row = new int[continents];
        int[] col = new int[continents];
        for (int i = 0; i < continents; i++) {
            row[i] = rowSize / continents * i - rand.nextInt(rowSize / continents / 4) + rowSize / continents / 8;
            col[i] = colSize / continents * i - rand.nextInt(colSize / continents / 4) + colSize / continents / 8;
        }
        row = shuffle(row);
        col = shuffle(col);
        int[] sizes = new int[continents];

        for (int i = 0; i < continents - 1; i = i + 2) {
            int randomSize = rand.nextInt(this.size / (continents * 2));
            sizes[i] = this.size / continents + randomSize;
            sizes[i + 1] = this.size / continents - randomSize;
        }
        int tmp = 0;
        for (int i = 0; i < sizes.length; i++) {
            tmp = tmp + sizes[i];
        }
        sizes[continents - 1] = sizes[continents - 1] + (size - tmp);
        sizes = shuffle(sizes);

        for (int i = 0; i < continents; i++) {
            this.add(new Continent(board, row[i], col[i], sizes[i]));
        }
        initTerrain();
        return this;
    }

    public static int[] shuffle(int[] points) {
        Random rand = new Random();
        if (points.length > 1) {
            for (int i = 0; i < points.length * 4; i++) {
                int point1 = rand.nextInt(points.length - 1);
                int point2 = rand.nextInt(points.length - 1);
                int tmp = points[point1];
                points[point1] = points[point2];
                points[point2] = tmp;
            }
            return points;
        } else return points;
    }

    public void initTerrain() {
        int altitude;
        for (int i = 0; i < board.getRowSize(); i++) {
            for (int j = 0; j < board.getColSize(); j++) {
                altitude = board.getCell(j, i).getAltitude();
                boolean polar = (i < board.getRowSize() / 20 || i > board.getRowSize() * 19 / 20);
                boolean subPolar = ((i < board.getRowSize() / 11 || i > board.getRowSize() * 10 / 11) &&
                        rand.nextInt(Math.abs(i - board.getRowSize() / 2)) < board.getRowSize() / 10);
                if (polar || subPolar || altitude >= 100) {
                    Terrain terrain = new IceTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                } else if (100 >= altitude && altitude > 20) {
                    Terrain terrain = new MountainTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                } else if (20 >= altitude && altitude > 1) {
                    Terrain terrain = new FieldTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                } else if (altitude == 1) {
                    Terrain terrain = new DesertTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                } else if (altitude <= 0) {
                    Terrain terrain = new WaterTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                } else {
                    Terrain terrain = new WaterTerrain();
                    board.getCell(j, i).setTerrain(terrain);
                }
            }
        }
    }

    public void demoPrintAltitude() {
        int altitude;
        System.out.println("\u2248" + " - altitude <= 0");
        System.out.println("\u2591" + " - 1 >= altitude && altitude > 0");
        System.out.println("\u2593" + " - 6 >= altitude && altitude > 1");
        System.out.println("\u2588" + " - altitude > 6");        
        for (int i = 0; i < board.getRowSize(); i++) {
            for (int j = 0; j < board.getColSize(); j++) {
                altitude = board.getCell(j, i).getAltitude();
                if (altitude > 6) {
                    System.out.print("\u2588");
                } else if (6 >= altitude && altitude >= 2) {
                    System.out.print("\u2593");
                } else if (altitude == 1) {
                    System.out.print("\u2591");
                } else if (altitude <= 0) {
                    System.out.print("\u2248");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public void demoPrintTerrain() {
        Terrain terrain;
        System.out.println("\u2248" + " - sea");
        System.out.println("\u2593" + " - field");
        System.out.println("\u2591" + " - desert");
        System.out.println("\u2588" + " - mountains");
        System.out.println("\u263C" + " or * - ice");        
        for (int i = 0; i < board.getRowSize(); i++) {
            for (int j = 0; j < board.getColSize(); j++) {
                terrain = board.getCell(j, i).getTerrain();
                if (terrain.getClass().equals(new MountainTerrain().getClass())) {
                    System.out.print("\u2588");
                } else if (terrain.getClass().equals(new FieldTerrain().getClass())) {
                    System.out.print("\u2593");
                } else if (terrain.getClass().equals(new DesertTerrain().getClass())) {
                    System.out.print("\u2591");
                } else if (terrain.getClass().equals(new WaterTerrain().getClass())) {
                    System.out.print("\u2248");
                } else if (terrain.getClass().equals(new IceTerrain().getClass())) {
                    if (board.getCell(j, i).getAltitude() >= 100) {
                        System.out.print("*");
                    } else {
                        System.out.print("\u263C");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}
