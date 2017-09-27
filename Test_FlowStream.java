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
    
    assertEquals("FlowStream.addFlowSpecies(testSpecies)", true, testStream.addFlowSpecies(testSpecies));
    
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
    
    assertEquals("FlowStream.getFlowSpecies(testSpecies1)", true,  fetchedHeatOfVapourization1 == 1.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies1)", false, fetchedHeatOfVapourization1 == 0.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies2)", true,  fetchedHeatOfVapourization2 == 2.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies2)", false, fetchedHeatOfVapourization2 == 0.0);
    
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
    
    assertEquals("FlowStream.getFlowSpecies(testSpecies1)", true,  fetchedHeatOfVapourization1 == 1.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies1)", false, fetchedHeatOfVapourization1 == 0.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies2)", true,  fetchedHeatOfVapourization2 == 2.0);
    assertEquals("FlowStream.getFlowSpecies(testSpecies2)", false, fetchedHeatOfVapourization2 == 0.0);
    
  }
  
  /**
   * Test getting the number of species in the stream
   */
  public void testGetNumberOfSpecies() {
    FlowStream testStream = new FlowStream();
    FlowSpecies testSpecies = new FlowSpecies();
    testStream.addFlowSpecies(testSpecies);
    
    assertEquals("FlowStream.getNumberOfSpecies()", 1, testStream.getNumberOfSpecies());
    assertEquals("FlowStream.getNumberOfSpecies()", false, testStream.getNumberOfSpecies() == 0);
  }
  
  /**
   * Test set/getMolarFlowRate
   */
  public void testMolarFlowRate() {
    FlowStream testStream = new FlowStream();
    testStream.setMolarFlowRate(1.0);
    
    assertEquals("FlowStream.getMolarFlowRate()", 1.0,   testStream.getMolarFlowRate());
    assertEquals("FlowStream.getMolarFlowRate()", false, testStream.getMolarFlowRate() == 0.0);
  }
  
  /**
   * Test set/getTemperature
   */
  public void testTemperature() {
    FlowStream testStream = new FlowStream();
    testStream.setTemperature(1.0);
    
    assertEquals("FlowStream.getTemperature()", 1.0,   testStream.getTemperature());
    assertEquals("FlowStream.getTemperature()", false, testStream.getTemperature() == 0.0);
  }
  
  /**
   * Test set/getPressure
   */
  public void testPressure() {
    FlowStream testStream = new FlowStream();
    testStream.setPressure(1.0);
    
    assertEquals("FlowStream.getPressure()", 1.0,   testStream.getPressure());
    assertEquals("FlowStream.getPressure()", false, testStream.getPressure() == 0.0);
  }
  
  
}
