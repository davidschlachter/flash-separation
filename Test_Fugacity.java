 import junit.framework.TestCase;

public class Test_Fugacity extends TestCase {
  
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);  
    assertTrue(testFugacityStream != null);
  }
  
  public void testCrossSpeciesCriticalZ() {
    FlowStream testStream = new FlowStream();
    Fugacity testFugacityStream = new Fugacity(testStream);
    testFugacityStream = new Fugacity(createTestObject());
    
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
  
  public void testCrossSpeciesCriticalVolume() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    double[][] testCriticalVolumes;
    int n = testFugacityStream.getFlowStream().getFlowSpecies().size();
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
  
  public void testCrossSpeciesCriticalTemperature() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    double[][] testCriticalTemps;
    int n = testFugacityStream.getFlowStream().getFlowSpecies().size();
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
  
  public void testCrossSpeciesCriticalPressure() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    double[][] testCriticalPressures;
    int n = testFugacityStream.getFlowStream().getFlowSpecies().size();
    testCriticalPressures = new double[n][];
    int i = 0;
    for(i=0; i<n; i++) {
      testCriticalPressures[i] = new double[i+1];
    }
    testCriticalPressures = testFugacityStream.crossSpeciesCriticalPressure();
    
    testFugacityStream.getFlowStream().setPressure(25000);
    
    assertTrue(testCriticalPressures[0][0] > 4140000 && testCriticalPressures[0][0] < 4160000);
    assertTrue(testCriticalPressures[1][1] > 4100000 && testCriticalPressures[1][1] < 4120000);
    assertTrue(testCriticalPressures[1][0] > 4120000 && testCriticalPressures[1][0] < 4140000);
  }
  
  public void testOmegaIJ() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    double[][] testOmegaIJ;
    int n = testFugacityStream.getFlowStream().getFlowSpecies().size();
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
  
  public void testBValues() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    testFugacityStream.getFlowStream().setTemperature(323.15);
    
    double[][] testBValues;
    testBValues = testFugacityStream.bValues();
    
    assertTrue(testBValues[0][0] > -0.001377 && testBValues[0][0] < -0.001373); 
    assertTrue(testBValues[1][1] > -0.001873 && testBValues[1][1] < -0.001869);  
    assertTrue(testBValues[1][0] > -0.001611 && testBValues[1][0] < -0.001607);
  }
  
  public void testFugacityCoefficients() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    
    testFugacityStream.getFlowStream().setTemperature(323.15);
    testFugacityStream.getFlowStream().setPressure(25000.0);
    testFugacityStream.mixtureFugacityCoefficients();
    
    double[] results = new double[testFugacityStream.getFlowStream().getFlowSpecies().size()];
    int i = 0;
    for(i = 0; i < testFugacityStream.getFlowStream().getFlowSpecies().size(); i++){
      results[i] = testFugacityStream.getFlowStream().getFlowSpecies().get(i).getMixtureFugacityCoefficient();
    }
     
    assertTrue(results[0] < 0.989 && results[0] > 0.985);
    assertTrue(results[1] < 0.985 && results[1] > 0.981);
  }
  
  public void testBeta() {
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.beta();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getBeta() < 0.027 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getBeta() > 0.026);   
  }
  
  public void testQValue() {
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.qValue();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getQValue() < 6.95 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getQValue() > 6.85);
  }
  
  // Can validate with http://people.ds.cam.ac.uk/pjb10/thermo/pure.html
  public void testZValue() {
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    testFugacityStream.beta();
    testFugacityStream.qValue();
    testFugacityStream.flowStreamZValues();
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getZValue() < 0.823 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getZValue() > 0.817);
  }
  
  
  public void testActivityCoefficient() {
    Fugacity testFugacityStream = createPureSpeciesTestObject();
    
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setMixtureFugacityCoefficient(0.985);
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setVapourMoleFraction(0.75);
    testFugacityStream.getFlowStream().getFlowSpecies().get(0).setLiquidMoleFraction(0.5);
    testFugacityStream.computeNonIdealParameters();
    
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getActivityCoefficient() < 1.77 &&
               testFugacityStream.getFlowStream().getFlowSpecies().get(0).getActivityCoefficient() > 1.73);
  }
  
  public void testLargePhi() {
    Fugacity testFugacityStream = new Fugacity(createTestObject());
    testFugacityStream.getFlowStream().setTemperature(323.15);
    testFugacityStream.getFlowStream().setPressure(25000);
    testFugacityStream.computeNonIdealParameters();
    
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getLargePhi() <  1.07);
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(0).getLargePhi() >  1.03);
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(1).getLargePhi() <  1.01);
    assertTrue(testFugacityStream.getFlowStream().getFlowSpecies().get(1).getLargePhi() >  1.005);
    
  }
  
  public void testComputeNonIdealParameters(){
    
    Fugacity unmodifiedStream = new Fugacity(createTestObject());
    Fugacity modifiedStream = new Fugacity(createTestObject());
    modifiedStream.mixtureFugacityCoefficients();
    modifiedStream.beta();
    modifiedStream.qValue();
    modifiedStream.flowStreamZValues();
    modifiedStream.largePhi();
    modifiedStream.activityCoefficient();
    boolean areTheyDifferent = false;
    areTheyDifferent = (modifiedStream.nonIdealParamsDiff(unmodifiedStream)); //needs to be changed to reflect the nature of the test
    assertTrue(areTheyDifferent == true);
    
  }
  
  public static FlowStream createTestObject() {
    FlowStream testStream = new FlowStream();
    
    FlowSpecies methylEthylKetone = new FlowSpecies();
    methylEthylKetone.setCriticalTemperature(535.5);
    methylEthylKetone.setCriticalPressure(4150000.);
    methylEthylKetone.setCriticalZ(0.249);
    methylEthylKetone.setCriticalVolume(0.000267);
    methylEthylKetone.setAcentricFactor(0.323); 
    methylEthylKetone.setVapourMoleFraction(0.5);
    methylEthylKetone.setLiquidMoleFraction(0.5);
    methylEthylKetone.setOverallMoleFraction(0.5);
    methylEthylKetone.setAntoineConstants(new AntoineCoefficients(10., 1400., -35., 0.0, 1000.0));
    
    FlowSpecies toluene = new FlowSpecies();
    toluene.setCriticalTemperature(591.8);
    toluene.setCriticalPressure(4106000.);
    toluene.setCriticalZ(0.264);
    toluene.setCriticalVolume(0.000316);
    toluene.setAcentricFactor(0.262); 
    toluene.setVapourMoleFraction(0.5);
    toluene.setLiquidMoleFraction(0.5);
    toluene.setOverallMoleFraction(0.5);
    toluene.setAntoineConstants(new AntoineCoefficients(9., 1300., -30., 0.0, 1000.0));
    
    testStream.addFlowSpecies(methylEthylKetone);
    testStream.addFlowSpecies(toluene); 
    testStream.setTemperature(300.);
    testStream.setPressure(101325);
    
    
    return testStream;
  }
  
  private Fugacity createPureSpeciesTestObject() {
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
