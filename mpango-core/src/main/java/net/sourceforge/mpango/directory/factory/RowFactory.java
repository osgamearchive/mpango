package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.RowDTO;
import net.sourceforge.mpango.entity.Row;

public class RowFactory extends BaseFactory<RowDTO, Row> {

	private RowFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return
	 */
	public static RowFactory instance() {
		return new RowFactory();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge
	 * .mpango.dto.BaseDTO)
	 */
	public Row create(RowDTO dto) {

		Row row = new Row();
		row.setIdentifier(dto.getId());
		row.setPosition(dto.getPosition());
		row.setSize(dto.getSize());
		row.setCells(CellFactory.instance().createList(dto.getCells()));

		return row;

	}

}
