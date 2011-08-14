package net.sf.mpango.game.core.ai;

import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.entity.Position;

import java.util.ArrayList;

/**
 * User: leonserg
 * Date: 08.08.11
 * Time: 18:42
 */
public class DirectWay extends ArrayList<Position> implements Path {
    private int rowSize;
    private int colSize;
    private Position bias;
    private Position start;
    private Position goal;
    private double direction;

    public DirectWay() {
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public DirectWay(GameBoard board, Position start, Position goal) {
        this(board.getRowSize(), board.getColSize(), start, goal);
    }

    public DirectWay(int rowSize, int colSize, Position start, Position goal) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.bias = new Position(0, 0);
        this.start = start;
        this.goal = obtainCorrectCoordinates(start, goal);
        this.direction = defineDirection(start, goal);
        this.addAll(getPath(start, goal));
    }

    public int isLarger(int a, int b) {
        int inc = 0;
        if (a > b) inc = 1;
        if (a < b) inc = -1;
        return inc;
    }

    public void defineBias(Position start, Position goal) {
        this.bias.setRowNumber(isLarger(goal.getRowNumber(), start.getRowNumber()));
        this.bias.setColNumber(isLarger(goal.getColNumber(), start.getColNumber()));
    }

    public Position makeBias(Position start) {
        start = makeRowBias(start);
        start = makeColBias(start);
        return start;
    }

    public Position makeRowBias(Position start) {
        start.setRowNumber(start.getRowNumber() + bias.getRowNumber());
        return start;
    }

    public Position makeColBias(Position start) {
        start.setColNumber(start.getColNumber() + bias.getColNumber());
        return start;
    }

    public boolean compareDirections(Position start, Position goal) {
        double d1 = Math.abs(direction - (double) (goal.getRowNumber() - start.getRowNumber() +
                bias.getRowNumber()) / (goal.getColNumber() - start.getColNumber()));
        double d2 = Math.abs(direction - (double) (goal.getRowNumber() - start.getRowNumber()) /
                (goal.getColNumber() - start.getColNumber() + bias.getColNumber()));
        if (d1 > d2) return true;
        else return false;
    }

    public Position getNextPosition(Position start, Position goal) {
        if (goal.getColNumber() == start.getColNumber() || goal.getRowNumber() == start.getRowNumber()) {
            defineBias(start, goal);
            start = makeBias(start);
        } else {
            if (compareDirections(start, goal)) {
                start = makeRowBias(start);
            } else {
                start = makeColBias(start);
            }
        }
        return start;
    }

    public ArrayList<Position> getPath(Position start, Position goal) {
        ArrayList<Position> path = new ArrayList<Position>();
        int length = Math.abs(goal.getRowNumber() - start.getRowNumber()) +
        Math.abs(goal.getColNumber() - start.getColNumber());
        defineBias(start, goal);
        path.add(new Position(start.getRowNumber(), start.getColNumber()));
        for (int i = 0; i < length; i++) {
            Position next = getNextPosition(path.get(i), goal);
            path.add(new Position(next.getRowNumber(), next.getColNumber()));
        }
        return path;
    }

    public Position obtainCorrectCoordinates(Position start, Position goal) {
        goal.setRowNumber(obtainCorrectDirection(start.getRowNumber(), goal.getRowNumber(), this.rowSize));
        goal.setColNumber(obtainCorrectDirection(start.getColNumber(), goal.getColNumber(), this.colSize));
        return goal;
    }

    public int obtainCorrectDirection(int start, int goal, int maxSize) {
        if (maxSize - goal + start < goal - start) {
            goal = -maxSize + goal;
        } else if (start - goal > maxSize - (start - goal)) {
            goal = maxSize + goal;
        }
        return goal;
    }

    public double getMagnitude(Position start, Position goal) {
        return (Math.sqrt((goal.getRowNumber() - start.getRowNumber()) * (goal.getRowNumber() - start.getRowNumber()) +
                (goal.getColNumber() - start.getColNumber()) * (goal.getColNumber() - start.getColNumber())));
    }

    public double defineDirection(Position start, Position goal) {
        double rows = goal.getRowNumber() - start.getRowNumber();
        double cols = goal.getColNumber() - start.getColNumber();
        return rows / cols;
    }

    @Override
    public ArrayList<Position> getParallelPath(Position start) {
        int rowBias = start.getRowNumber() - this.start.getRowNumber();
        int colBias = start.getColNumber() - this.start.getColNumber();
        return getParallelPath(rowBias, colBias);
    }

    @Override
    public ArrayList<Position> getParallelPath(int rowBias, int colBias) {
        ArrayList<Position> parallelPath = new ArrayList<Position>();
        for (Position point : this) {
            parallelPath.add(new Position(point.getRowNumber() + rowBias, point.getColNumber() + colBias));
        }
        return parallelPath;
    }

    public void prolong() {
    }

    public void trim() {
    }

    @Override
    public double getDirection() {
        return defineDirection(this.start, this.goal);
    }

    @Override
    public Position getStart() {
        return this.start;
    }

    @Override
    public Position getGoal() {
        return this.goal;
    }
}
