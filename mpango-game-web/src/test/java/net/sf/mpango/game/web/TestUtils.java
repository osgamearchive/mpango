package net.sf.mpango.game.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.action.Command;
import net.sf.mpango.game.core.entity.BoardConfiguration;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.entity.Construction;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Shield;
import net.sf.mpango.game.core.entity.Technology;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.entity.Weapon;
import net.sf.mpango.game.core.enums.Resources;

public class TestUtils {
	
	public static final int ROW_SIZE = 20;
	public static final int COLUMN_SIZE = 15;

	public static City getCity(Long id) {
		City city = new City();
		city.setId(id);
		city.setAttackBonus(1.0f);
		city.setDefenseBonus(1f);
		city.setHitPoints(1f);
		city.setMaximumHitPoints(1f);
		return city;
	}
	
	public static Cell getCell(Long id) {
		Cell cell = new Cell(1,1);
		cell.setIdentifier(id);
		cell.setAttackBonus(1f);
		cell.setColumn(1);
		cell.setDefenseBonus(1f);
		cell.setRow(1);
		cell.setConstructions(new ArrayList<Construction>());
		cell.getConstructions().add(getCity(1L));
		return cell;
	}

	public static GameBoard getGameBoard(Long id) {
		GameBoard board = prepareGameBoard(ROW_SIZE, COLUMN_SIZE);
        board.setId(id);
        return board;
	}
	
	public static Player getPlayer() {
		Player player = new Player();
		player.setState(User.State.CREATED);
		return player;
	}
	
	
	public static Unit getUnit() {
		Unit unit = TestUnit.instance();
		unit.setWeapon(new Weapon(10f));
		unit.setShield(new Shield(10f));
		return unit;
	}

    public static GameBoard prepareGameBoard() {
        return prepareGameBoard(ROW_SIZE, COLUMN_SIZE);
    }

    public static net.sf.mpango.game.core.entity.GameBoard prepareGameBoard (int rowSize, int colSize) {
        BoardConfiguration boardConfiguration = new BoardConfiguration(rowSize, colSize);
        return GameBoard.generateRandomBoard(boardConfiguration);
    }

    public static Cell prepareCell(int colPosition, int rowPosition, Set<Resources> resources) {
        return new Cell(colPosition, rowPosition, resources);
    }
	
	public static Technology getTechnology(Long id, int cost) {
		Technology tech = new Technology();
		tech.setId(id);
		tech.setTechnologyCost(cost);
		tech.setRequiredTechnologies((List<Technology>)new ArrayList<Technology>());
		return tech;
	}
	
	@SuppressWarnings("serial")
	private static class TestUnit extends Unit {
		private TestUnit(List<Technology> technology, List<Command> command) {
			super(new City(), command, technology, 10f, 100f);
		}
		public static Unit instance() {
			List<Technology> tech = new ArrayList<Technology>();
			List<Command> com = new ArrayList<Command>();
			return new TestUnit(tech, com);
		}
	}
	
}
