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
    double temperature = 400.0;
    
    BubblePoint testBubblePoint = new BubblePoint(testStream);
    double testFunction = testBubblePoint.testFunction(temperature);
    
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
    
    FlowStream flow1 = new FlowStream();
    flow1.addFlowSpecies(pentane);
    flow1.addFlowSpecies(hexane);
    flow1.setPressure(101325.0);
    flow1.setTemperature(298.0);
    
    BubblePoint pentaneBub = new BubblePoint(flow1);
    double answer = pentaneBub.calc();
    assertTrue("pentaneBub.calc()",answer > 334.5 && answer < 335.0);
  }
  
  // Test a non-ideal bubble point calculation
  public void testNonIdealCalc(){
    
    FlowStream testStream = new FlowStream();
    
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
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(300.);
    
    BubblePoint testBubblePoint = new BubblePoint(testStream);
    double bubblePoint = testBubblePoint.calc();
    System.out.println("BUBBLE POINT IS: "+bubblePoint);
    
    assertTrue(bubblePoint > 367.3 && bubblePoint < 367.7);
    
  }

} 
