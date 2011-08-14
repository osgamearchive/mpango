package net.sf.mpango.game.core.ai;

import net.sf.mpango.game.core.entity.Position;

import java.sql.Array;
import java.util.ArrayList;

/**
 * User: leonserg
 * Date: 10.08.11
 * Time: 12:56
 */
public interface Path {
    ArrayList<Position> getPath(Position start, Position goal);
    ArrayList<Position> getParallelPath(Position start);
    ArrayList<Position> getParallelPath(int rowBias, int colBias);
    double getDirection();
    Position getStart();
    Position getGoal();
    int size();
}
