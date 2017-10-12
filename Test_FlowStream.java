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
   * Test the constructor
   */
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    assertTrue("new FlowStream()", testStream != null);
    assertFalse("new FlowStream()", testStream == null);
  }
  
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
    
    flowSpecies.get(0).setSpeciesName("water");
    flowSpecies.get(1).setSpeciesName("ethanol");
    
    testStream.setFlowSpecies(flowSpecies);
    
    String fetchedSpeciesName1 = testStream.getFlowSpecies().get(0).getSpeciesName();
    String fetchedSpeciesName2 = testStream.getFlowSpecies().get(1).getSpeciesName();
    
    assertTrue("FlowStream.getFlowSpecies(testSpecies1)",  fetchedSpeciesName1.equals("water"));
    assertFalse("FlowStream.getFlowSpecies(testSpecies1)", fetchedSpeciesName1.equals("ethanol"));
    assertTrue("FlowStream.getFlowSpecies(testSpecies2)",  fetchedSpeciesName2.equals("ethanol"));
    assertFalse("FlowStream.getFlowSpecies(testSpecies2)", fetchedSpeciesName2.equals("water"));
    
  }
  
  /**
   * Test setting and getting the list of flowSpecies using addFlowSpecies
   */
  public void testAddFlowSpeciesGetFlowSpecies() {
    FlowStream testStream = new FlowStream();
    FlowSpecies testSpecies1 = new FlowSpecies();
    FlowSpecies testSpecies2 = new FlowSpecies();
    
    testSpecies1.setSpeciesName("water");
    testSpecies2.setSpeciesName("ethanol");
    testStream.addFlowSpecies(testSpecies1);
    testStream.addFlowSpecies(testSpecies2);
    
    String fetchedSpeciesName1 = testStream.getFlowSpecies().get(0).getSpeciesName();
    String fetchedSpeciesName2 = testStream.getFlowSpecies().get(1).getSpeciesName();
    
    assertTrue("FlowStream.getFlowSpecies(testSpecies1)",  fetchedSpeciesName1.equals("water"));
    assertFalse("FlowStream.getFlowSpecies(testSpecies1)", fetchedSpeciesName1.equals("ethanol"));
    assertTrue("FlowStream.getFlowSpecies(testSpecies2)",  fetchedSpeciesName2.equals("ethanol"));
    assertFalse("FlowStream.getFlowSpecies(testSpecies2)", fetchedSpeciesName2.equals("water"));
    
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
  
  /**
   * Test the getter and setter for the vapour fraction
   */
  public void testVapourFraction() {
    FlowStream testStream = new FlowStream();
    
    double vapourFraction = 0.5;
    assertTrue("FlowStream.setVapourFraction(0.5)", testStream.setVapourFraction(vapourFraction));
    assertFalse("FlowStream.setVapourFraction(2.0)", testStream.setVapourFraction(2.0));
    assertFalse("FlowStream.setVapourFraction(-2.0)", testStream.setVapourFraction(-2.0));
    assertEquals("FlowStream.getVapourFraction()", vapourFraction, testStream.getVapourFraction());
    
  }
  
  
}
