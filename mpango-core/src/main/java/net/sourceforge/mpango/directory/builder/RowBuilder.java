package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.RowDTO;
import net.sourceforge.mpango.entity.Row;

/**
 * 
 * @author aplause
 *
 */
public class RowBuilder extends BaseBuilder<Row, RowDTO> {

	private RowBuilder() {
		super();
	}
	
	/**
	 * @return
	 */
	public static RowBuilder instance() {
		return new RowBuilder();
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.Object)
	 */
	@Override
	public RowDTO build(Row entity) {
		RowDTO dto = new RowDTO();
		dto.setId(entity.getIdentifier());
		dto.setPosition(entity.getPosition());
		dto.setSize(entity.getSize());
		dto.setCells(CellBuilder.instance().buildList(entity.getCells()));
		return dto;
	}

}
