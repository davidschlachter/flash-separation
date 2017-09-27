import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_Species extends TestCase {
  
  /**
   * Test the getter and setter for the heat capacity constants for a species
   */
  public void testHeatCapacityConstants() {
    
    Species testSpecies = new Species();
    
    double heatCapacityA = 1.0;
    double heatCapacityB = 1.0;
    double heatCapacityC = 1.0;
    double heatCapacityD = 1.0;
    
    testSpecies.setHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double[] heatCapacityConstants = testSpecies.getHeatCapacityConstants();
    
    assertEquals("Species.set/getHeatCapacityConstants[0]", heatCapacityA, heatCapacityConstants[0]);
    assertEquals("Species.set/getHeatCapacityConstants[1]", heatCapacityB, heatCapacityConstants[1]);
    assertEquals("Species.set/getHeatCapacityConstants[2]", heatCapacityC, heatCapacityConstants[2]);
    assertEquals("Species.set/getHeatCapacityConstants[3]", heatCapacityD, heatCapacityConstants[3]);
    
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
   * Test the getter and setter for the heat of vapourization
   */
  public void testHeatOfVapourization() {
    Species testSpecies = new Species();
    
    double heatOfVapourization = 1.0;
    testSpecies.setHeatOfVapourization(heatOfVapourization);
    
    assertEquals("Species.set/getHeatOfVapourization", heatOfVapourization, testSpecies.getHeatOfVapourization());
    
  }
  
  
  /**
   * Test the getter and setter for the activity coefficient
   */
  public void testActivityCoefficient() {
    Species testSpecies = new Species();
    
    double activityCoefficient = 0.5;
    assertEquals("Species.setActivityCoefficient(0)", true, testSpecies.setActivityCoefficient(activityCoefficient));
    assertEquals("Species.getActivityCoefficient()", activityCoefficient, testSpecies.getActivityCoefficient());
    
  }
  
}
