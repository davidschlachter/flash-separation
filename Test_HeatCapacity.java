import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_HeatCapacity extends TestCase {
  
  public void testIntegrate() {
    
    Species testSpecies = new Species();
    
    double heatCapacityA = 33.866;
    double heatCapacityB = -0.1726;
    double heatCapacityC = 0.00034917;
    double heatCapacityD = 0.0;
    
    testSpecies.setHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double integral = HeatCapacity.integrate(testSpecies, 295.0, 333.0);
    
    assertTrue("HeatCapacity.integrate()", integral > 4460.0 && integral < 4470.0);
    
  }
  
}
