/**
 * 
 */
package modeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Group;
import model.Item;

/**
 * @author Brian
 *
 */
public class GroupTest {
	
	private Group group;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		group = new Group("test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Item.resetUID();
	}

	@Test
	public void testInsertItem() {
		Item item = new Item("apple");
		group.insertItem(item);
		assertEquals(true, group.getItem(item.getID()).equals(item));
	}
	
	@Test
	public void testInsertItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		assertEquals(true, group.getItem(1).equals(item1));
		assertEquals(true, group.getItem(2).equals(item2));
	}
	
	@Test
	public void testRemoveItem() {
		Item item1 = new Item("apple");
		group.insertItem(item1);
		group.removeItem(item1);
		assertEquals(null, group.getItem(item1.getID()));
	}
	
	@Test
	public void testRemoveItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		group.removeItems(items);
		assertEquals(0, group.getSize());
	}
	
	@Test
	public void testRemoveAll() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		group.removeAll();
		assertEquals(0, group.getSize());
	}
	
	@Test
	public void testGetTitle() {
		assertEquals("test", group.getTitle());
	}
	
	@Test
	public void testSetTitle() {
		group.setTitle("pineapple on pizza");
		assertEquals("pineapple on pizza", group.getTitle());
	}
	
	@Test
	public void testIsEmpty() {
		//negative test
		group.insertItem(new Item("steak"));
		assertEquals(false, group.isEmpty());
	}
	
	@Test
	public void testFindMatching() {
		
	}
	
}
