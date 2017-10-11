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
    
    assertTrue("new DewPoint(testStream)", testDewPoint != null);
    assertFalse("new DewPoint(testStream)", testDewPoint == null);
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

    assertTrue("DewPoint.testFunction()", testFunction > -0.700 && testFunction < -0.690);

  }
  
  // Test the DewPoint calculation
  public void testCalc() {
    FlowStream testStream = new FlowStream();
    
    // Test function result for water and ethanol at 1 atm, 300 K
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(10.19621302, 1730.63, -39.724);
    water.setOverallMoleFraction(0.5);
    water.setCriticalTemperature(647.0);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(9.80607302, 1332.04, -73.95);
    ethanol.setOverallMoleFraction(0.5);
    ethanol.setCriticalTemperature(514.0);
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double dewPoint = testDewPoint.calc();

    assertTrue("DewPoint.calc()", dewPoint > 364.25 && dewPoint < 364.28);

  }
  
  // Test the DewPoint calculation with a non-condensing species included
  // Problem is example 7.21 from  CHEMICAL AND ENERGY PROCESS ENGINEERING
  // by Sigurd Skogestad, Published by CRC Press (2009)

  public void testCalcNonCondensing() {
    FlowStream testStream = new FlowStream();
    
    // All Antoine coefficients converted into Pa, K
    FlowSpecies pentane = new FlowSpecies();
    pentane.setAntoineConstants(8.983576612, 1064.840, -41.136);
    pentane.setOverallMoleFraction(0.1);
    pentane.setCriticalTemperature(469.6);

    FlowSpecies hexane = new FlowSpecies();
    hexane.setAntoineConstants(9.007106612, 1170.875, -48.833);
    hexane.setOverallMoleFraction(0.1);
    hexane.setCriticalTemperature(507.6);
   
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setAntoineConstants(3.74192, 264.651, -6.788);
    nitrogen.setOverallMoleFraction(0.8);
    nitrogen.setCriticalTemperature(126.192);
    
    testStream.addFlowSpecies(pentane);
    testStream.addFlowSpecies(hexane);
    testStream.addFlowSpecies(nitrogen);
    testStream.setPressure(300000.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double dewPoint = testDewPoint.calc();
    
    assertTrue("DewPoint.calc()", dewPoint > 314.0 && dewPoint < 315.0);

  }
  
}