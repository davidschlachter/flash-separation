import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public class Test_SaturationPressure extends TestCase {
  
  // Test the saturation pressure calculation vs manual calculation in Excel
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
  
  // Test the saturation pressure calculation vs manual calculation in Excel
  public void testSatPressure() {
    Species pentane = new Species();
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454));
    double satPressure = SaturationPressure.calc(pentane,300.0);
    assertTrue("SaturationPressure.calc(pentane,300.0)", (satPressure > 73154.2 && satPressure < 73155.0));
  }
  
  // Test that we're getting the best Antoine coefficients if the temperature is out of range
  public void testOutOfBoundsSatPressure() {
    Species testSpecies = new Species();
    List<AntoineCoefficients> testSpeciesAntoine = new ArrayList<AntoineCoefficients>();
    testSpeciesAntoine.add(new AntoineCoefficients(2.0, 0.0, 0.0,  20.0, 100.0));
    testSpeciesAntoine.add(new AntoineCoefficients(3.0, 0.0, 0.0, 100.1, 200.0));
    testSpeciesAntoine.add(new AntoineCoefficients(4.0, 0.0, 0.0, 200.1, 300.0));
    testSpecies.setAntoineConstants(testSpeciesAntoine);
    
    assertTrue(Math.abs(SaturationPressure.calc(testSpecies, 50.0) - 100.0) < 0.1);
    assertTrue(Math.abs(SaturationPressure.calc(testSpecies, 150.0) - 1000.0) < 0.1);
    assertTrue(Math.abs(SaturationPressure.calc(testSpecies, 250.0) - 10000.0) < 0.1);
    assertTrue(Math.abs(SaturationPressure.calc(testSpecies, 10.0) - 100.0) < 0.1);
    assertTrue(Math.abs(SaturationPressure.calc(testSpecies, 400.0) - 10000.0) < 0.1);
  }
  
}
