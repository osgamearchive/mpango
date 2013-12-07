package net.sf.mpango.game.core.ai;

import java.util.List;

import net.sf.mpango.game.core.entity.Position;

/**
 * User: leonserg
 * Date: 10.08.11
 * Time: 12:56
 */
public interface Path {
    List<Position> getParallelPath(Position start);
    Position getStart();
    Position getGoal();
    int size();
}
