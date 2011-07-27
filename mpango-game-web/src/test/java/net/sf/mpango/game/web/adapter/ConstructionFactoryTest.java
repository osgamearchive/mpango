package net.sf.mpango.game.web.adapter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import net.sf.mpango.game.core.entity.City;
import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.web.dto.CityDTO;
import net.sf.mpango.game.web.TestUtils;

import org.junit.Assert;
import org.junit.Test;


public class ConstructionFactoryTest extends TestCase{

	@Test
	public void testCityFactory() {
		CityDTO dto = TestUtils.getCityDTO(1L);
		
		City city = CityAdapter.instance().fromDTO(dto);
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
		List<City> cityList = CityAdapter.instance().fromDTOList(dtoList);
		Assert.assertNotNull(cityList);
		Assert.assertEquals(cityList.size(), 5);
	}
}
