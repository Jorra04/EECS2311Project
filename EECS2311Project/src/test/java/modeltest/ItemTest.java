/**
 * 
 */
package modeltest;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Item;

/**
 * @author Brian
 *
 */
public class ItemTest {

	private Item item;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		item = new Item("apple");
	}

	@Test
	public void testGetText() {
		assertEquals("apple", item.getText());
	}
	
	@Test
	public void testGetID() {
		String b = "apple";
		int a = b.hashCode();
		assertEquals(a, item.getID());
	}
	
	@Test
	public void testIncrementID() {
		Item item2 = new Item("bananas");
		String b = "bananas";
		int a = b.hashCode();
		assertEquals(a, item2.getID());
	}
	
	@Test
	public void testSetText() {
		item.setText("oranges");
		assertEquals("oranges", item.getText());
	}
	
	@Test
	public void testEquals() {
		Item item2 = new Item("apple");
		assertEquals(true, item.equals(item2));
	}
	
	@Test
	public void testToString() {
		String b = "apple";
		int a = b.hashCode();
		assertEquals("Item [id="+a+", text="+b+"]", item.toString());
		
	}
	
	@Test
	public void testHashCode() {
		assertNotEquals(0, item.hashCode());
	}
	
	
	
	@After
	public void tearDown() {
		//reset item unique id UID to 0.
		Item.resetUID();
	}
	
	

}
