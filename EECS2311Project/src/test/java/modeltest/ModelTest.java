/**
 * 
 */
package modeltest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import model.VennModel;
import model.Group;
import model.Item;

/**
 * @author Brian
 *
 */
public class ModelTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	
	VennModel vennModel = new VennModel();
	} 
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void titleTest() {
		Group group = new Group("my group");
		assertEquals("my group",group.getTitle());
	}
	@Test
	public void setTitleTest() {
		Group group = new Group("my group");
		group.setTitle("title 2");
		assertEquals("title 2",group.getTitle());
	}
	@Test
	public void contain() {
		
	}

}
