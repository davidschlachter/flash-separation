import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_SaturationPressure extends TestCase {
  
  // Test the static saturation pressure calculation method
  public void testCalc() {
    
    Species testSpecies = new Species();
    
    // Antoine coefficients for water between 0-100 ºC, with units in Pa, K
    // source: Wikipedia  :)
    testSpecies.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    // Temperature in K for the saturation pressure calculation
    double temperature = 298.15;
    
    double saturationPressure = SaturationPressure.calc(testSpecies, temperature);
    
    // Account for rounding errors in floating point arithmetic
    assertTrue("SaturationPressure.calc(water, 298.15)", (saturationPressure > 3157.9  && saturationPressure < 3158.0));
    assertFalse("SaturationPressure.calc(water, 298.15)", (saturationPressure < 3157.9 || saturationPressure > 3158.0));
    
  }
  
  public void testSatPressure() {
    
    Species pentane = new Species();
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    double satPressure = SaturationPressure.calc(pentane,300.0);
    assertTrue("SaturationPressure.calc(pentane,300.0)", (satPressure > 73154.2 && satPressure < 73155.0));
    
    
  }
  
}
