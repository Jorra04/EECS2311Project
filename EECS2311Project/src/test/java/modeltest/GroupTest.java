/**
 * 
 */
package modeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

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
		assertEquals(true, group.getItem(item1.getID()).equals(item1));
		assertEquals(true, group.getItem(item2.getID()).equals(item2));
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
	public void testGetItems() {
		Item item1 = new Item("item1");
		group.insertItem(item1);
		
		TreeMap<Integer, Item> a = new TreeMap<Integer ,Item >();
		a.put(Integer.valueOf(item1.getID()), item1);
		assertEquals(true, group.getItems().equals(a));		
	}
	
	@Test
	public void testContains() {
	
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		Item item3 = new Item("banana");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		
		
		assertEquals(true, group.contains(item2));
		assertEquals(false, group.contains(item3));	
	}
	@Test
	public void testToVisualList() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		
		assertEquals(group.toVisualList(), "apple" + "\n" + "mango"+ "\n");		
		
	}
	@Test
	public void testToSet() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		
		String arr [] = {"apple", "mango"};
		Set<String > a = new HashSet<String>(Arrays.asList(arr)); 
		
		
		assertEquals(true, group.toSet().equals(a));
		
	}
	@Test
	public void testFindMatching() {
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("apple");
		Item item2 = new Item("mango");
		Item item3 = new Item("banana");
		items.add(item1);
		items.add(item2);
		group.insertItems(items);
		
		Group group2 = new Group("test2");
		items.add(item3);
		group2.insertItems(items);
		
		
		Integer arr [] = {item1.getID(), item2.getID()};
		Set<Integer > a = new HashSet<Integer>(Arrays.asList(arr)); 
		
		assertEquals(true, group.findMatching(group2).equals(a));
	}
	
	
}
