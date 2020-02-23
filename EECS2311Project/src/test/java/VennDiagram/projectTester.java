package VennDiagram;


import static org.junit.Assert.*;


import java.util.*;
import org.junit.Test;
import controller.Controller;
import model.Group;
import model.Item;
import model.VennModel;
public class projectTester {

	
	@Test
	public void groupTest1() {
		groupNameTest();
		groupNameTest2();
		groupEmptyTest();
		groupEmptyTest2();
		addAndRemoveTest();
		addAndRemoveTest2();
	}
	
	@Test
	public void itemTest1() {
		
	}

	
	
	private void groupNameTest() {
		Group group = new Group("name");
		assertSame("name", group.getTitle());
	}
	
	private void groupNameTest2() {
		Group group = new Group("name");
		group.setTitle("newTitle");
		assertSame("newTitle", group.getTitle());
	}
	
	private void groupEmptyTest() {
		Group group = new Group("name");
		assertTrue(group.isEmpty());	
	}
	
	private void groupEmptyTest2() {
		Group group = new Group("group");
		Item item = new Item("item");
		group.insertItem(item);
		assertFalse(group.isEmpty());	
	}
	public void addAndRemoveTest() {
		Group group = new Group("group");
		Item item = new Item("item");
		group.insertItem(item);
		group.removeItem(item);
		assertTrue(group.isEmpty());
	}
	public void addAndRemoveTest2() {
		Group group = new Group("group");
		Item item1 = new Item("item1");
		Item item2 = new Item("item2");
		group.insertItem(item1);
		group.insertItem(item2);
		group.removeAll();
		assertTrue(group.isEmpty());
	}
	
	


	

}
