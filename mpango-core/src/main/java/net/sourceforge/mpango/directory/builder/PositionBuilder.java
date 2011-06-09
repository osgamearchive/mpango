/**
 * 
 */
package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.PositionDTO;
import net.sourceforge.mpango.entity.Position;

/**
 * @author aplause
 *
 */
public class PositionBuilder extends BaseBuilder<Position, PositionDTO> {

	public static PositionBuilder instance() {
		
		return new PositionBuilder();
	}

	@Override
	public PositionDTO build(Position position) {
		PositionDTO dto = new PositionDTO();
		dto.setColNumber(position.getColNumber());
		dto.setRowNumber(position.getRowNumber());
		return dto;
	}

}
