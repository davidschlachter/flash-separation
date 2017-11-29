import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public class Test_Species extends TestCase {
  
  public void testConstructor() {
    Species testSpecies = new Species();
    assertTrue("new Species()", testSpecies != null);
    assertFalse("new Species()", testSpecies == null);
  }
  
  public void testLiquidHeatCapacityConstants() {
    Species testSpecies = new Species();
    
    double heatCapacityA = 1.0;
    double heatCapacityB = 1.0;
    double heatCapacityC = 1.0;
    double heatCapacityD = 1.0;
    
    testSpecies.setLiquidHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    double[] heatCapacityConstants = testSpecies.getLiquidHeatCapacityConstants();
    
    assertEquals("Species.set/getLiquidHeatCapacityConstants[0]", heatCapacityA, heatCapacityConstants[0]);
    assertEquals("Species.set/getLiquidHeatCapacityConstants[1]", heatCapacityB, heatCapacityConstants[1]);
    assertEquals("Species.set/getLiquidHeatCapacityConstants[2]", heatCapacityC, heatCapacityConstants[2]);
    assertEquals("Species.set/getLiquidHeatCapacityConstants[3]", heatCapacityD, heatCapacityConstants[3]);
  }
  
  public void testVapourHeatCapacityConstants() {
    Species testSpecies = new Species();
    
    double heatCapacityA = 1.0;
    double heatCapacityB = 1.0;
    double heatCapacityC = 1.0;
    double heatCapacityD = 1.0;
    
    testSpecies.setVapourHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double[] heatCapacityConstants = testSpecies.getVapourHeatCapacityConstants();
    
    assertEquals("Species.set/getVapourHeatCapacityConstants[0]", heatCapacityA, heatCapacityConstants[0]);
    assertEquals("Species.set/getVapourHeatCapacityConstants[1]", heatCapacityB, heatCapacityConstants[1]);
    assertEquals("Species.set/getVapourHeatCapacityConstants[2]", heatCapacityC, heatCapacityConstants[2]);
    assertEquals("Species.set/getVapourHeatCapacityConstants[3]", heatCapacityD, heatCapacityConstants[3]);
  }
  
  public void testAntoineConstants() {
    
    Species testSpecies = new Species();
    
    double antoineA = 1.0;
    double antoineB = 1.0;
    double antoineC = 1.0;
    
    testSpecies.setAntoineConstants(new AntoineCoefficients(antoineA, antoineB, antoineC));
    
    double[] antoineConstants = testSpecies.getAntoineConstants(0.0);
    
    assertEquals("Species.set/getAntoineConstants[0]", antoineA, antoineConstants[0]);
    assertEquals("Species.set/getAntoineConstants[1]", antoineB, antoineConstants[1]);
    assertEquals("Species.set/getAntoineConstants[2]", antoineC, antoineConstants[2]);
  }
  
  public void testMultipleAntoineConstants() {
    Species testSpecies = new Species();
    
    List<AntoineCoefficients> cyclohexaneAntoine = new ArrayList<AntoineCoefficients>();
    cyclohexaneAntoine.add(new AntoineCoefficients(9.145546612, 1316.554, -35.581, 323, 523)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.997716612, 1216.93, -48.621, 303, 343)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.176966612, 780.637, -107.29, 315.7, 353.9)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.975596612, 1203.562, -50.287, 293.06, 354.73)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    testSpecies.setAntoineConstants(cyclohexaneAntoine);
    
    double[] returnedConstants = testSpecies.getAntoineConstants(400.0);
    assertTrue(returnedConstants[0] > 9.1450 && returnedConstants[0] < 9.1456);
    
    returnedConstants = testSpecies.getAntoineConstants(295.0);
    assertTrue(returnedConstants[0] > 8.9750 && returnedConstants[0] < 8.9756);
  }
  
  public void testSpeciesName() {
    Species testSpecies = new Species();
    String speciesName = "ethanol";
    testSpecies.setSpeciesName(speciesName);
    
    assertTrue("Species.getSpeciesName()", speciesName.equals(testSpecies.getSpeciesName()));
    assertFalse("Species.getSpeciesName()", speciesName.equals("water"));
  }
  
  public void testCriticalTemperature() {
    Species testSpecies = new Species();
    
    double criticalTemperature = 45.0;
    //testSpecies.setCriticalTemperature(criticalTemperature);
    
    assertFalse("Species.setCriticalTemperature(-1.0)", testSpecies.setCriticalTemperature(-1.0));
    assertEquals("Species.setCriticalTemperature(1.0)",  true, testSpecies.setCriticalTemperature(criticalTemperature));
    assertTrue("Species.getCriticalTemperature()", testSpecies.getCriticalTemperature() == criticalTemperature);
  }
  
  public void testCriticalPressure() {
    Species testSpecies = new Species();
    double criticalPressure = 300.0;
    
    assertFalse("Species.setCriticalPressure(-1.0)", testSpecies.setCriticalPressure(-1.0));
    assertEquals("Species.setCriticalPressure(1.0)", true, testSpecies.setCriticalPressure(criticalPressure));
    assertTrue("Species.setCriticalPressure()", testSpecies.getCriticalPressure() == criticalPressure);
  }
  
  public void testAcentricFactor() {
    Species testSpecies = new Species();
    double acentricFactor = 0.5;
    
    assertFalse("Species.setAcentricFactor(-1.1)", testSpecies.setAcentricFactor(-1.1));
    assertFalse("Species.setAcentricFactor(1.1)", testSpecies.setAcentricFactor(1.1));
    assertEquals("Species.setAcentricFactor(1.0)", true, testSpecies.setAcentricFactor(acentricFactor));
    assertTrue("Species.setAcentricFactor()", testSpecies.getAcentricFactor() == acentricFactor);
  }
  
  public void testClone() {
    Species testSpecies = new Species();
    
    testSpecies.setSpeciesName("test");
    testSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    testSpecies.setCriticalTemperature(1.0);
    testSpecies.setCriticalPressure(1.0);
    testSpecies.setAcentricFactor(1.0);
    
    double testVapourHeatCapacityConstants[] = testSpecies.getVapourHeatCapacityConstants();
    double testLiquidHeatCapacityConstants[] = testSpecies.getLiquidHeatCapacityConstants();
    double testAntoineConstants[] = testSpecies.getAntoineConstants(0.0);
    
    Species cloneSpecies = testSpecies.clone();
    double cloneVapourHeatCapacityConstants[] = cloneSpecies.getVapourHeatCapacityConstants();
    double cloneLiquidHeatCapacityConstants[] = cloneSpecies.getLiquidHeatCapacityConstants();
    double cloneAntoineConstants[] = cloneSpecies.getAntoineConstants(0.0);
    
    assertEquals("Species.cloneSpeciesName", testSpecies.getSpeciesName(), cloneSpecies.getSpeciesName());
    assertEquals("Species.cloneVapourHeatCapacityA", testVapourHeatCapacityConstants[0], cloneVapourHeatCapacityConstants[0]);
    assertEquals("Species.cloneVapourHeatCapacityB", testVapourHeatCapacityConstants[1], cloneVapourHeatCapacityConstants[1]);
    assertEquals("Species.cloneVapourHeatCapacityC", testVapourHeatCapacityConstants[2], cloneVapourHeatCapacityConstants[2]);
    assertEquals("Species.cloneVapourHeatCapacityD", testVapourHeatCapacityConstants[3], cloneVapourHeatCapacityConstants[3]);
    assertEquals("Species.cloneLiquidHeatCapacityA", testLiquidHeatCapacityConstants[0], cloneLiquidHeatCapacityConstants[0]);
    assertEquals("Species.cloneLiquidHeatCapacityB", testLiquidHeatCapacityConstants[1], cloneLiquidHeatCapacityConstants[1]);
    assertEquals("Species.cloneLiquidHeatCapacityC", testLiquidHeatCapacityConstants[2], cloneLiquidHeatCapacityConstants[2]);
    assertEquals("Species.cloneLiquidHeatCapacityD", testLiquidHeatCapacityConstants[3], cloneLiquidHeatCapacityConstants[3]);
    assertEquals("Species.cloneAntoineA", testAntoineConstants[0], cloneAntoineConstants[0]);
    assertEquals("Species.cloneAntoineB", testAntoineConstants[1], cloneAntoineConstants[1]);
    assertEquals("Species.cloneAntoineC", testAntoineConstants[2], cloneAntoineConstants[2]);
    assertEquals("Species.cloneCriticalTemperature", testSpecies.getCriticalTemperature(), cloneSpecies.getCriticalTemperature());
    assertEquals("Species.cloneCriticalPressure", testSpecies.getCriticalPressure(), cloneSpecies.getCriticalPressure());
    assertEquals("Species.cloneAcentricFactor", testSpecies.getAcentricFactor(), cloneSpecies.getAcentricFactor());
  }
  
  public void testEquals() {
    Species testSpecies = new Species();
    testSpecies.setSpeciesName("test");
    testSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    testSpecies.setCriticalTemperature(1.0);
    testSpecies.setCriticalPressure(1.0);
    testSpecies.setAcentricFactor(1.0);
    
    Species equalSpecies = new Species();
    equalSpecies.setSpeciesName("TeSt");
    equalSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    equalSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    equalSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    equalSpecies.setCriticalTemperature(1.0);
    equalSpecies.setCriticalPressure(1.0);
    equalSpecies.setAcentricFactor(1.0);
    
    Species inequalSpecies = new Species();
    inequalSpecies.setSpeciesName("differentTest");
    inequalSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    inequalSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    inequalSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    inequalSpecies.setCriticalTemperature(1.0);
    inequalSpecies.setCriticalPressure(1.0);
    inequalSpecies.setAcentricFactor(1.0);
    
    assertTrue("Species.equals(equalSpecies)", testSpecies.equals(equalSpecies));
    assertFalse("Species.equals(inequalSpecies)", testSpecies.equals(inequalSpecies));
  }
  
  public void testConstructorWithPresetSpecies() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowSpecies testSp = new FlowSpecies(presetSpecies.get(0));
    
    assertTrue(testSp.equals(presetSpecies.get(0)));
  }
  
  public void testHeatOfVapourization() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowSpecies water = new FlowSpecies(presetSpecies.get(4));
    assertTrue("getHeatOfVapourization()", water.getHeatOfVapourization() > 43989. && water.getHeatOfVapourization() < 43991.);
    assertTrue("getHeatOfVapourization(temp)", water.getHeatOfVapourization(573.15) > 24384. && water.getHeatOfVapourization(573.15) < 24386.);
  }
  
}