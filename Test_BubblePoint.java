import junit.framework.TestCase;

public class Test_BubblePoint extends TestCase {
  
  // Test the constructor
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    
    // Bubble point requires at least one species, and a specified pressure
    testStream.addFlowSpecies(new FlowSpecies());
    testStream.setPressure(101325.0);
    
    BubblePoint testBubblePoint = new BubblePoint(testStream);
    
    assertTrue("new BubblePoint(testStream)", testBubblePoint != null);
    assertFalse("new BubblePoint(testStream)", testBubblePoint == null);
  }
  
  // Test that the testFunction returns expected values
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
    
    BubblePoint testBubblePoint = new BubblePoint(testStream);
    double testFunction = 0.0;
    double T = 400.0;
    testFunction = testBubblePoint.testFunction(T);
    
    assertTrue("BubblePoint.testFunction()", testFunction > 2.80 && testFunction < 2.82);
  }
  
  // Test an ideal bubble point calculation. Source is a manual calculation in Excel.
  public void testCalc() {
    FlowStream testStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    water.setCriticalTemperature(647.0);
    ethanol.setCriticalTemperature(514.0);
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(298.0);
    
    BubblePoint testBubblePoint = new BubblePoint(testStream);
    double dewPoint = testBubblePoint.calc();
    
    assertTrue("BubblePoint.calc()", dewPoint > 359.9 && dewPoint < 360.0);
  }
  
  // Test an ideal bubble point calculation. Source is a manual calculation in Excel.
  public void testBubPoint() {
    FlowSpecies pentane = new FlowSpecies();
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    FlowSpecies hexane = new FlowSpecies();
    hexane.setAntoineConstants(new AntoineCoefficients(9.00266, 1171.53, -48.784));
    hexane.setOverallMoleFraction(0.85);
    pentane.setOverallMoleFraction(0.15);
    hexane.setCriticalTemperature(507.6);
    pentane.setCriticalTemperature(469.6);
    FlowStream flow1 = new FlowStream();
    flow1.addFlowSpecies(pentane);
    flow1.addFlowSpecies(hexane);
    flow1.setPressure(101325.0);
    flow1.setTemperature(298.0);
    
    BubblePoint pentaneBub = new BubblePoint(flow1);
    double answer = pentaneBub.calc();
    assertTrue("pentaneBub.calc()",answer > 334.5 && answer < 335.0);
  }
  
  // Test the ideal bubble point against this example from LearnChemE:
  // https://www.youtube.com/watch?v=0nOPZQHPpyk
  public void testIdealLearnChemEBubblePoint() {
    FlowSpecies component1 = new FlowSpecies();
    FlowSpecies component2 = new FlowSpecies();
    // Antoine coefficients converted with http://www.envmodels.com/freetools.php?menu=antoine
    component1.setAntoineConstants(new AntoineCoefficients(9.21041, 1278.99725, -49.15));
    component2.setAntoineConstants(new AntoineCoefficients(9.16698, 1278.12866, -64.15));
    component1.setCriticalTemperature(1000.0);
    component2.setCriticalTemperature(1000.0);
    component1.setOverallMoleFraction(0.40);
    component2.setOverallMoleFraction(0.60);
    
    FlowStream test = new FlowStream();
    test.addFlowSpecies(component1);
    test.addFlowSpecies(component2);
    test.setPressure(85*1000);
    test.setTemperature(298.15); // Required for bubble point intial guess
    test.setMolarFlowRate(1.0);
    
    double bubblePoint = new BubblePoint(test).calc();
    
    assertTrue(bubblePoint > 84.3+273.15 && bubblePoint < 84.5+273.15);
  }

} 