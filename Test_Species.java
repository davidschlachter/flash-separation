import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_Species extends TestCase {
  
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
   * Test the getter and setter for the activity coefficient
   */
  public void testActivityCoefficient() {
    Species testSpecies = new Species();
    
    double activityCoefficient = 0.5;
    assertTrue("Species.setActivityCoefficient(0)", testSpecies.setActivityCoefficient(activityCoefficient));
    assertFalse("Species.setActivityCoefficient(-2.0)", testSpecies.setActivityCoefficient(-2.0));
    assertEquals("Species.getActivityCoefficient()", activityCoefficient, testSpecies.getActivityCoefficient());
    
  }
  
  /**
   * Test the getter and setter for the species name
   */
  public void testSpeciesName() {
    Species testSpecies = new Species();
    
    String speciesName = "ethanol";
    testSpecies.setSpeciesName(speciesName);
    
    assertTrue("Species.getSpeciesName()", speciesName.equals(testSpecies.getSpeciesName()));
    
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
  
}
