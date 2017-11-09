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
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
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
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    water.setCriticalTemperature(647.0);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    ethanol.setCriticalTemperature(514.0);
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(298.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double dewPoint = testDewPoint.calc();

    assertTrue("DewPoint.calc()", dewPoint > 364.25 && dewPoint < 364.28);

  }
  
  public void testNonIdealCalc(){
  
      FlowStream testStream = new FlowStream();
    
<<<<<<< HEAD
    // Test function result for water and ethanol at 1 atm, 300 K under NONIDEAL CONDITIONS!
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.75);
    water.setCriticalTemperature(647.0);
    water.setActivityCoefficient(0.953);
    water.setLargePhi(1.043);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.25);
    ethanol.setCriticalTemperature(514.0);
    ethanol.setActivityCoefficient(0.968);
    ethanol.setLargePhi(1.003);
=======
    // Test function result for water and ethanol at 1 atm, 300 K
    FlowSpecies mek = new FlowSpecies();
    mek.setOverallMoleFraction(0.75);
    mek.setCriticalTemperature(500);
    mek.setActivityCoefficient(0.953);
    mek.setLargePhi(1.043);
    mek.setAntoineConstants(new AntoineCoefficients(10., 1400., -35., 0.1, 1000.));
    
    FlowSpecies toluene = new FlowSpecies();
    toluene.setOverallMoleFraction(0.25);
    toluene.setCriticalTemperature(500);
    toluene.setActivityCoefficient(0.968);
    toluene.setLargePhi(1.003);
    toluene.setAntoineConstants(new AntoineCoefficients(9., 1300., -30., 0.1, 1000.));
    
    testStream.addFlowSpecies(mek);
    testStream.addFlowSpecies(toluene);
    testStream.setPressure(75000.0);
    testStream.setTemperature(300.);
    
    DewPoint testNonIdealDewpoint = new DewPoint(testStream);
    double dewpoint = testNonIdealDewpoint.calc();
    System.out.println("DEWPOINT IS: "+dewpoint);
    
    //Hand calcs give: (326.2, 326.9), Program calcs give: (396.8, 397.9)
    assertTrue(dewpoint > 326.2 && dewpoint < 326.9); //this should be double checked
>>>>>>> 24196806c43850a853d106156a1c38d3b6171242
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(298.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double dewPoint = testDewPoint.calc();
    
    assertTrue(dewPoint > 371.0 && dewPoint < 372.0);
    
  }
  
  // Test the DewPoint calculation with a non-condensing species included
  // Problem is example 7.21 from  CHEMICAL AND ENERGY PROCESS ENGINEERING
  // by Sigurd Skogestad, Published by CRC Press (2009)

  public void testCalcNonCondensing() {
    FlowStream testStream = new FlowStream();
    
    // All Antoine coefficients converted into Pa, K
    FlowSpecies pentane = new FlowSpecies();
    pentane.setAntoineConstants(new AntoineCoefficients(8.983576612, 1064.840, -41.136));
    pentane.setOverallMoleFraction(0.1);
    pentane.setCriticalTemperature(469.6);

    FlowSpecies hexane = new FlowSpecies();
    hexane.setAntoineConstants(new AntoineCoefficients(9.007106612, 1170.875, -48.833));
    hexane.setOverallMoleFraction(0.1);
    hexane.setCriticalTemperature(507.6);
   
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setAntoineConstants(new AntoineCoefficients(3.74192, 264.651, -6.788));
    nitrogen.setOverallMoleFraction(0.8);
    nitrogen.setCriticalTemperature(126.192);
    
    testStream.addFlowSpecies(pentane);
    testStream.addFlowSpecies(hexane);
    testStream.addFlowSpecies(nitrogen);
    testStream.setPressure(300000.0);
    testStream.setTemperature(298.0);
    
    DewPoint testDewPoint = new DewPoint(testStream);
    double dewPoint = testDewPoint.calc();
    
    assertTrue("DewPoint.calc()", dewPoint > 314.0 && dewPoint < 315.0);

  }
  
  public void testDew() {
   
    FlowSpecies pentane = new FlowSpecies();
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    FlowSpecies hexane = new FlowSpecies();
    hexane.setCriticalTemperature (507.6);
    hexane.setAntoineConstants(new AntoineCoefficients(9.00266, 1171.53, -48.784));
    hexane.setOverallMoleFraction(0.85);
    pentane.setCriticalTemperature (469.6);
    pentane.setOverallMoleFraction(0.15);
    
    FlowStream flow1 = new FlowStream();
    flow1.addFlowSpecies(pentane);
    flow1.addFlowSpecies(hexane);
    flow1.setPressure(101325.0);
    flow1.setTemperature(298.0);
    
    DewPoint pentaneDew = new DewPoint(flow1);
    double answer = pentaneDew.calc();
    System.out.println("Is "+answer);
    assertTrue("pentaneDew.calc()",answer > 338.7 && answer < 339.0);
  }
  
}