import static org.junit.Assert.*;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;


public class IntSetTest {

	@BeforeClass
	public static void setUp() {
		System.out.println("Running tests...");
	}
	
	@AfterClass
	public static void close() {
		System.out.println("Finished.");
	}
	
	@Test
	public void constructorTest() {
		IntSet set1 = new IntSet(10);
		assertTrue(set1.getCapacity() >= 0);
		assertEquals(set1.getCount(), 0);
		assertEquals(set1.getCapacity(),10);
		assertTrue(set1.getCount() <= set1.getCapacity());
		
		IntSet set2 = new IntSet(20);
		assertTrue(set2.getCapacity() >= 0);
		assertEquals(set2.getCount(), 0);
		assertEquals(set2.getCapacity(),20);
		assertTrue(set2.getCount() <= set2.getCapacity());
	}
	
	@Test
	public void isEmptyTest() {
		IntSet set1 = new IntSet(10);
		assertTrue(set1.isEmpty());
		assertEquals(set1.getCount(),0);
		
		set1.add(12);
		assertFalse(set1.isEmpty());
		assertEquals(set1.getCount(),1);
		
		set1.remove(12);
		assertTrue(set1.isEmpty());
		assertEquals(set1.getCount(),0);
	}
	
	@Test
	public void hasTest() {
		IntSet set1 = new IntSet(10);
		assertFalse(set1.has(0));
		
		set1.add(3);
		set1.add(11);
		set1.add(92);
		assertTrue(set1.has(3));
		assertTrue(set1.has(11));
		assertTrue(set1.has(92));
		assertFalse(set1.has(91));
		
		set1.remove(11);
		assertFalse(set1.has(11));
	}
	
	@Test
	public void addTest(){
		IntSet set1 = new IntSet(100);
		int[] testArray;
	
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set1.add(4);
		set1.add(5);
		assertEquals(set1.getCount(),5);
		set1.add(6);
		set1.add(7);
		set1.add(8);
		set1.add(9);
		assertEquals(set1.getCount(),9);
		set1.add(10);
		set1.add(11);
		
		testArray = set1.getArray();
		assertEquals(testArray[0], 1);
		assertEquals(testArray[1], 2);
		assertEquals(testArray[2], 3);
		assertEquals(testArray[10], 11);
		assertEquals(set1.getCount(),11);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void addTest2() {
	    IntSet set1  = new IntSet(2);
	    set1.add(3);
	    set1.add(10);
	    assertTrue(set1.getCount() == 2);
	    set1.add(4);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void addTest3() {
	    IntSet set1  = new IntSet(10);
		set1.add(10);
		assertTrue(set1.getCount() == 1);
		set1.add(10);
	}
	
	@Test
	public void removeTest(){
		IntSet set1 = new IntSet(100);
		int[] testArray;
	
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set1.add(4);
		set1.add(5);
		assertEquals(set1.getCount(),5);
		
		set1.remove(2);
		assertEquals(set1.getCount(),4);
		set1.remove(4);

		testArray = set1.getArray();
		assertEquals(testArray[0], 1);
		assertEquals(testArray[1], 3);
		assertEquals(testArray[2], 5);
		assertEquals(set1.getCount(),3);
		
		
	}
	
	@Test(expected=NoSuchElementException.class)
	public void removeTest2() {
	    IntSet set1  = new IntSet(10);
	    assertTrue(set1.getCount() == 0);
	    set1.remove(3);
	}
	
	@Test
	public void intersectTest(){
		IntSet set1 = new IntSet(10);
		IntSet set2 = new IntSet(10);
		IntSet set3 = new IntSet(10);
	
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set2.add(1);
		set2.add(2);
		set2.add(4);
		
		assertEquals(set3.getCount(),0);
		set3 = set1.intersect(set2);
		assertTrue(set3.has(1));
		assertTrue(set3.has(2));
		assertFalse(set3.has(3));
		assertFalse(set3.has(4));
		assertEquals(set3.getCount(),2);
	}
	
	@Test
	public void unionTest(){
		IntSet set1 = new IntSet(10);
		IntSet set2 = new IntSet(10);
		IntSet set3 = new IntSet(10);
	
		set1.add(2);
		set1.add(4);
		set1.add(6);
		set2.add(1);
		set2.add(3);
		set2.add(2);
		set2.add(4);
		set2.add(5);
		
		assertEquals(set3.getCount(),0);
		set3 = set1.union(set2);
		assertTrue(set3.has(1));
		assertTrue(set3.has(4));
		assertTrue(set3.has(6));
		assertTrue(set3.has(5));
		assertEquals(set3.getCount(),6);
	}
	
	@Test
	public void getArrayTest(){
		IntSet set1 = new IntSet(10);
		assertEquals(set1.getArray(), set1.array);
		assertEquals(set1.getArray().length, set1.getCapacity());
		for(int i =0; i < set1.array.length; i++){
			assertEquals(set1.getArray()[i], set1.array[i]);
		}
	}
	
	
	@Test
	public void getCapacityTest(){
		IntSet set1 = new IntSet(10);
		assertEquals(set1.getCapacity(),set1.capacity);
	}
	
	
	@Test
	public void getCountTest(){
		IntSet set1 = new IntSet(10);
		set1.add(10);
		set1.add(20);
		set1.add(30);
		assertEquals(set1.getCount(),3);
		
		set1.add(40);
		set1.add(50);
		assertEquals(set1.getCount(),5);
		
		set1.remove(40);
		set1.remove(50);
		assertEquals(set1.getCount(),3);
		
		set1.remove(10);
		set1.remove(20);
		set1.remove(30);
		assertEquals(set1.getCount(),0);
	}
	
	@Test
	public void toStringTest(){
		IntSet set1 = new IntSet(10);
		IntSet set2 = new IntSet(20);
		IntSet set3 = new IntSet(5);
		
		set1.add(1);
		assertEquals(set1.toString(), "{1}");
		
		set2.add(2);
		set2.add(3);
		set2.add(6);
		set2.add(10);
		assertEquals(set2.toString(), "{2,3,6,10}");
		
	    assertEquals(set3.toString(), "{}");
	}
}
