import java.util.List;
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
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getAI() < 2630800.0 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getAI() > 2630000.0);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getAI() < 3677000.0 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getAI() > 3675000.0); 
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getAI() < 4859500.0 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getAI() > 4858000.0);
    
    
  }
  
  public void testBI(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualB();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getBI() < 90.3 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getBI() > 89.9);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getBI() < 109.5 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getBI() > 108.7);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getBI() < 128.0 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getBI() > 127.2);
    
  }
  
  public void testSpeciesA(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA(); 
    testPeng.speciesA();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getSpeciesA() < 0.038 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getSpeciesA() > 0.036);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getSpeciesA() < 0.06 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getSpeciesA() > 0.045);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getSpeciesA() < 0.078 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getSpeciesA() > 0.062);
    
  }
  
  public void testSpeciesB(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualB(); 
    testPeng.speciesB();
    
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(0).getSpeciesB() < 0.0035 && 
               testPeng.getFlowStream().getFlowSpecies().get(0).getSpeciesB() > 0.00335);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(1).getSpeciesB() < 0.0042 && 
               testPeng.getFlowStream().getFlowSpecies().get(1).getSpeciesB() > 0.00405);
    assertTrue(testPeng.getFlowStream().getFlowSpecies().get(2).getSpeciesB() < 0.00495 && 
               testPeng.getFlowStream().getFlowSpecies().get(2).getSpeciesB() > 0.00475);
    
  }
  
  public void testAIJ(){
    
    PengRobinson testPeng = createTestPeng();
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.speciesA();
    double[][] testResult = testPeng.aij();
    
    assertTrue(testResult[0][0] < 0.038 && testResult [0][0] > 0.0365);
    assertTrue(testResult[0][1] < 0.0445 && testResult [0][1] > 0.0435);
    assertTrue(testResult[0][2] < 0.055 && testResult [0][2] > 0.045);
    assertTrue(testResult[1][0] < 0.0445 && testResult [0][1] > 0.0435);
    assertTrue(testResult[1][1] < 0.054 && testResult [1][1] > 0.048);
    assertTrue(testResult[1][2] < 0.0625 && testResult [1][2] > 0.0575);
    assertTrue(testResult[2][0] < 0.055 && testResult [0][2] > 0.045);
    assertTrue(testResult[2][1] < 0.0625 && testResult [1][2] > 0.0575);
    assertTrue(testResult[2][2] < 0.06925 && testResult [2][2] > 0.06825);
    
  }
  
  public void testStreamAX(){
    
     List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.072257);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.927743);
    inletStream.setTemperature(254.);
    inletStream.setPressure(100000.0);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.speciesA();
    testPeng.streamAX();
    assertTrue(testPeng.getFlowStream().getStreamAX() < 0.0617 && testPeng.getFlowStream().getStreamAX() > 0.0612);
    
  }
  
    public void testStreamAY(){
    
     List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.905077);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.094923);
    inletStream.setTemperature(254.);
    inletStream.setPressure(100000.0);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualA();
    testPeng.speciesA();
    testPeng.streamAY();
    assertTrue(testPeng.getFlowStream().getStreamAY() < 0.0185 && testPeng.getFlowStream().getStreamAY() > 0.0175);
    
  }
    
      public void testStreamBX(){
    
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.072257);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.927743);
    inletStream.setTemperature(254.);
    inletStream.setPressure(100000.0);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualB();
    testPeng.speciesB();
    testPeng.streamBX();
    assertTrue(testPeng.getFlowStream().getStreamBX() < 0.0042 && testPeng.getFlowStream().getStreamBX() > 0.0040);
    
  }
  
  public void testStreamBY(){
    
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.905077);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.094923);
    inletStream.setTemperature(254.);
    inletStream.setPressure(100000.0);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.kappaI();
    testPeng.alphaI();
    testPeng.individualB();
    testPeng.speciesB();
    testPeng.streamBY();
    assertTrue(testPeng.getFlowStream().getStreamBY() < 0.00221 && testPeng.getFlowStream().getStreamBY() > 0.001975);
    
  }
  
  public void testSolveVapourFugacities(){
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.0721212);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.9065648);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.9278788);
    inletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.0934352);
    
    inletStream.setIsIdeal(false);
    inletStream.setMolarFlowRate(1.0);
    inletStream.setPressure(100000.0);
    inletStream.setTemperature(254.0);
    inletStream.setVapourFraction(0.75245204);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.nonIdealCalcs();
    
    double ethaneVap = testPeng.getFlowStream().getFlowSpecies().get(0).getVapourFugacity();
    double pentaneVap = testPeng.getFlowStream().getFlowSpecies().get(1).getVapourFugacity();
    
    System.out.println("ethane vapour fugacity: "+ethaneVap);
    System.out.println("pentane vapour fugacity: "+pentaneVap);
    
    assertTrue(Math.abs(ethaneVap  - 0.987348685)/ethaneVap < 0.0005);
    assertTrue(Math.abs(pentaneVap - 0.953596213)/pentaneVap < 0.0005);

  }
  
  public void testSolveLiquidFugacities(){
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.0721212);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.9065648);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.9278788);
    inletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.0934352);
    
    inletStream.setIsIdeal(false);
    inletStream.setMolarFlowRate(1.0);
    inletStream.setPressure(100000.0); // 1 bar
    inletStream.setTemperature(254.0);
    inletStream.setVapourFraction(0.75245204);
    
    PengRobinson testPeng = new PengRobinson(inletStream);
    testPeng.nonIdealCalcs();
    
    double ethaneLiq = testPeng.getFlowStream().getFlowSpecies().get(0).getLiquidFugacity(); //ethane
    double pentaneLiq = testPeng.getFlowStream().getFlowSpecies().get(1).getLiquidFugacity(); //pentane
    
    System.out.println("ethane liquid fugacity: "+ethaneLiq);
    System.out.println("pentane liquid fugacity: "+pentaneLiq);
    
    assertTrue(Math.abs(ethaneLiq  - 12.41099827)/ethaneLiq < 0.0005);
    assertTrue(Math.abs(pentaneLiq - 0.096024869)/pentaneLiq < 0.0005);

  }
  
  private PengRobinson createTestPeng(){
    FlowStream testStream = new FlowStream();
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setCriticalPressure(3369000.0);
    pentane.setCriticalTemperature(469.7);
    pentane.setAcentricFactor(0.249);
    pentane.setOverallMoleFraction(0.8168);
    FlowSpecies hexane = new FlowSpecies();
    hexane.setCriticalPressure(3012000.0);
    hexane.setCriticalTemperature(507.4);
    hexane.setAcentricFactor(0.305);
    hexane.setOverallMoleFraction(0.1501);
    FlowSpecies heptane = new FlowSpecies();
    heptane.setCriticalPressure(2736000.0);
    heptane.setCriticalTemperature(540.3);
    heptane.setAcentricFactor(0.349);
    heptane.setOverallMoleFraction(0.0331);
    testStream.addFlowSpecies(pentane);
    testStream.addFlowSpecies(hexane);
    testStream.addFlowSpecies(heptane);
    testStream.setTemperature(322.29);
    testStream.setPressure(101325.0);
    
    PengRobinson testPeng = new PengRobinson(testStream);
    
    return testPeng;
  }
  
}