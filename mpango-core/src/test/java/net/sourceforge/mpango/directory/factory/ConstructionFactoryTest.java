package net.sourceforge.mpango.directory.factory;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.TestUtils;
import net.sourceforge.mpango.dto.CityDTO;
import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.enums.ConstructionType;

import org.junit.Assert;
import org.junit.Test;


public class ConstructionFactoryTest /*extends TestCase*/{

	@Test
	public void testCityFactory() {
		CityDTO dto = TestUtils.getCity(1L);
		
		City city = CityFactory.instance().create(dto);
		Assert.assertNotNull(city);
		Assert.assertEquals(dto.getType(), ConstructionType.CITY);
	}
	
	@Test
	public void testCityFactoryList() {
		List<CityDTO> dtoList = new ArrayList<CityDTO>();
		dtoList.add(TestUtils.getCity(1L));
		dtoList.add(TestUtils.getCity(2L));
		dtoList.add(TestUtils.getCity(3L));
		dtoList.add(TestUtils.getCity(4L));
		dtoList.add(TestUtils.getCity(5L));
		List<City> cityList = CityFactory.instance().createList(dtoList);
		Assert.assertNotNull(cityList);
		Assert.assertEquals(cityList.size(), 5);
	}
}
