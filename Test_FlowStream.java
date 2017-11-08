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
  
  /**
   * Test the approximately equals method
   */
  public void testApproxEquals() {
    
    FlowStream firstStream = new FlowStream();
    FlowStream secondStream = new FlowStream();
    FlowStream thirdStream = new FlowStream();
    
    // Define the first stream
    FlowSpecies ethane = new FlowSpecies();
    ethane.setSpeciesName("Ethane");
    ethane.setVapourHeatCapacityConstants(1.131, 19.225, -5.561, 0.0);
    ethane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    ethane.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    ethane.setAntoineConstants(new AntoineCoefficients(8.9440666, 659.739, -16.719));
    ethane.setCriticalTemperature (305.3);
    ethane.setOverallMoleFraction(0.5);
    ethane.setVapourMoleFraction(0.5);
    ethane.setLiquidMoleFraction(0.5);
    FlowSpecies pentane = new FlowSpecies();
    pentane.setSpeciesName("Pentane");
    pentane.setVapourHeatCapacityConstants(2.464, 45.351, 14.111, 0.0);
    pentane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    pentane.setCriticalTemperature (469.6);
    pentane.setOverallMoleFraction(0.5);
    pentane.setVapourMoleFraction(0.5);
    pentane.setLiquidMoleFraction(0.5);
    firstStream.addFlowSpecies(pentane);
    firstStream.addFlowSpecies(ethane);
    firstStream.setMolarFlowRate(10.0);
    firstStream.setTemperature(300.0);
    firstStream.setPressure(101325.0);
    firstStream.setVapourFraction(0.7);
    
    // Define the second stream
    // This stream has several properties changed from the first stream, marked with comments,
    // all within the error tolerance of +/- 0.001 (default tolerance)
    FlowSpecies ethaneTwo = new FlowSpecies();
    ethaneTwo.setSpeciesName("Ethane");
    ethaneTwo.setVapourHeatCapacityConstants(1.131, 19.225, -5.561, 0.0);
    ethaneTwo.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    ethaneTwo.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    ethaneTwo.setAntoineConstants(new AntoineCoefficients(8.9440666, 659.739, -16.719));
    ethaneTwo.setCriticalTemperature (305.3);
    ethaneTwo.setOverallMoleFraction(0.50045); // +0.0009
    ethaneTwo.setVapourMoleFraction(0.50045); // +0.0009
    ethaneTwo.setLiquidMoleFraction(0.50045); // +0.0009
    FlowSpecies pentaneTwo = new FlowSpecies();
    pentaneTwo.setSpeciesName("Pentane");
    pentaneTwo.setVapourHeatCapacityConstants(2.464, 45.351, 14.111, 0.0);
    pentaneTwo.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentaneTwo.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    pentaneTwo.setCriticalTemperature (469.6);
    pentaneTwo.setOverallMoleFraction(0.49955); // -0.0009
    pentaneTwo.setVapourMoleFraction(0.49955); // -0.0009
    pentaneTwo.setLiquidMoleFraction(0.49955); // -0.0009
    secondStream.addFlowSpecies(pentaneTwo);
    secondStream.addFlowSpecies(ethaneTwo);
    secondStream.setMolarFlowRate(10.009); // +0.0009
    secondStream.setTemperature(300.27); // +0.0009
    secondStream.setPressure(101325.0);
    secondStream.setVapourFraction(0.7);
    
    // The third stream has several properties changed from the first in a much larger
    // magnitude than the default tolerance
    FlowSpecies ethaneThree = new FlowSpecies();
    ethaneThree.setSpeciesName("Ethane");
    ethaneThree.setVapourHeatCapacityConstants(1.0, 19.0, -5.0, 0.0);  // Smaller
    ethaneThree.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    ethaneThree.setAntoineConstants(new AntoineCoefficients(8.0, 659.0, -16.0)); // Smaller
    ethaneThree.setCriticalTemperature (340.0); // Bigger
    ethaneThree.setOverallMoleFraction(0.2); // Smaller
    ethaneThree.setVapourMoleFraction(0.2); // Smaller
    ethaneThree.setLiquidMoleFraction(0.2); // Smaller
    FlowSpecies pentaneThree = new FlowSpecies();
    pentaneThree.setSpeciesName("Pentane");
    pentaneThree.setVapourHeatCapacityConstants(2.464, 45.351, 14.111, 0.0);
    pentaneThree.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentaneThree.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    pentaneThree.setCriticalTemperature (469.6);
    pentaneThree.setOverallMoleFraction(1.0); // Bigger
    pentaneThree.setVapourMoleFraction(1.0); // Bigger
    pentaneThree.setLiquidMoleFraction(1.0); // Bigger
    thirdStream.addFlowSpecies(pentaneThree);
    thirdStream.addFlowSpecies(ethaneThree);
    thirdStream.setMolarFlowRate(10.1); // Bigger
    thirdStream.setTemperature(303.0); // Bigger
    thirdStream.setPressure(90000.0); // Smaller
    thirdStream.setVapourFraction(0.6); // Smaller
    
    double error = 0.001;
    
    assertTrue("firstStream.approxEquals(firstStream)", firstStream.approxEquals(firstStream, error));
    assertTrue("firstStream.approxEquals(secondStream)", firstStream.approxEquals(secondStream, error));
    assertFalse("firstStream.approxEquals(thirdStream)", firstStream.approxEquals(thirdStream, error));
    
  }
  
  /**
   * Test the constructor
   */
  public void testClone() {
    
    double error = 0.001;

    FlowStream firstStream = new FlowStream();
    firstStream.setFlowSpecies(PresetSpecies.get());
    firstStream.setMolarFlowRate(10.0);
    firstStream.setTemperature(300.0);
    firstStream.setPressure(100000.0);
    firstStream.setVapourFraction(0.5);
    
    FlowStream secondStream = firstStream.clone();
    
    // Test with approximately equals first!
    assertTrue("firstStream.approxEquals(firstStream)", firstStream.approxEquals(firstStream, error));
    assertTrue("secondStream.approxEquals(firstStream)", secondStream.approxEquals(firstStream, error));
    
    // Check that the properties were all copied over
    assertTrue("Copy constructor: molar flow rates equal", firstStream.getMolarFlowRate() == secondStream.getMolarFlowRate());
    assertTrue("Copy constructor: temperatures equal", firstStream.getTemperature() == secondStream.getTemperature());
    assertTrue("Copy constructor: pressures equal", firstStream.getPressure() == secondStream.getPressure());
    assertTrue("Copy constructor: vapour fractions equal", firstStream.getVapourFraction() == secondStream.getVapourFraction());
    // TODO: Check that species list is the same for both!
    
    // Check that the objects are independent
    firstStream.setPressure(200000.0);
    assertTrue("Copy constructor: new pressures not equal", firstStream.getPressure() != secondStream.getPressure());
    
    // Check that the copy was deep
    assertTrue("Copy constructor: deep copy", firstStream.getFlowSpecies().get(1).getCriticalTemperature() == secondStream.getFlowSpecies().get(1).getCriticalTemperature());
    firstStream.getFlowSpecies().get(1).setCriticalTemperature(400.0);
    assertFalse("Copy constructor: deep copy", firstStream.getFlowSpecies().get(1).getCriticalTemperature() == secondStream.getFlowSpecies().get(1).getCriticalTemperature());
    
  }
  
  
  
}
