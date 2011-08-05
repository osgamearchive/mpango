package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: leonserg
 * Date: 28.06.11
 * Time: 15:28
 * This class creates an ArrayList containing a cluster of cells. 
 */
public class Continent extends ArrayList<Cell> {

	private GameBoard board;
    private Random rand;
    private String name;

    public enum Type {ROCK, PLATO, VOLCANO}

    public Continent(GameBoard board) {
        this.board = board;
        this.rand = new Random();
    }

    public Continent(GameBoard board, int row, int col, int size, Type type) {
        this(board);
        switch (type) {
            case ROCK:
                createRock(row, col, size);
                break;
            case PLATO:
                createPlato(row, col, size);
                break;
            case VOLCANO:
                createVolcano(row, col, size);
                break;
            default:
                createPlato(row, col, size);
                break;
        }
    }

    public Continent() {
    }

    public void createRock(int row, int col, int size) {
        int radius = radius(size);
        ArrayList<Cell> frame = getRandomPoints(row, col, radius, radius + 8, 2);
        ArrayList<ArrayList<Cell>> rock = getClustersList(frame, board.getCell(row, col));
        rock = trimToSize(rock, size);
        this.addAll(lay(rock));
    }

    public void createVolcano(int row, int col, int size) {

    }

    public void createPlato(int row, int col, int size) {

    }

    public ArrayList<Cell> lay(ArrayList<ArrayList<Cell>> layers) {
        ArrayList<Cell> surface = layers.get(0);
        for (int i = 0; i < layers.size(); i++) {
            for (int j = 0; j < layers.get(i).size(); j++) {
                int alt = layers.get(i).get(j).getAltitude();
                alt++;
                layers.get(i).get(j).setAltitude(alt);
            }
        }
        return surface;
    }

    public ArrayList<Cell> getBorder() {
        return null;
    }

    // TODO very expensive and failed method, need remake it
    public ArrayList<ArrayList<Cell>> trimToSize(ArrayList<ArrayList<Cell>> list, int size) {
        ArrayList<ArrayList<Cell>> internal = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<Cell>> external = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).size() > size) {
                external.add(list.get(i));
            } else {
                internal.add(list.get(i));
            }
        }
        ArrayList<Cell> border = internal.get(0);
        for (int i = 0; i < external.get(external.size() - 1).size(); i++) {
            border.add(external.get(external.size() - 1).get(i));
            if (border.size() >= size) break;
        }
        internal.add(border);
        return internal;
    }

    public static int radius(int square) {
        return (int) Math.floor(Math.sqrt(square / Math.PI));
    }

    // TODO very expensive and failed method, need remake it
    public ArrayList<ArrayList<Cell>> getClustersList(ArrayList<Cell> points, Cell center) {
        ArrayList<ArrayList<Cell>> clasters = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<Cell>> rays = new ArrayList<ArrayList<Cell>>();
        int max = this.board.getWay(points.get(0), center).size();
        for (Cell point : points) {
            rays.add(this.board.getWay(point, center));
            int current = this.board.getWay(point, center).size();
            if (max < current) max = current;
        }
        for (int i = 0; i < max; i++) {
            ArrayList<Cell> contour = new ArrayList<Cell>();
            for (int j = 0; j < rays.size(); j++) {
                if (i < rays.get(j).size()) {
                    contour.add(rays.get(j).get(i));
                } else {
                    contour.add(rays.get(j).get(rays.get(j).size() - 1));
                }
            }
            clasters.add(getCluster(contour, center));
        }
        return clasters;
    }

    //This method selects a random cell around the center
    public ArrayList<Cell> getRandomPoints(int row, int col, int radius, int pointsNumber, int delta) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        int angle = 360 / pointsNumber;
        int x;
        int y;
        int randRow;
        int randCol;
        for (int i = 0; i < pointsNumber; i++) {
            if (delta > 0) {
                randRow = rand.nextInt(delta) - delta / 2;
                randCol = rand.nextInt(delta) - delta / 2;
            } else {
                randRow = 0;
                randCol = 0;
            }
            x = (int) Math.floor(Math.sin(Math.toRadians(angle * i)) * radius) + row + randRow;
            y = (int) Math.floor(Math.cos(Math.toRadians(angle * i)) * radius) + col + randCol;
            cells.add(this.board.getCell(x, y));
        }
        return cells;
    }

    //This method combines a cell, successively arranged in an array and creates a frame
    private ArrayList<Cell> commitPoints(ArrayList<Cell> cells) {
        ArrayList<Cell> sides = new ArrayList<Cell>();
        for (int i = 0; i < cells.size() - 1; i++) {
            sides.addAll(this.board.getWay(cells.get(i), cells.get(i + 1)));
        }
        sides.addAll(this.board.getWay(cells.get(cells.size() - 1), cells.get(0)));
        return sides;
    }


    public ArrayList<Cell> getCluster(ArrayList<Cell> frame, Cell center) {
        ArrayList<Cell> cluster = new ArrayList<Cell>();
        ArrayList<Cell> cells = new ArrayList<Cell>();
        frame = commitPoints(frame);
        for (int i = 0; i < frame.size(); i++) {
            ArrayList<Cell> ray = this.board.getWay(center, frame.get(i));
            for (int j = 0; j < ray.size(); j++) cells.add(ray.get(j));
        }
        for (int i = 0; i < cells.size(); i++) cluster.add(cells.get(i));
        cluster.add(center);
        return cluster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
