package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.entity.Cell;

public class CellFactory extends BaseFactory<CellDTO, Cell> {
	private CellFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return
	 */
	public static CellFactory instance() {
		return new CellFactory();

	}

	
	/* (non-Javadoc)
	 * @see net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge.mpango.dto.BaseDTO)
	 */
	public Cell create(CellDTO dto) {

		Cell cell = new Cell();
		cell.setColumn(dto.getColumn());
		cell.setRow(dto.getRow());
		cell.setIdentifier(dto.getId());
		cell.setAttackBonus(dto.getAttackBonus());
		cell.setConstructions(ConstructionFactory.instance().createList(
				dto.getConstructions()));
		cell.setDefenseBonus(dto.getDefenseBonus());

		return cell;
	}
	
	
}
