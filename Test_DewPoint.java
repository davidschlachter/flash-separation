import junit.framework.TestCase;

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
  
  // Test that the testFunction returns expected values for a given input
  public void testTestFunction() {
    FlowStream testStream = new FlowStream();
    
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
  
  // Test an ideal dew point calculation. Source is a manual calculation in Excel
  public void testCalc() {
    FlowStream testStream = new FlowStream();
    
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
  
  // Test an ideal dew point calculation. Source is a manual calculation in Excel
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
    assertTrue("pentaneDew.calc()",answer > 338.7 && answer < 339.0);
  }

  
  // Test the an ideal dew point calculation with a non-condensing species included
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
    // Test that the mole fraction of nitrogen wasn't changed in the source FlowStream
    // (This used to be an issue!)
    assertTrue("testStream.getFlowSpecies(2).getOverallMoleFraction()", testStream.getFlowSpecies().get(2).getOverallMoleFraction() > 0.7999 && testStream.getFlowSpecies().get(2).getOverallMoleFraction() < 0.8001);
    
  }
  
  // Test the ideal dew point against this example from LearnChemE:
  // https://www.youtube.com/watch?v=_26D3TDKH8Y
  public void testIdealLearnChemEDewPoint() {
    FlowSpecies component1 = new FlowSpecies();
    FlowSpecies component2 = new FlowSpecies();
    // Antoine coefficients converted with http://www.envmodels.com/freetools.php?menu=antoine
    component1.setAntoineConstants(new AntoineCoefficients(9.01762, 1203.531, -53.262));
    component2.setAntoineConstants(new AntoineCoefficients(9.08295, 1346.773, -53.457));
    // So that calculations work!
    component1.setCriticalTemperature(1000.0);
    component2.setCriticalTemperature(1000.0);
    component1.setOverallMoleFraction(0.65);
    component2.setOverallMoleFraction(0.35);
    
    FlowStream test = new FlowStream();
    test.addFlowSpecies(component1);
    test.addFlowSpecies(component2);
    test.setPressure(19998.35526);
    test.setTemperature(298.15); // Required for dew point intial guess
    test.setMolarFlowRate(1.0);
    
    double dewPoint = new DewPoint(test).calc();

    assertTrue(dewPoint > 48.21+273.15 && dewPoint < 48.23+273.15);
  }
  
}