import junit.framework.TestCase;
import java.util.List;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_PresetSpecies extends TestCase {
  
  // Test that the preset species list does return a list of species
  public void testGet() {
    List<FlowSpecies> flowSpecies = PresetSpecies.get();
    assertTrue("PresetSpecies.get()", flowSpecies.size() > 0);
    assertFalse("PresetSpecies.get()", flowSpecies.size() == 0);
  }
  
  // Test whether the units of the Antoine coefficients are Pa, K
  // If the saturation pressure at the known boiling point is atmospheric pressure,
  // then the units are correct. Accepted error in the tests is 2.5 %
  // Source of boiling points: https://www.engineeringtoolbox.com/boiling-points-fluids-gases-d_155.html
  public void testAntoineCoefficients() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    double lowerBound = 98791.9; // 101325 Pa - 2.5%
    double upperBound = 103858.2; // 101325 Pa + 2.5%
    
    double ethaneSaturationPressure = SaturationPressure.calc(presetSpecies.get(0), 184.37);
    assertTrue("Ethane saturation pressure (Antoine coefficients)",
               ethaneSaturationPressure > lowerBound && ethaneSaturationPressure < upperBound);
    
    double pentaneSaturationPressure = SaturationPressure.calc(presetSpecies.get(1), 309.15);
    assertTrue("Pentane saturation pressure (Antoine coefficients)",
               pentaneSaturationPressure > lowerBound && pentaneSaturationPressure < upperBound);
    
    double hexaneSaturationPressure = SaturationPressure.calc(presetSpecies.get(2), 341.85);
    assertTrue("Hexane saturation pressure (Antoine coefficients)",
               hexaneSaturationPressure > lowerBound && hexaneSaturationPressure < upperBound);
    
    double cyclohexaneSaturationPressure = SaturationPressure.calc(presetSpecies.get(3), 353.85);
    assertTrue("Cyclohexane saturation pressure (Antoine coefficients)",
               cyclohexaneSaturationPressure > lowerBound && cyclohexaneSaturationPressure < upperBound);
    
    double waterSaturationPressure = SaturationPressure.calc(presetSpecies.get(4), 373.15);
    assertTrue("Water saturation pressure (Antoine coefficients)",
               waterSaturationPressure > lowerBound && waterSaturationPressure < upperBound);
    
    double nitrogenSaturationPressure = SaturationPressure.calc(presetSpecies.get(5), 77.15);
    assertTrue("Nitrogen saturation pressure (Antoine coefficients)",
               nitrogenSaturationPressure > lowerBound && nitrogenSaturationPressure < upperBound);
    
    
  }
  
}
