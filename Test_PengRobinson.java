import junit.framework.TestCase;

public class Test_PengRobinson extends TestCase {
  
  public void testKappaI() {
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getKappa() < 0.876 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getKappa() > 0.872);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getKappa() < 1.259 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getKappa() > 1.255);
    
  }
  
  public void testAlphaI(){
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getAlpha() < 1.65 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getAlpha() > 1.63);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getAlpha() < 1.69 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getAlpha() > 1.67);
    
  }
  
  public void testAI(){
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getAI() < 0.986 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getAI() > 0.984);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getAI() < 2.296 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getAI() > 2.291); 
    
    
  }
  
  public void testBI(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualB();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getBI() < 1.91e-5 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getBI() > 1.87e-5);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getBI() < 5.44e-5 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getBI() > 5.37e-5); 
    
  }
  
  public void testAIJ(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    double[][] testResult = testPeng.aij();
    assertTrue(testResult[0][0] < 0.986 && testResult [0][0] > 0.984);
    assertTrue(testResult[1][1] < 2.296 && testResult [1][1] > 2.291);
    assertTrue(testResult[0][1] < 1.52 && testResult [0][1] > 1.50);
    
  }
  
  public void testSmallAX(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    testPeng.aij();
    testPeng.flowStreamSmallAXValue();
    System.out.println("AX value is: "+testPeng.getFlowStream().getSmallAX());
    assertTrue(testPeng.getFlowStream().getSmallAX() < 1.51 &&
               testPeng.getFlowStream().getSmallAX() > 1.50);
    
  }
  
  public void testSmallBX(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    testPeng.aij();
    testPeng.flowStreamSmallBXValue();
    System.out.println("BX value is: "+testPeng.getFlowStream().getSmallBX());
    assertTrue(testPeng.getFlowStream().getSmallBX() < 3.5e-5 &&
               testPeng.getFlowStream().getSmallBX() > 3.3e-5);
    
  }
  
  public void testLargeAX(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    testPeng.aij();
    testPeng.flowStreamSmallAXValue();
    testPeng.flowStreamLargeAXValue();
    assertTrue(testPeng.getFlowStream().getLargeAX() < 0.025 &&
               testPeng.getFlowStream().getLargeAX() > 0.024);
    
  }
  
  public void testLargeBX(){
    
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    testPeng.aij();
    testPeng.flowStreamSmallBXValue();
    testPeng.flowStreamLargeBXValue();
    System.out.println("BX IS: "+testPeng.getFlowStream().getLargeBX());
    assertTrue(testPeng.getFlowStream().getLargeBX() < 0.00145 &&
               testPeng.getFlowStream().getLargeBX() > 0.00135);
    
  }
  
  public void testCubicZSolve(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.individualB();
    testPeng.aij();
    testPeng.flowStreamSmallBXValue();
    testPeng.flowStreamLargeBXValue();
    System.out.println("BEGINNING OF TEST CUBC SOLVE");
    testPeng.solveZCubic();
    System.out.println("END OF TEST CUBC SOLVE");
    
  }
  
  private PengRobinson createTestPeng(){
    FlowStream testStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.55);
    water.setCriticalTemperature(647.0);
    water.setCriticalPressure(22055000.0);
    water.setAcentricFactor(0.345);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.45);
    ethanol.setCriticalTemperature(514.0);
    ethanol.setCriticalPressure(6148000.0);
    ethanol.setAcentricFactor(0.645);
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setTemperature(298.15);
    testStream.setPressure(101325.0);
    
    PengRobinson testPeng = new PengRobinson(testStream);
    
    return testPeng;
  }
  
}
