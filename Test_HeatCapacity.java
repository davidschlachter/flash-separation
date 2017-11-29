import junit.framework.TestCase;
import java.util.List;

public class Test_HeatCapacity extends TestCase {
  
  public void testLiquidIntegrate() {
    Species testSpecies = new Species();
    
    double heatCapacityA = 33.866;
    double heatCapacityB = -0.1726;
    double heatCapacityC = 0.00034917;
    double heatCapacityD = 0.0;
    
    testSpecies.setLiquidHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double integral = HeatCapacity.integrate(testSpecies, 295.0, 333.0, "liquid") * 8.3145;
    
    assertTrue("HeatCapacity.integrate()", integral > 4460.0 && integral < 4470.0);
  }
  
  public void testVapourIntegrate() {
    Species testSpecies = new Species();
    
    double heatCapacityA = 33.866;
    double heatCapacityB = -0.1726;
    double heatCapacityC = 0.00034917;
    double heatCapacityD = 0.0;
    
    testSpecies.setVapourHeatCapacityConstants(heatCapacityA, heatCapacityB, heatCapacityC, heatCapacityD);
    
    double integral = HeatCapacity.integrate(testSpecies, 295.0, 333.0, "vapour") * 8.3145;
    
    assertTrue("HeatCapacity.integrate()", integral > 4460.0 && integral < 4470.0);
  }
  
  // Test integrated heat capacity of water vapour. Expected values are from steam tables.
  public void testWaterVapourIntegrate() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    Species water = new FlowSpecies(presetSpecies.get(4)); // Water
    
    double integral = HeatCapacity.integrate(water, 125.0+273.15, 150.0+273.15, "vapour");
    
    // Actual value is about 899 J/mol, but integrated correlation expected to give 
    // 859.982 J/mol (calculated with WolframAlpha)
    assertTrue("HeatCapacity.integrate()", integral > 859.0 && integral < 861.0);
  }
  
}