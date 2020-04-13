package modeltest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.QuantityItem;
import model.Item;



public class QuantityItemTest {
	
	private QuantityItem QItem;

	@Before
	public void setUp() throws Exception {
		
		QItem = new QuantityItem("apple", 0);
	}
	
	
	@After
	public void tearDown() throws Exception {
		QItem.setCount(0);
	}
	
	@Test
	public void testGetCount() {
		assertEquals(0, QItem.getCount());
	}
	
	@Test
	public void testIncrementCount() {
		QItem.incrementCount();
		assertEquals(1, QItem.getCount());
	}
	@Test
	public void testHashCode() {
		
		assertEquals(1497870007, QItem.hashCode());
	}
	@Test
	public void testEquals() {
		
		QuantityItem QItem2 = new QuantityItem("apple", 0);
		QuantityItem QItem3 = null;
		QuantityItem QItem4 = new QuantityItem ("",0);
		QItem4.setText(null);
		
		assertEquals(QItem, QItem2);
		assertNotEquals(QItem, QItem3);
	
		assertEquals(QItem.getClass(), QItem2.getClass());
		
		assertEquals(false, QItem.equals(QItem4));
		assertEquals(false, QItem.equals(QItem3));
		assertEquals(true, QItem.equals(QItem2));
		
		QItem.setText(null);
		assertEquals(false, QItem.equals(QItem2));
		QItem2.setText(null);
		assertEquals(true, QItem.equals(QItem2));
	}
	
	
	
}
