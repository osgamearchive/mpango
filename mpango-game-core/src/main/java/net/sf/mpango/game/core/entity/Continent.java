package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.Random;

import net.sf.mpango.game.core.ai.DirectWay;

/**
 * User: leonserg
 * Date: 28.06.11
 * Time: 15:28
 * The class contains positions belonging to the continent.
 * Method getCells() returns the corresponding position of the cell from the board.
 */
public class Continent extends ArrayList<Position> {

    private class Lay extends ArrayList<Position> {
        @Override
        public boolean add(Position point) {
            for (int i = 0; i < size(); i++) {
                if (get(i).getColNumber() == point.getColNumber() && get(i).getRowNumber() == point.getRowNumber()) {
                    return false;
                }
            }
            return super.add(point);
        }
    }

    private GameBoard board;
    private Random rand;
    private String name;
    private Position center;

    public Continent() {}

    public Continent(GameBoard board, int row, int col, int size) {
        this(board, new Position(row, col), size);
    }

    public Continent(GameBoard board, Position center, int size) {
        this.board = board;
        this.rand = new Random();
        this.center = center;
        ArrayList<Position> points = createPolygonPoints(center, size);
        points = commitPoints(points);
        ArrayList<DirectWay> blank = fillFrame(points);
        ArrayList<Lay> lays = createBlankLaysList(blank);
        Lay foundation = getFoundation(lays, size);
        this.addAll(foundation);
    }

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < this.size(); i++) {
            cells.add(this.board.getCell(this.get(i)));
        }
        return cells;
    }

    public static int radius(int square) {
        return (int) Math.floor(Math.sqrt(square / Math.PI));
    }

    public Position getNeighboringRandomPoint(Position point, int size, int angle, int delta) {
        int rRand = rand.nextInt(delta) - delta / 2;
        int cRand = rand.nextInt(delta) - delta / 2;
        int radius = radius(size) * 12 / 10;
        int x = (int) Math.floor(Math.sin(Math.toRadians(angle)) * radius) + point.getRowNumber() + rRand;
        int y = (int) Math.floor(Math.cos(Math.toRadians(angle)) * radius) + point.getColNumber() + cRand;
        return new Position(x, y);
    }

    public ArrayList<Position> createPolygonPoints(Position center, int size) {
        return createPolygonPoints(center, size, 3 + size / 15);
    }

    public ArrayList<Position> createPolygonPoints(Position center, int size, int pointsNumber) {
        ArrayList<Position> points = new ArrayList<Position>();
        int angle = 360 / pointsNumber;
        points.add(getNeighboringRandomPoint(center, size, 0, 6));
        for (int i = 1; i < pointsNumber; i++) {
            int iAngle = angle * i;
            Position c = getNeighboringRandomPoint(center, size, iAngle, 6);
            points.add(c);
        }
        return points;
    }

    public int areaOfTriangle(Position a, Position b, Position c) {
        return Math.abs((a.getRowNumber() - c.getRowNumber()) * (b.getColNumber() - a.getColNumber())
                - (a.getRowNumber() - b.getRowNumber()) * (c.getColNumber() - a.getColNumber())) / 2;
    }

    private void setNewCenter(ArrayList<Position> points) {
        int centerRow = 0;
        int centerCol = 0;
        for (Position point : points) {
            centerRow = centerRow + point.getRowNumber();
            centerCol = centerCol + point.getColNumber();
        }
        centerRow = centerRow / points.size();
        centerCol = centerCol / points.size();
        this.center.setRowNumber(centerRow);
        this.center.setColNumber(centerCol);
    }

    public Position getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Position> commitPoints(ArrayList<Position> points) {
        ArrayList<Position> sides = new ArrayList<Position>();
        for (int i = 0; i < points.size() - 1; i++) {
            sides.addAll(new DirectWay(this.board, points.get(i), points.get(i + 1)));
        }
        sides.addAll(new DirectWay(this.board, points.get(points.size() - 1), points.get(0)));
        return sides;
    }

    private ArrayList<DirectWay> fillFrame(ArrayList<Position> frame) {
        ArrayList<DirectWay> blankLay = new ArrayList<DirectWay>();
        for (int i = 0; i < frame.size(); i++) {
            blankLay.add(new DirectWay(this.board, frame.get(i), center));
        }
        return blankLay;
    }

    private Lay convertToLay(ArrayList<DirectWay> blankLay) {
        Lay lay = new Lay();
        for (int i = 0; i < blankLay.size(); i++) {
            for (int j = 0; j < blankLay.get(i).size(); j++) {
                lay.add(blankLay.get(i).get(j));
            }
        }
        return lay;
    }

    private int maxLength(ArrayList<ArrayList> list) {
        int maxLength = 1;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (maxLength < list.get(i).size()) maxLength = list.get(i).size();
            }
        }
        return maxLength;
    }

    private ArrayList<Lay> createBlankLaysList(ArrayList<DirectWay> blankLay) {
        ArrayList<Lay> lays = new ArrayList<Lay>();
        int maxLength = maxLength((ArrayList)blankLay);
        for (int i = 0; i < maxLength; i++) {
            int k;
            Lay lay = new Lay();
            for (int j = 0; j < blankLay.size(); j++) {
                if (i >= blankLay.get(j).size() - 1) {
                    k = blankLay.get(j).size() - 1;
                } else k = i;
                Position point = blankLay.get(j).get(k);
                lay.add(point);
            }
            lays.add(lay);
        }
        return lays;
    }

    private Lay getFoundation(ArrayList<Lay> blank, int size) {
        Lay foundation = new Lay();
        for (int i = blank.size() - 1; i > 0; i--) {
            for (int j = 0; j < blank.get(i).size(); j++) {
                if (foundation.size() >= size) break;
                foundation.add(blank.get(i).get(j));
            }
        }
        return foundation;
    }

}