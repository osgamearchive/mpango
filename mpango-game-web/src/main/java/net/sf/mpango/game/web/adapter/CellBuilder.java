package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.core.entity.Cell;

public class CellBuilder extends BaseBuilder<Cell, CellDTO> {
	private CellBuilder() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return {@link CellBuilder}
	 */
	public static CellBuilder instance() {
		return new CellBuilder();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.
	 * Object)
	 */
	public CellDTO build(Cell cell) {

		if (null == cell) {
			return null;
		}

		CellDTO dto = new CellDTO();
		dto.setId(cell.getIdentifier());
		dto.setAttackBonus(cell.getAttackBonus());
		dto.setColumn(cell.getColumn());
		dto.setConstructions(ConstructionBuilder.instance().buildList(
				cell.getConstructions()));
		dto.setDefenseBonus(cell.getDefenseBonus());
		dto.setRow(cell.getRow());

		return dto;
	}

}
