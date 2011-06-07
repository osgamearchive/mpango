package net.sourceforge.mpango.entity;

import java.util.Hashtable;

import net.sourceforge.mpango.exception.NotEnoughResourcesException;
import net.sourceforge.mpango.enums.Resources;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CityTest {

	private City city;
	
	@Before
	public void setUp() {
		city = prepareCity();
	}
	
	@Test
	public void testAddNewResources() {
		city = new City();
		city.addResources(Resources.FOOD, 10);
		assertEquals(Integer.valueOf(10), city.getResources().get(Resources.FOOD));
	}
	
	@Test
	public void testAddExistingResources() {
		city.addResources(Resources.FOOD, 10);
		assertEquals(Integer.valueOf(20), city.getResources().get(Resources.FOOD));
	}
	
	@Test
	public void testSubstractEnoughResources() throws NotEnoughResourcesException {
		city.substractResources(Resources.FOOD, 5);
		assertEquals(Integer.valueOf(5), city.getResources().get(Resources.FOOD));
	}
	
	@Test
	public void testSubstractNotEnoughResources() {
		try {
			city.substractResources(Resources.FOOD, 20);
			fail("Expected exception not raised");
		} catch (NotEnoughResourcesException expected) {
			//Do nothing, this is expected to happen
		}
	}

	private City prepareCity() {
		City city = new City();
		Hashtable<Resources, Integer> resources = new Hashtable<Resources,Integer>();
		resources.put(Resources.FOOD, 10);
		city.setResources(resources);
		return city;
	}
}
