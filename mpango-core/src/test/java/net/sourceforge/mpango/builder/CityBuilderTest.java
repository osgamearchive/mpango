package net.sourceforge.mpango.builder;



import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.builder.CityBuilder;
import net.sourceforge.mpango.dto.CityDTO;
import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.TestUtils;

import org.junit.Assert;
import org.junit.Test;

public class CityBuilderTest {

	@Test
	public void testCityBuilder() {
		City city = TestUtils.getCity(1L);
		CityDTO dto = CityBuilder.instance().build(city);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId(), new Long(city.getIdentifier()));
		Assert.assertEquals(dto.getAttackBonus(), new Float(city.getAttackBonus()));
		Assert.assertEquals(dto.getDefenseBonus(), new Float(city.getDefenseBonus()));
		Assert.assertEquals(dto.getHitPoints(), new Float(city.getHitPoints()));
		Assert.assertEquals(dto.getMaximumHitPoints(), new Float(city.getMaximumHitPoints()));						
	}
	
	@Test
	public void testCityBuilderList() {
		List<City> cityList = new ArrayList<City>();
		cityList.add(TestUtils.getCity(1L));
		cityList.add(TestUtils.getCity(2L));
		cityList.add(TestUtils.getCity(3L));
		cityList.add(TestUtils.getCity(4L));
		cityList.add(TestUtils.getCity(5L));
		List<CityDTO> dtoList = CityBuilder.instance().buildList(cityList);
		Assert.assertNotNull(dtoList);
		Assert.assertEquals(dtoList.size(), 5);
	}

	

}
