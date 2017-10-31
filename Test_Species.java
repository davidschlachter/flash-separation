import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_Species extends TestCase {
  
  /**
   * Test the constructor
   */
  public void testConstructor() {
    Species testSpecies = new Species();
    assertTrue("new Species()", testSpecies != null);
    assertFalse("new Species()", testSpecies == null);
  }
  
  /**
   * Test the getter and setter for the liquid heat capacity constants for a species
   */
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
  
  /**
   * Test the getter and setter for the vapour heat capacity constants for a species
   */
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
  
  /**
   * Test the getter and setter for the Antoine equation coefficients for a species
   */
  public void testAntoineConstants() {
    
    Species testSpecies = new Species();
    
    double antoineA = 1.0;
    double antoineB = 1.0;
    double antoineC = 1.0;
    
    testSpecies.setAntoineConstants(antoineA, antoineB, antoineC);
    
    double[] antoineConstants = testSpecies.getAntoineConstants();
    
    assertEquals("Species.set/getAntoineConstants[0]", antoineA, antoineConstants[0]);
    assertEquals("Species.set/getAntoineConstants[1]", antoineB, antoineConstants[1]);
    assertEquals("Species.set/getAntoineConstants[2]", antoineC, antoineConstants[2]);

    
  }
  
  
  /**
   * Test the getter and setter for the species name
   */
  public void testSpeciesName() {
    Species testSpecies = new Species();
    
    String speciesName = "ethanol";
    testSpecies.setSpeciesName(speciesName);
    
    assertTrue("Species.getSpeciesName()", speciesName.equals(testSpecies.getSpeciesName()));
    assertFalse("Species.getSpeciesName()", speciesName.equals("water"));
    
  }
  
  /**
   * Test the getter and setter for the critical temperature
   */
  public void testCriticalTemperature() {
    Species testSpecies = new Species();
    
    double criticalTemperature = 45.0;
    //testSpecies.setCriticalTemperature(criticalTemperature);
    
    assertFalse("Species.setCriticalTemperature(-1.0)", testSpecies.setCriticalTemperature(-1.0));
    assertEquals("Species.setCriticalTemperature(1.0)",  true, testSpecies.setCriticalTemperature(criticalTemperature));
    assertTrue("Species.getCriticalTemperature()", testSpecies.getCriticalTemperature() == criticalTemperature);
    
  }
  
  /*
   * Test the cloning method
   */
  public void testClone() {
    Species testSpecies = new Species();
    
    testSpecies.setSpeciesName("test");
    testSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testSpecies.setAntoineConstants(1.0, 1.0, 1.0);
    testSpecies.setCriticalTemperature(1.0);
    testSpecies.setCriticalPressure(1.0);
    testSpecies.setCriticalVolume(1.0);
    testSpecies.setCriticalZ(1.0);
    testSpecies.setAcentricFactor(1.0);
    testSpecies.setZValue(1.0);
    testSpecies.setBeta(1.0);
    testSpecies.setQValue(1.0);
    testSpecies.setActivityCoefficient(1.0);
    testSpecies.setMixtureFugacityCoefficient(1.0);
    
    double testVapourHeatCapacityConstants[] = testSpecies.getVapourHeatCapacityConstants();
    double testLiquidHeatCapacityConstants[] = testSpecies.getLiquidHeatCapacityConstants();
    double testAntoineConstants[] = testSpecies.getAntoineConstants();
    
    Species cloneSpecies = new Species(testSpecies);
    double cloneVapourHeatCapacityConstants[] = cloneSpecies.getVapourHeatCapacityConstants();
    double cloneLiquidHeatCapacityConstants[] = cloneSpecies.getLiquidHeatCapacityConstants();
    double cloneAntoineConstants[] = cloneSpecies.getAntoineConstants();
    
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
    assertEquals("Species.cloneCriticalVolume", testSpecies.getCriticalVolume(), cloneSpecies.getCriticalVolume());
    assertEquals("Species.cloneCriticalZ", testSpecies.getCriticalZ(), cloneSpecies.getCriticalZ());
    assertEquals("Species.cloneAcentricFactor", testSpecies.getAcentricFactor(), cloneSpecies.getAcentricFactor());
    assertEquals("Species.cloneZValue", testSpecies.getZValue(), cloneSpecies.getZValue());
    assertEquals("Species.cloneBeta", testSpecies.getBeta(), cloneSpecies.getBeta());
    assertEquals("Species.cloneQValue", testSpecies.getQValue(), cloneSpecies.getQValue());
    assertEquals("Species.cloneActivityCoefficient", testSpecies.getActivityCoefficient(), cloneSpecies.getActivityCoefficient());
    assertEquals("Species.cloneMixutreFugacityCoefficient", testSpecies.getMixtureFugacityCoefficient(), cloneSpecies.getMixtureFugacityCoefficient());
  }
}
