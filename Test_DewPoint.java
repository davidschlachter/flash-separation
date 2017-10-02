import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_DewPoint extends TestCase {

  // Test the constructor
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    
    // Dew point requires at least one species, and a specified pressure
    testStream.addFlowSpecies(new FlowSpecies());
    testStream.setPressure(101325.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    
    assertEquals("new DewPoint(testStream)", true,  testDewPoint != null);
    assertEquals("new DewPoint(testStream)", false, testDewPoint == null);
  }
  
  // Test the testFunction
  public void testTestFunction() {
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
    double temperature = 400.0;
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double testFunction = testDewPoint.testFunction(temperature);
    System.out.println(testFunction);
    assertEquals("DewPoint.testFunction()", true,  testFunction > -0.700 && testFunction < -0.690);

  }
  
  // Test the DewPoint calculation
  public void testCalc() {
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
    
    DewPoint testDewPoint = new DewPoint(testStream);
<<<<<<< HEAD
    //double dewPoint = testDewPoint.calc();
    System.out.println(dewPoint);
=======
    double dewPoint = testDewPoint.calc();
<<<<<<< HEAD
>>>>>>> b19404eaa1d8f9945962f3cd2e775eb0cd1681ee
    
    assertEquals("DewPoint.calc()", true,  dewPoint > 359.9 && dewPoint < 360.0);
=======

    assertEquals("DewPoint.calc()", true,  dewPoint > 364.25 && dewPoint < 364.28);
>>>>>>> 500acc429d2dbdf1ff17012896d9a0a738d6352c

  }
  
}
