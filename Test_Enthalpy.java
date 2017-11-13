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
  
  // Test a pure species enthalpy calculation within the supercooled vapour range of temperatures
  // Reference values are from the steam tables
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
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    
    // Note that real change is closer to 890 J/mol, but the correlation is a bit off
    // in this range
    assertTrue(theEnthalpy > 859.0 && theEnthalpy < 861.0);
    assertTrue(theReverseEnthalpy > -861.0 && theReverseEnthalpy < -859.0);
  }
  
  // Test a pure species enthalpy calculation within the subcooled liquid range of temperatures
  // Reference values are from the steam tables
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
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    assertTrue(theEnthalpy > 4503.0 && theEnthalpy < 4540.0);
    assertTrue(theReverseEnthalpy > -4540.0 && theReverseEnthalpy < -4503.0);
  }
  
  // Test a pure species enthalpy calculation for the transition between subcooled liquid and
  // superheated vapour (in both directions)
  // Reference values are from the steam tables
  public void testPureSpeciesPhaseChangeEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    //System.out.println("The phase change enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 47464.0 && theEnthalpy < 49401.0);
    assertTrue(theReverseEnthalpy < -47464.0 && theReverseEnthalpy > -49401.0);
  }
  
  // Test a pure species enthalpy calculation for the transition between subcooled liquid and
  // vapour-liquid equilibrium (in both directions)
  // Reference values are from the steam tables
  public void testPureSpeciesVLESubcooledLiquidEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(100.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.5); // Half of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    //System.out.println("The VLE/subcooled liquid enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 25860.7 && theEnthalpy < 26916.3);
    assertTrue(theReverseEnthalpy < -25860.7 && theReverseEnthalpy > -26916.3);
  }
  
  // Test a pure species enthalpy calculation for the transition between VLE and
  // superheated vapour (in both directions)
  // Reference values are from the steam tables
  public void testPureSpeciesVLESuperHeatedVapourEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(100.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.5); // Half of the water is vapourized
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.0);
    outletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0); // All of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    //System.out.println("The VLE/superheated vapour enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 21603.5 && theEnthalpy < 22485.3);
    assertTrue(theReverseEnthalpy < -21603.5 && theReverseEnthalpy > -22485.3);
  }
  
  // Test a pure species enthalpy calculation within VLE
  // Reference values are from the steam tables
  public void testPureSpeciesVLEEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(100.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.25); // 25% of the water is vapourized
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(100.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.75); // 75% of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    //System.out.println("The VLE enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 19923.4 && theEnthalpy < 20736.6);
    assertTrue(theReverseEnthalpy < -19923.4 && theReverseEnthalpy > -20736.6);
  }
  
  // Test a mixture enthalpy calculation from subcooled liquid to superheated vapour
  // Reference values are from our own manual calculations using BubblePoint and HeatCapacity methods
  public void testMixtureEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.75);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.75);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.0);
    inletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(3))); // Cyclohexane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.25);
    inletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.25);
    inletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.0);
    inletStream.getFlowSpecies().get(1).setHeatOfVapourization(31690.4);
    inletStream.setMolarFlowRate(2.0);
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.75);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.75);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.0);
    outletStream.getFlowSpecies().get(0).setHeatOfVapourization(40660.0);
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(3)));
    outletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.25);
    outletStream.getFlowSpecies().get(1).setVapourMoleFraction(0.25);
    outletStream.getFlowSpecies().get(1).setLiquidMoleFraction(0.0);
    outletStream.getFlowSpecies().get(1).setHeatOfVapourization(31690.4);
    outletStream.setMolarFlowRate(2.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(outletStream.getTemperature());
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).testFunction(inletStream.getTemperature());
    
    //System.out.println("The VLE mixture enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 96500.94 && theEnthalpy < 100439.75);
    assertTrue(theReverseEnthalpy < -96500.94 && theReverseEnthalpy > -100439.75);
  }
  
}
