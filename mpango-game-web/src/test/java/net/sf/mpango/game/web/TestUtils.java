package net.sf.mpango.game.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.action.Command;
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
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.web.dto.CityDTO;
import net.sf.mpango.game.web.dto.ConstructionDTO;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import net.sf.mpango.game.web.dto.PlayerDTO;

public class TestUtils {
	
	public static final int ROW_SIZE = 20;
	public static final int COLUMN_SIZE = 15;

	public static City getCity(Long id) {
		City city = new City();
		city.setIdentifier(id);
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
		GameBoard board = GameBoard.generateRandomBoard(ROW_SIZE, COLUMN_SIZE);
        board.setIdentifier(id);
        return board;
	}
	
	public static Player getPlayer() {
		Player player = new Player();
		player.setState(StateEnum.CREATED);
		return player;
	}
	
	
	public static Unit getUnit() {
		Unit unit = TestUnit.instance();
		unit.setWeapon(new Weapon(10f));
		unit.setShield(new Shield(10f));
		return unit;
	}	

    public static net.sf.mpango.game.core.entity.GameBoard prepareGameBoard (int colSize, int rowSize) {
        return GameBoard.generateRandomBoard(rowSize, colSize);
    }

    public static Cell prepareCell(int colPosition, int rowPosition, Set<Resources> resources) {
        return new Cell(colPosition, rowPosition, resources);
    }
	
	public static Technology getTechnology(Long id, int cost) {
		Technology tech = new Technology();
		tech.setIdentifier(id);
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
	
	public static CityDTO getCityDTO(Long id) {
		CityDTO dto = new CityDTO();
		dto.setId(id);
		dto.setAttackBonus(1.0f);
		dto.setConstructionTime(1);
		dto.setDefenseBonus(1f);
		dto.setHitPoints(1f);
		dto.setMaximumHitPoints(1f);
		return dto;
	}
	
	public static CellDTO getCellDTO(Long id) {
		CellDTO dto = new CellDTO();
		dto.setId(id);
		dto.setAttackBonus(1f);
		dto.setColumn(1);
		dto.setDefenseBonus(1f);
		dto.setRow(1);
		dto.setConstructions(new ArrayList<ConstructionDTO>());
		dto.getConstructions().add(getCityDTO(1L));
		return dto;
	}

	public static GameBoardDTO getGameBoardDTO(Long id) {

		GameBoardDTO dto = new GameBoardDTO();
		dto.setId(id);
		dto.setColSize(COLUMN_SIZE);
		dto.setRowSize(ROW_SIZE);
		dto.setCells(new CellDTO[ROW_SIZE][COLUMN_SIZE]);

		for (Integer row = 0; row < ROW_SIZE; row++) {
			for (Integer col = 0; col < COLUMN_SIZE; col++) {
				dto.getCells()[row][col] = TestUtils.getCellDTO(new Long(row
						* col));
			}
		}

		return dto;

	}

	public static PlayerDTO getPlayerDTO() {
		PlayerDTO dto = new PlayerDTO();
		dto.setState(StateEnum.CREATED);
		return dto;
	}
	
}
