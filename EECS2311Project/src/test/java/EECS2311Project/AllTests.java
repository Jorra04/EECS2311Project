package EECS2311Project;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import guitest.GUITest;
import modeltest.GroupTest;
import modeltest.ItemTest;
import modeltest.ModelTest;

@RunWith(Suite.class)
@SuiteClasses({GUITest.class, ModelTest.class, GroupTest.class, ItemTest.class})
public class AllTests {

}
