import junit.framework.TestCase;

public class Test_PengRobinson extends TestCase {
  
  public void testKappaI() {
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getKappa() < 0.7425 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getKappa() > 0.7415);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getKappa() < 0.8225 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getKappa() > 0.7975);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getKappa() < 0.8850 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getKappa() > 0.8750);
    
  }
  
  public void testAlphaI(){
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getAlpha() < 1.273 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getAlpha() > 1.267);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getAlpha() < 1.363 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getAlpha() > 1.357);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getAlpha() < 1.443 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getAlpha() > 1.437);
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
    assertTrue(testPeng.getFlowStream().getLargeBX() < 0.00145 &&
               testPeng.getFlowStream().getLargeBX() > 0.00135);
    
  }
  
  private PengRobinson createTestPeng(){
    FlowStream testStream = new FlowStream();
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setCriticalPressure(3369000.0);
    pentane.setCriticalTemperature(469.7);
    pentane.setAcentricFactor(0.249);
    FlowSpecies hexane = new FlowSpecies();
    hexane.setCriticalPressure(3012000.0);
    hexane.setCriticalTemperature(507.4);
    hexane.setAcentricFactor(0.305);
    FlowSpecies heptane = new FlowSpecies();
    heptane.setCriticalPressure(2736000.0);
    heptane.setCriticalTemperature(540.3);
    heptane.setAcentricFactor(0.349);
    testStream.addFlowSpecies(pentane);
    testStream.addFlowSpecies(hexane);
    testStream.addFlowSpecies(heptane);
    testStream.setTemperature(322.29);
    testStream.setPressure(101325.0);
    
    PengRobinson testPeng = new PengRobinson(testStream);
    
    return testPeng;
  }
  
}
