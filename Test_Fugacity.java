import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_Fugacity extends TestCase {
  
  public void testConstructor() {
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);  
    assertTrue(testFugacityStream != null);
    
  }
  
  public void testCrossSpeciesCriticalZ(){
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = createTestObject();
    
    double[][] testCriticalZValues;
    int n = testStream.getFlowSpecies().size();
    testCriticalZValues = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testCriticalZValues[i] = new double[i+1];
    }
    testCriticalZValues = testFugacityStream.crossSpeciesCriticalZ();
    
    assertTrue(testCriticalZValues[0][0] > 0.247 && testCriticalZValues[0][0] < 0.251);
    assertTrue(testCriticalZValues[1][0] > 0.254 && testCriticalZValues[1][0] < 0.258);
    assertTrue(testCriticalZValues[1][1] > 0.262 && testCriticalZValues[1][1] < 0.266);
    
  }
  
  public void testCrossSpeciesCriticalVolume(){
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = createTestObject();
    
    double[][] testCriticalVolumes;
    int n = testStream.getFlowSpecies().size();
    testCriticalVolumes = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testCriticalVolumes[i] = new double[i+1];
    }
    testCriticalVolumes = testFugacityStream.crossSpeciesCriticalVolume();
    
    assertTrue(testCriticalVolumes[0][0] > 0.000266 && testCriticalVolumes[0][0] < 0.000268);
    assertTrue(testCriticalVolumes[1][0] > 0.000290 && testCriticalVolumes[1][0] < 0.000292);
    assertTrue(testCriticalVolumes[1][1] > 0.000315 && testCriticalVolumes[1][1] < 0.000317);
    
  }
  
  public void testCrossSpeciesCriticalTemperature(){
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = createTestObject();
    
    double[][] testCriticalTemps;
    int n = testStream.getFlowSpecies().size();
    testCriticalTemps = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testCriticalTemps[i] = new double[i+1];
    }
    testCriticalTemps = testFugacityStream.crossSpeciesCriticalTemperature();
    
    assertTrue(testCriticalTemps[0][0] > 535.0 && testCriticalTemps[0][0] < 536.0);
    assertTrue(testCriticalTemps[1][0] > 562.5 && testCriticalTemps[1][0] < 563.5);
    assertTrue(testCriticalTemps[1][1] > 591.5 && testCriticalTemps[1][1] < 592.5);
    
  }
  
  public void testCrossSpeciesCriticalPressure(){
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = createTestObject();
    
    double[][] testCriticalPressures;
    int n = testStream.getFlowSpecies().size();
    testCriticalPressures = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testCriticalPressures[i] = new double[i+1];
    }
    testCriticalPressures = testFugacityStream.crossSpeciesCriticalPressure();
    
    testStream.setPressure(25000);
    
    assertTrue(testCriticalPressures[0][0] > 4140000 && testCriticalPressures[0][0] < 4160000);
    assertTrue(testCriticalPressures[1][1] > 4100000 && testCriticalPressures[1][1] < 4120000);
    assertTrue(testCriticalPressures[1][0] > 4120000 && testCriticalPressures[1][0] < 4140000);
    
  }
  
  public void testOmegaIJ(){
    
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = createTestObject();
    
    double[][] testOmegaIJ;
    int n = testStream.getFlowSpecies().size();
    testOmegaIJ = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testOmegaIJ[i] = new double[i+1];
    }
    testOmegaIJ = testFugacityStream.omegaIJ();
    
    assertTrue(testOmegaIJ[0][0] > 0.321 && testOmegaIJ[0][0] < 0.325);
    assertTrue(testOmegaIJ[1][1] > 0.260 && testOmegaIJ[1][1] < 0.264);
    assertTrue(testOmegaIJ[1][0] > 0.291 && testOmegaIJ[1][0] < 0.295);
    
  }
  
  public void testBValues(){
    
    Fugacity testFugacityStream = createTestObject();;
    
    testFugacityStream.getFlowStream().setTemperature(323.15);
    
    double[][] testBValues;
    testBValues = testFugacityStream.bValues();
    
    assertTrue(testBValues[0][0] > -0.001380 && testBValues[0][0] < -0.001370);
    assertTrue(testBValues[1][1] > -0.001875 && testBValues[1][1] < -0.001870);
    assertTrue(testBValues[1][0] > -0.001615 && testBValues[1][0] < -0.001608);
    
  }
  
  public void testFugacityCoefficients(){
    
    Fugacity testFugacityStream = createTestObject();
    
    testFugacityStream.getFlowStream().setTemperature(323.15);
    testFugacityStream.getFlowStream().setPressure(25000.0);
    testFugacityStream.fugacityCoefficients();
    
    double[] results = new double[testFugacityStream.getFlowStream().getFlowSpecies().size()];
    int i = 0;
    for(i = 0; i < testFugacityStream.getFlowStream().getFlowSpecies().size(); i++){
      results[i] = testFugacityStream.getFlowStream().getFlowSpecies().get(i).getMixtureFugacityCoefficient();
    }
    
    assertTrue(results[0] < 0.989 && results[0] > 0.985);
    assertTrue(results[1] < 0.985 && results[1] > 0.981);
    
  }
  
  public void testBeta(){
    
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.beta();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getBeta() < 0.027 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getBeta() > 0.026);   
  }
  
  public void testQValue(){
    
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.qValue();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getQValue() < 6.95 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getQValue() > 6.85);
  }
  
  public void testZValue(){
    
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.beta();
    testFugacityStream.qValue();
    testFugacityStream.zValue();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getZValue() < 0.822 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getQValue() > 0.816);
  }
  
  public void testActivityCoefficient(){
    
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setMixtureFugacityCoefficient(0.985);
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setVapourMoleFraction(0.75);
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setLiquidMoleFraction(0.5);
    testFugacityStream.computeNonIdealParameters(testFugacityStream);
    
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getActivityCoefficient() < 1.77 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getActivityCoefficient() > 1.73);
  }
  
  private Fugacity createTestObject(){
    
    FlowStream testStream = new FlowStream();
    
    FlowSpecies methylEthylKetone = new FlowSpecies();
    methylEthylKetone.setCriticalTemperature(535.5);
    methylEthylKetone.setCriticalPressure(41.50);
    methylEthylKetone.setCriticalZ(0.249);
    methylEthylKetone.setCriticalVolume(267.0);
    methylEthylKetone.setAcentricFactor(0.323); 
    
    FlowSpecies toluene = new FlowSpecies();
    toluene.setCriticalTemperature(591.8);
    toluene.setCriticalPressure(41.06);
    toluene.setCriticalZ(0.264);
    toluene.setCriticalVolume(316.0);
    toluene.setAcentricFactor(0.262); 
    
    testStream.addFlowSpecies(methylEthylKetone);
    testStream.addFlowSpecies(toluene); 
    
    Fugacity testFugacityStream = new Fugacity(testStream); 
    
    return testFugacityStream;
    
  }
  
  private Fugacity createPureSpeciesTestObject(){
    FlowSpecies nButane = new FlowSpecies();
    nButane.setCriticalTemperature(425.1);
    nButane.setCriticalPressure(3796000);
    nButane.setAcentricFactor(0.199);
    FlowStream testStream = new FlowStream();
    testStream.addFlowSpecies(nButane);
    testStream.setTemperature(350);
    testStream.setPressure(945730);
    Fugacity testFugacityObject = new Fugacity(testStream);
    return testFugacityObject;
  }
  
}
