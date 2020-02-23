/**
 * 
 */
package modeltest;

import static org.junit.Assert.*;

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

}
