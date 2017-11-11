import junit.framework.TestCase;
import java.util.List;

public class Test_Enthalpy extends TestCase {
  
  public void testConstructor() { 
    FlowStream inlet = new FlowStream();
    FlowStream outlet = new FlowStream();
    Enthalpy enthalpy = new Enthalpy(inlet, outlet);
    assertTrue("new Enthalpy(inlet, outlet)", enthalpy != null);
    assertFalse("new Enthalpy(inlet, outlet)", enthalpy == null);
  }
  
  // Test a pure species enthalpy calculation, reference is from the steam tables
  public void testPureSpeciesVapourEnthalpyCalculation() {
    
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(125.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(1.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    
       
    // Note that real change is closer to 890 J/mol, but the correlation is a bit off
    // in this range
    assertTrue(theEnthalpy > 859.0 && theEnthalpy < 861.0);
  }
  
  // Test a pure species enthalpy calculation, reference is from the steam tables
  public void testPureSpeciesLiquidEnthalpyCalculation() {
    
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(80.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    
    
    assertTrue(theEnthalpy > 4503.0 && theEnthalpy < 4540.0);
    
  }
 
  public void testPureSpeciesPhaseChangeEnthalpyCalculation()
  {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    
    System.out.println("Water enthalpy from liquid to vapour is: " + theEnthalpy);
    
    
    assertTrue(theEnthalpy > 47464.0 && theEnthalpy < 49401.0);
  }
  
}
