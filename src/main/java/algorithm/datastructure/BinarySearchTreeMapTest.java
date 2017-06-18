package algorithm.datastructure;

import test.TestBase;

public class BinarySearchTreeMapTest extends TestBase {
	
	public void testFirstLastKey() {

		BinarySearchTreeMap<String, String> map = new BinarySearchTreeMap<String, String>();
		map.put("3", "c");
		map.put("1", "a");
		map.put("4", "d");
		map.put("2", "b");
		map.put("7", "g");
		map.put("6", "f");
		assertEquals(map.firstKey(), "1");
		assertEquals(map.lastKey(), "7");
	}
	
	public void testFloorCeilingKey() {

		BinarySearchTreeMap<String, String> map = new BinarySearchTreeMap<String, String>();
		map.put("3", "c");
		map.put("1", "a");
		map.put("4", "d");
		map.put("2", "b");
		map.put("7", "g");
		map.put("6", "f");

		assertEquals(map.floorKey("2"), "2");
		assertEquals(map.floorKey("3"), "3");
		assertEquals(map.floorKey("4"), "4");
		assertEquals(map.floorKey("5"), "4");
		assertEquals(map.floorKey("6"), "6");
		assertEquals(map.floorKey("7"), "7");
		assertEquals(map.floorKey("8"), "7");
		
		assertEquals(map.lowerKey("2"), "1");
		assertEquals(map.lowerKey("3"), "2");
		assertEquals(map.lowerKey("4"), "3");
		assertEquals(map.lowerKey("5"), "4");
		assertEquals(map.lowerKey("6"), "4");
		assertEquals(map.lowerKey("7"), "6");
		assertEquals(map.lowerKey("8"), "7");
		
		assertEquals(map.ceilingKey("2"), "2");
		assertEquals(map.ceilingKey("3"), "3");
		assertEquals(map.ceilingKey("4"), "4");
		assertEquals(map.ceilingKey("5"), "6");
		assertEquals(map.ceilingKey("6"), "6");
		assertEquals(map.ceilingKey("7"), "7");
		assertEquals(map.ceilingKey("8"), null);
		
		assertEquals(map.higherKey("2"), "3");
		assertEquals(map.higherKey("3"), "4");
		assertEquals(map.higherKey("4"), "6");
		assertEquals(map.higherKey("5"), "6");
		assertEquals(map.higherKey("6"), "7");
		assertEquals(map.higherKey("7"), null);
		assertEquals(map.higherKey("8"), null);
	}
	
}
