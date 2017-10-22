import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_Fugacity extends TestCase {
  
    //TODO: add a cloning method here to avoid clutter below when creating testStream over and over
  public Fugacity createTestObject(FlowStream emptyStream){
  
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
  
  emptyStream.addFlowSpecies(methylEthylKetone);
  emptyStream.addFlowSpecies(toluene);
  
  Fugacity testFugacityStream = new Fugacity(emptyStream);
  
  return testFugacityStream;
  
  }
    
  public void testConstructor() {
    
  FlowStream testStream = new FlowStream();
  
  /*FlowSpecies methylEthylKetone = new FlowSpecies();
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
  testStream.addFlowSpecies(toluene); */ 
  
  Fugacity emptyFugacityStream = new Fugacity(testStream); 
  Fugacity filledFugacityStream = new Fugacity (testStream);
  filledFugacityStream = emptyFugacityStream.createTestObject(testStream);
  
  
  
  assertTrue(filledFugacityStream != null);
    
  }
  
  public void testCrossSpeciesCriticalZ(){
    
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
  
  double[][] testCriticalZValues = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testCriticalZValues = testFugacityStream.crossSpeciesCriticalZ();
  
  assertTrue(testCriticalZValues[0][0] > 0.247 && testCriticalZValues[0][0] < 0.251);
  assertTrue(testCriticalZValues[0][1] > 0.254 && testCriticalZValues[0][1] < 0.258);
  assertTrue(testCriticalZValues[1][1] > 0.262 && testCriticalZValues[1][1] < 0.266);
  
  }
  
   public void testCrossSpeciesCriticalVolume(){
    
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
  
  double[][] testCriticalVolumes = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testCriticalVolumes = testFugacityStream.crossSpeciesCriticalVolume();

  assertTrue(testCriticalVolumes[0][0] > 0.000266 && testCriticalVolumes[0][0] < 0.000268);
  assertTrue(testCriticalVolumes[0][1] > 0.000290 && testCriticalVolumes[0][1] < 0.000292);
  assertTrue(testCriticalVolumes[1][1] > 0.000315 && testCriticalVolumes[1][1] < 0.000317);
  
  }
   
  public void testCrossSpeciesCriticalTemperature(){
    
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
  
  double[][] testCriticalTemps = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testCriticalTemps = testFugacityStream.crossSpeciesCriticalTemperature();

  assertTrue(testCriticalTemps[0][0] > 535.0 && testCriticalTemps[0][0] < 536.0);
  assertTrue(testCriticalTemps[0][1] > 562.5 && testCriticalTemps[0][1] < 563.5);
  assertTrue(testCriticalTemps[1][1] > 591.5 && testCriticalTemps[1][1] < 592.5);
  
  }
  
  public void testCrossSpeciesCriticalPressure(){
    
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
  
  double[][] testCriticalPressures = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testCriticalPressures = testFugacityStream.crossSpeciesCriticalPressure();
  
  testStream.setPressure(25000);

  assertTrue(testCriticalPressures[0][0] > 4140000 && testCriticalPressures[0][0] < 4160000);
  //assertTrue(testCriticalPressures[1][1] > 41.0 && testCriticalPressures[1][1] < 41.2);
  //assertTrue(testCriticalPressures[0][1] > 41.2 && testCriticalPressures[0][1] < 41.4);
  
  }
  
  public void testOmegaIJ(){
    
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
  
  double[][] testOmegaIJ = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testOmegaIJ = testFugacityStream.omegaIJ();

  assertTrue(testOmegaIJ[0][0] > 0.321 && testOmegaIJ[0][0] < 0.325);
  assertTrue(testOmegaIJ[1][1] > 0.260 && testOmegaIJ[1][1] < 0.264);
  assertTrue(testOmegaIJ[0][1] > 0.291 && testOmegaIJ[0][1] < 0.295);
  
  }
  
  public void testBValues(){
    
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
  
  testStream.setTemperature(323.15);
  
  double[][] testBValues = new double[testStream.getFlowSpecies().size()][testStream.getFlowSpecies().size()];
    
  testBValues = testFugacityStream.bValues();

  assertTrue(testBValues[0][0] > -0.001380 && testBValues[0][0] < -0.001370);
  assertTrue(testBValues[1][1] > -0.001875 && testBValues[1][1] < -0.001870);
  assertTrue(testBValues[0][1] > -0.001615 && testBValues[0][1] < -0.001608);
  
  }
  
  public void testFugacityCoefficients(){
  
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
  
  testStream.setTemperature(323.15);
  testStream.setPressure(25000.0);
  
  double[] results = new double[testStream.getFlowSpecies().size()];
  
  results = testFugacityStream.fugacityCoefficients();
  
  assertTrue(results[0] < 0.989 && results[0] > 0.985);
  assertTrue(results[1] < 0.985 && results[1] > 0.981);
  
  }
  
}
