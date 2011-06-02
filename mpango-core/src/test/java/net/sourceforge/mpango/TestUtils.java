package net.sourceforge.mpango;

import net.sourceforge.mpango.dto.CityDTO;

import org.junit.Ignore;

@Ignore
public class TestUtils {

	public static CityDTO getCity(Long id) {
		CityDTO dto = new CityDTO();
		dto.setId(id);
		dto.setAttackBonus(1.0f);
		dto.setConstructionTime(1);
		dto.setDefenseBonus(1f);
		dto.setHitPoints(1f);
		dto.setMaximumHitPoints(1f);
		return dto;
	}
}
