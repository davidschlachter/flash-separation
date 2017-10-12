import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_HeatCapacity extends TestCase {
  
  public void testLiquidIntegrate() {
    
    Species testSpecies = new Species();
    
    double heatCapacityA = 33.866;
    double heatCapacityB = -0.1726;
    double heatCapacityC = 0.00034917;
    double heatCapacityD = 0.0;
    
    testSpecies.setLiquidHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double integral = HeatCapacity.integrate(testSpecies, 295.0, 333.0, "liquid");
    
    assertTrue("HeatCapacity.integrate()", integral > 4460.0 && integral < 4470.0);
    
  }
  
  public void testVapourIntegrate() {
    
    Species testSpecies = new Species();
    
    double heatCapacityA = 33.866;
    double heatCapacityB = -0.1726;
    double heatCapacityC = 0.00034917;
    double heatCapacityD = 0.0;
    
    testSpecies.setVapourHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double integral = HeatCapacity.integrate(testSpecies, 295.0, 333.0, "vapour");
    
    assertTrue("HeatCapacity.integrate()", integral > 4460.0 && integral < 4470.0);
    
  }
  
}
