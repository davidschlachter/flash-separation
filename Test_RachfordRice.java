import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_RachfordRice extends TestCase {

  // Test the constructor
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    
    // Rachford-Rice object requires at least one species, and a specified pressure and temperature for the stream
    testStream.addFlowSpecies(new FlowSpecies());
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    
    assertEquals("new RachfordRice(testStream)", true,  testRachfordRice != null);
    assertEquals("new RachfordRice(testStream)", false, testRachfordRice == null);
  }
  
  // Test the testFunction
  public void testTestFunction() {
    FlowStream testStream = new FlowStream();
    
    // Test function result for water and ethanol at 1 atm, 368 K
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(10.19621302, 1730.63, -39.724);
    water.setOverallMoleFraction(0.5);
    testStream.setTemperature(368.0);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(9.80607302, 1332.04, -73.95);
    ethanol.setOverallMoleFraction(0.5);
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    // Test for a guessed V/F of 0.5
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    double testFunction = testRachfordRice.testFunction(0.5);
    
    assertEquals("RachfordRice.testFunction()", true,  testFunction > 0.207 && testFunction < 0.209);

  }
  
  // Test the RachfordRice solution for a stream
  public void testSolution() {
    FlowStream testStream = new FlowStream();
    
    // Test function result for water and ethanol at 1 atm, 300 K
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(10.19621302, 1730.63, -39.724);
    water.setOverallMoleFraction(0.5);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(9.80607302, 1332.04, -73.95);
    ethanol.setOverallMoleFraction(0.5);
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    
    FlowStream solvedFlowStream = testRachfordRice.solve();

    double waterLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getLiquidMoleFraction();
    double waterVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getVapourMoleFraction();
    
    assertTrue("RachfordRice.calc()", waterLiquidMoleFraction > 0.8338 && waterLiquidMoleFraction < 0.8358);
    assertTrue("RachfordRice.calc()", waterVapourMoleFraction > 0.6912 && waterVapourMoleFraction < 0.6932);
    assertTrue("RachfordRice.calc()", ethanolLiquidMoleFraction > 0.1642 && ethanolLiquidMoleFraction < 0.1662);
    assertTrue("RachfordRice.calc()", ethanolVapourMoleFraction > 0.3068 && ethanolVapourMoleFraction < 0.3088);

  }
  
}