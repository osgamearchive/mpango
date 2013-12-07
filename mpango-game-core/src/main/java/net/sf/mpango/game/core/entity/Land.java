package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.Random;

import net.sf.mpango.game.core.terrains.Terrain;

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
        this(board, (int) Math.ceil(ratio * board.getConfiguration().getRowNumber() * board.getConfiguration().getColNumber()));
    }

    /**
     * Ratio of land to the sea should be in the range of 0.05 to 0.95
     */
    public Land(GameBoard board, int size) {
        this.rowSize = board.getConfiguration().getRowNumber();
        this.colSize = board.getConfiguration().getColNumber();
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
        for (int rowNumber = 0; rowNumber < rowSize; rowNumber++) {
            for (int colNumber = 0; colNumber < colSize; colNumber++) {
                Position position = new Position(rowNumber, colNumber);
                Cell cell = board.getCell(position);
                cell.setTerrain(Terrain.createTerrain(cell, rowNumber));
            }
        }
    }

    public void demoPrintAltitude() {
        int altitude;
        System.out.println("\u2248" + " - altitude <= 0");
        System.out.println("\u2591" + " - 1 >= altitude && altitude > 0");
        System.out.println("\u2593" + " - 6 >= altitude && altitude > 1");
        System.out.println("\u2588" + " - altitude > 6");        
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Position position = new Position(i,j);
                altitude = board.getCell(position).getAltitude();
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
        System.out.println("\u2248" + " - sea");
        System.out.println("\u2593" + " - field");
        System.out.println("\u2591" + " - desert");
        System.out.println("\u2588" + " - mountains");
        System.out.println("\u263C" + " or * - ice");        
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {

                Position position = new Position(i,j);
                Cell cell = board.getCell(position);
                Terrain terrain = cell.getTerrain();
                int altitude = cell.getAltitude();

                switch (terrain) {
                    case MOUNTAIN:
                        System.out.print("\u2588");
                        break;
                    case FIELD:
                        System.out.print("\u2593");
                        break;
                    case DESERT:
                        System.out.print("\u2591");
                        break;
                    case WATER:
                        System.out.print("\u2248");
                        break;
                    case ICE:
                        if (altitude >= 100) {
                            System.out.print("*");
                        } else {
                            System.out.print("\u263C");
                        }
                        break;
                    default:
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println();
        }
    }
}
