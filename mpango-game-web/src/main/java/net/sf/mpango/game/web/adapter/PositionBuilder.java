/**
 * 
 */
package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.PositionDTO;
import net.sf.mpango.game.core.entity.Position;

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
		if (position == null) {
			return null;
		}
		PositionDTO dto = new PositionDTO();
		dto.setColNumber(position.getColNumber());
		dto.setRowNumber(position.getRowNumber());
		return dto;
	}

}
