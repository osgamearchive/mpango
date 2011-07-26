package net.sf.mpango.game.core.builder;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.mpango.game.core.TestUtils;
import net.sf.mpango.game.core.builder.CityBuilder;
import net.sf.mpango.game.core.dto.CityDTO;
import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.enums.ConstructionType;

import org.junit.Assert;
import org.junit.Test;

public class ConstructionBuilderTest extends TestCase {

	@Test
	public void testCityBuilder() {
		City city = TestUtils.getCity(1L);
		CityDTO dto = CityBuilder.instance().build(city);
		Assert.assertNotNull(city);
		Assert.assertEquals(dto.getType(), ConstructionType.CITY);
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