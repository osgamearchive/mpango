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
    /**
	 * 
	 */
	private static final long serialVersionUID = -2291086943507768547L;
	private int rowSize;
    private int colSize;
    private Position difference;
    private Position start;
    private Position goal;
    private double direction;

    public DirectWay() {
    }

    public DirectWay(GameBoard board, Position start, Position goal) {
        this(board.getRowSize(), board.getColSize(), start, goal);
    }

    public DirectWay(int rowSize, int colSize, Position start, Position goal) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.difference = new Position(0, 0);
        this.start = start;
        this.goal = obtainCorrectCoordinates(start, goal);
        this.direction = defineDirection(start, goal);
        getPath(start, goal);
    }

    public int isLarger(int a, int b) {
        int diff = 0;
        if (a > b) diff = 1;
        if (a < b) diff = -1;
        return diff;
    }

    private void defineDifference(Position start, Position goal) {
        this.difference.setRowNumber(isLarger(goal.getRowNumber(), start.getRowNumber()));
        this.difference.setColNumber(isLarger(goal.getColNumber(), start.getColNumber()));
    }

    private Position makeDifference(Position start) {
        start = makeRowDiff(start);
        start = makeColDiff(start);
        return start;
    }

    private Position makeRowDiff(Position start) {
        start.setRowNumber(start.getRowNumber() + difference.getRowNumber());
        return start;
    }

    private Position makeColDiff(Position start) {
        start.setColNumber(start.getColNumber() + difference.getColNumber());
        return start;
    }

    public boolean compareDirections(Position start, Position goal) {
        double d1 = Math.abs(direction - (double) (goal.getRowNumber() - start.getRowNumber() +
                difference.getRowNumber()) / (goal.getColNumber() - start.getColNumber()));
        double d2 = Math.abs(direction - (double) (goal.getRowNumber() - start.getRowNumber()) /
                (goal.getColNumber() - start.getColNumber() + difference.getColNumber()));
        if (d1 > d2) return true;
        else return false;
    }

    private Position getNextPosition(Position start, Position goal) {
        Position next = new Position(start.getRowNumber(), start.getColNumber());
        if (goal.getColNumber() == next.getColNumber() || goal.getRowNumber() == next.getRowNumber()) {
            defineDifference(next, goal);
            next = makeDifference(next);
        } else {
            if (compareDirections(next, goal)) {
                next = makeRowDiff(next);
            } else {
                next = makeColDiff(next);
            }
        }
        return next;
    }

    public void getPath(Position start, Position goal) {
        int length = Math.abs(goal.getRowNumber() - start.getRowNumber()) +
        Math.abs(goal.getColNumber() - start.getColNumber());
        this.add(start);
        defineDifference(start, goal);
        for (int i = 0; i < length; i++) {
            Position next = getNextPosition(this.get(i), goal);
            this.add(new Position(next.getRowNumber(), next.getColNumber()));
        }
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
        start = difference(start, this.start);
        ArrayList<Position> path = new ArrayList<Position>();
        for (Position point : this) {
            Position adding = addition(point, start);
            path.add(new Position(adding.getRowNumber(), adding.getColNumber()));
        }
        return path;
    }

    public Position addition(Position point1, Position point2) {
        return new Position(point1.getRowNumber() + point2.getRowNumber(), point1.getColNumber() + point2.getRowNumber());
    }

    public Position difference(Position point1, Position point2) {
        return new Position(point1.getRowNumber() - point2.getRowNumber(), point1.getColNumber() - point2.getColNumber());
    }

    public void prolong(int points) {
        int times;
        if (points > this.size()) {
            times = points / this.size();
        } else {
            times = 1;
        }
        for (int i = 0; i < times; i++) {
            ArrayList<Position> extra = getParallelPath(this.goal);
            for (int j = 1; j < extra.size(); j++) {
                if (points > 0) this.add(extra.get(j));
                points--;
            }
            this.goal = this.get(this.size() - 1);
        }
    }

    public void trim(int points) {
        for (int i = this.size() - 1; i > 0; i--, points--) if (points > 0) this.remove(i);
        this.goal = this.get(this.size() - 1);
    }

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
