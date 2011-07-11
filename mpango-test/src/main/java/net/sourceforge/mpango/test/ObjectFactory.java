package net.sourceforge.mpango.test;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import net.sourceforge.mpango.enums.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 7/11/11
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectFactory {

    public static net.sourceforge.mpango.entity.GameBoard prepareGameBoard (int colSize, int rowSize) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.setColSize(colSize);
        gameBoard.setRowSize(rowSize);
        List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i<colSize; i++) {
            for (int j = 0; j<rowSize; j++) {
                Cell cell = prepareCell(i,j, null);
            }
        }
        return gameBoard;
    }

    public static Cell prepareCell(int colPosition, int rowPosition, Set<Resources> resources) {
        return new Cell(colPosition, rowPosition, resources);
    }
}
