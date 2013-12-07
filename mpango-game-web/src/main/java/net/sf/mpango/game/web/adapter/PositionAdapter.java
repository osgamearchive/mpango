/**
 * 
 */
package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.PositionDTO;
import net.sf.mpango.game.core.entity.Position;

/**
 * @author aplause
 * @author etux
 *
 */
public class PositionAdapter extends BaseAdapter<Position, PositionDTO> {

	public static PositionAdapter instance() {
		return new PositionAdapter();
	}

	@Override
	public Position fromDTO(PositionDTO dto) {
		if (dto == null) {
			return null;
		}
		Position position = new Position(dto.getRowNumber(), dto.getColNumber());
		return position;
	}

	@Override
	public PositionDTO toDTO(Position position) {
		if (position == null) {
			return null;
		}
		PositionDTO dto = new PositionDTO();
		dto.setColNumber(position.getColNumber());
		dto.setRowNumber(position.getRowNumber());
		return dto;
	}

}
