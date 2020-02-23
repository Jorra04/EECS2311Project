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
	public void testGroupName() {
		assertSame("test", group.getTitle());
	}
	
	@Test
	public void testGroupName2() {
		group.setTitle("newTitle");
		assertSame("newTitle", group.getTitle());
	}
	
	@Test
	public void testGroupEmpty() {
		assertTrue(group.isEmpty());	
	}
	
	@Test
	public void testGroupEmpty2() {
		Item item = new Item("item");
		group.insertItem(item);
		assertFalse(group.isEmpty());	
	}
	
	@Test
	public void testAddAndRemove() {
		Item item = new Item("item");
		group.insertItem(item);
		group.removeItem(item);
		assertTrue(group.isEmpty());
	}
	
	@Test
	public void testAddAndRemove2() {
		Item item1 = new Item("item1");
		Item item2 = new Item("item2");
		group.insertItem(item1);
		group.insertItem(item2);
		group.removeAll();
		assertTrue(group.isEmpty());
	}
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
	public void testFindMatching() {
		
	}
	
}
