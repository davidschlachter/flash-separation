import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_FlowStream extends TestCase {
  
  /**
   * Test adding a flowSpecies to the flowStream
   */
  public void testAddFlowSpecies() {
    FlowStream testStream = new FlowStream();
    FlowSpecies testSpecies = new FlowSpecies();
    
    assertTrue("FlowStream.addFlowSpecies(testSpecies)", testStream.addFlowSpecies(testSpecies));
    
  }
  
  /**
   * Test setting and getting the list of flowSpecies in flowStream directly
   */
  public void testSetFlowSpeciesGetFlowSpecies() {
    FlowStream testStream = new FlowStream();
    
    List<FlowSpecies> flowSpecies = new ArrayList<FlowSpecies>();
    flowSpecies.add(new FlowSpecies());
    flowSpecies.add(new FlowSpecies());
    
    flowSpecies.get(0).setHeatOfVapourization(1.0);
    flowSpecies.get(1).setHeatOfVapourization(2.0);
    
    testStream.setFlowSpecies(flowSpecies);
    
    double fetchedHeatOfVapourization1 = testStream.getFlowSpecies().get(0).getHeatOfVapourization();
    double fetchedHeatOfVapourization2 = testStream.getFlowSpecies().get(1).getHeatOfVapourization();
    
    assertTrue("FlowStream.getFlowSpecies(testSpecies1)", fetchedHeatOfVapourization1 == 1.0);
    assertFalse("FlowStream.getFlowSpecies(testSpecies1)", fetchedHeatOfVapourization1 == 0.0);
    assertTrue("FlowStream.getFlowSpecies(testSpecies2)", fetchedHeatOfVapourization2 == 2.0);
    assertFalse("FlowStream.getFlowSpecies(testSpecies2)", fetchedHeatOfVapourization2 == 0.0);
    
  }
  
  /**
   * Test setting and getting the list of flowSpecies using addFlowSpecies
   */
  public void testAddFlowSpeciesGetFlowSpecies() {
    FlowStream testStream = new FlowStream();
    FlowSpecies testSpecies1 = new FlowSpecies();
    FlowSpecies testSpecies2 = new FlowSpecies();
    
    testSpecies1.setHeatOfVapourization(1.0);
    testSpecies2.setHeatOfVapourization(2.0);
    testStream.addFlowSpecies(testSpecies1);
    testStream.addFlowSpecies(testSpecies2);
    
    double fetchedHeatOfVapourization1 = testStream.getFlowSpecies().get(0).getHeatOfVapourization();
    double fetchedHeatOfVapourization2 = testStream.getFlowSpecies().get(1).getHeatOfVapourization();
    
    assertTrue("FlowStream.getFlowSpecies(testSpecies1)", fetchedHeatOfVapourization1 == 1.0);
    assertFalse("FlowStream.getFlowSpecies(testSpecies1)", fetchedHeatOfVapourization1 == 0.0);
    assertTrue("FlowStream.getFlowSpecies(testSpecies2)", fetchedHeatOfVapourization2 == 2.0);
    assertFalse("FlowStream.getFlowSpecies(testSpecies2)", fetchedHeatOfVapourization2 == 0.0);
    
  }
  
  /**
   * Test getting the number of species in the stream
   */
  public void testGetNumberOfSpecies() {
    FlowStream testStream = new FlowStream();
    FlowSpecies testSpecies = new FlowSpecies();
    testStream.addFlowSpecies(testSpecies);
    
    assertEquals("FlowStream.getNumberOfSpecies()", 1, testStream.getNumberOfSpecies());
    assertFalse("FlowStream.getNumberOfSpecies()", testStream.getNumberOfSpecies() == 0);
  }
  
  /**
   * Test set/getMolarFlowRate
   */
  public void testMolarFlowRate() {
    FlowStream testStream = new FlowStream();
    testStream.setMolarFlowRate(1.0);
    
    assertEquals("FlowStream.getMolarFlowRate()", 1.0,  testStream.getMolarFlowRate());
    assertFalse("FlowStream.getMolarFlowRate()", testStream.getMolarFlowRate() == 0.0);
  }
  
  /**
   * Test set/getTemperature
   */
  public void testTemperature() {
    FlowStream testStream = new FlowStream();
    testStream.setTemperature(1.0);
    
    assertEquals("FlowStream.getTemperature()", 1.0,  testStream.getTemperature());
    assertFalse("FlowStream.getTemperature()", testStream.getTemperature() == 0.0);
  }
  
  /**
   * Test set/getPressure
   */
  public void testPressure() {
    FlowStream testStream = new FlowStream();
    testStream.setPressure(1.0);
    
    assertEquals("FlowStream.getPressure()", 1.0,  testStream.getPressure());
    assertFalse("FlowStream.getPressure()", testStream.getPressure() == 0.0);
  }
  
  
}
