package net.sf.mpango.game.core.ai;

import net.sf.mpango.game.core.entity.Position;

import java.util.ArrayList;

/**
 * User: leonserg
 * Date: 10.08.11
 * Time: 12:56
 */
public interface Path {
    ArrayList<Position> getParallelPath(Position start);
    Position getStart();
    Position getGoal();
    int size();
}
