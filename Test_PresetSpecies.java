import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_PresetSpecies extends TestCase {
  
  public void testGet() {
    List<FlowSpecies> flowSpecies = PresetSpecies.get();
    assertTrue("PresetSpecies.get()", flowSpecies.size() > 0);
    assertFalse("PresetSpecies.get()", flowSpecies.size() == 0);
  }
  
}
