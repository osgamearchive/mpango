package net.sourceforge.mpango.directory.factory;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.mpango.TestUtils;
import net.sourceforge.mpango.dto.CityDTO;
import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.enums.ConstructionType;

import org.junit.Assert;
import org.junit.Test;


public class ConstructionFactoryTest extends TestCase{

	@Test
	public void testCityFactory() {
		CityDTO dto = TestUtils.getCityDTO(1L);
		
		City city = CityFactory.instance().create(dto);
		Assert.assertNotNull(city);
		Assert.assertEquals(city.getType(), ConstructionType.CITY);
	}
	
	@Test
	public void testCityFactoryList() {
		List<CityDTO> dtoList = new ArrayList<CityDTO>();
		dtoList.add(TestUtils.getCityDTO(1L));
		dtoList.add(TestUtils.getCityDTO(2L));
		dtoList.add(TestUtils.getCityDTO(3L));
		dtoList.add(TestUtils.getCityDTO(4L));
		dtoList.add(TestUtils.getCityDTO(5L));
		List<City> cityList = CityFactory.instance().createList(dtoList);
		Assert.assertNotNull(cityList);
		Assert.assertEquals(cityList.size(), 5);
	}
}
