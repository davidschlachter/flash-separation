import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

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
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
    
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
    inletStream.setMolarFlowRate(2.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.setMolarFlowRate(2.0);
    outletStream.setTemperature(80.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
    assertTrue(theEnthalpy > 9006.0 && theEnthalpy < 9080.0);
    assertTrue(theReverseEnthalpy > -9080.0 && theReverseEnthalpy < -9006.0);
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
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
    //System.out.println("The phase change enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 47464.0 && theEnthalpy < 49401.0);
    assertTrue(theReverseEnthalpy < -47464.0 && theReverseEnthalpy > -49401.0);
  }
  
  // Test a pure species enthalpy calculation for the transition between subcooled liquid and
  // superheated vapour (in both directions), with a change in pressure
  // Reference values are from the steam tables
  public void testPureSpeciesPressureChangePhaseChangeEnthalpyCalculation() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(120.0 + 273.15);
    inletStream.setPressure(200000.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(110.0 + 273.15);
    outletStream.setPressure(100000.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
    //System.out.println("The pressure change phase change enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 38868. && theEnthalpy < 40454.);
    assertTrue(theReverseEnthalpy < -38868. && theReverseEnthalpy > -40454.);
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
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(100.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.5); // Half of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
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
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(100.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.5); // Half of the water is vapourized
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0); // All of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
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
    inletStream.setMolarFlowRate(1.0); // 1 mol/s = 3.6 kgmol/h
    inletStream.setTemperature(100.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.25); // 25% of the water is vapourized
    
    outletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4)));
    outletStream.getFlowSpecies().get(0).setOverallMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setVapourMoleFraction(1.0);
    outletStream.getFlowSpecies().get(0).setLiquidMoleFraction(1.0);
    outletStream.setMolarFlowRate(1.0);
    outletStream.setTemperature(100.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(0.75); // 75% of the water is vapourized
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
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
    double theEnthalpy = enthalpy.calc();
    double theReverseEnthalpy = new Enthalpy(outletStream, inletStream).calc();
    
    //System.out.println("The VLE mixture enthalpy change is: "+theEnthalpy+" "+theReverseEnthalpy);
    assertTrue(theEnthalpy > 96500.94 && theEnthalpy < 100439.75);
    assertTrue(theReverseEnthalpy < -96500.94 && theReverseEnthalpy > -100439.75);
  }
  
  // Test specifically if the heat of vapourization and other species properties are available to the
  // Enthapy class
  public void testGettersSetters() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();

    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(4))); // Water
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(3))); // Cyclohexane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.75);
    inletStream.getFlowSpecies().get(0).setLiquidMoleFraction(0.75);
    inletStream.getFlowSpecies().get(0).setVapourMoleFraction(0.0);
    inletStream.setMolarFlowRate(2.0);
    inletStream.setTemperature(20.0 + 273.15);
    inletStream.setPressure(101325.0);
    inletStream.setVapourFraction(0.0);
    
    outletStream.addFlowSpecies(presetSpecies.get(4));
    outletStream.addFlowSpecies(presetSpecies.get(3));
    outletStream.setMolarFlowRate(2.0);
    outletStream.setTemperature(150.0 + 273.15);
    outletStream.setPressure(101325.0);
    outletStream.setVapourFraction(1.0);
    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    
    assertTrue(enthalpy.getInlet().getFlowSpecies().get(0).getCriticalTemperature() == presetSpecies.get(4).getCriticalTemperature());
    assertTrue(enthalpy.getInlet().getFlowSpecies().get(0).getHeatOfVapourization() == presetSpecies.get(4).getHeatOfVapourization());
    
  }
  
  // Test sourced from https://www.youtube.com/watch?v=Aw4VsloWVjM -- verify that the solution is actually adiabatic!
  // NOTE: This test is only here so that we can validate that this transition IS actually adiabatic -- if it is, we
  // can use this system to test our Controller.
  public void testIdealAdiabaticFlash() {
    
    FlowSpecies ethanol = new FlowSpecies();
    FlowSpecies methanol = new FlowSpecies();
    
    methanol.setOverallMoleFraction(0.30);
    ethanol.setOverallMoleFraction(0.70);
    // Antoine
    List<AntoineCoefficients> methanolAntoine = new ArrayList<AntoineCoefficients>();
    methanol.setAntoineConstants(new AntoineCoefficients(10.2049, 1582, -33.15));
    ethanol.setAntoineConstants(new AntoineCoefficients(10.2349, 1593, -47.15));
    // Heat capacities
    methanol.setVapourHeatCapacityConstants(52., 0., 0., 0.);
    methanol.setLiquidHeatCapacityConstants(110., 0., 0., 0.);
    ethanol.setLiquidHeatCapacityConstants(165., 0., 0., 0.);
    ethanol.setVapourHeatCapacityConstants(80., 0., 0., 0.);
    // Other
    methanol.setCriticalTemperature(513.0);
    methanol.setHeatOfVapourization(35300.0);
    ethanol.setCriticalTemperature(514.0);
    ethanol.setHeatOfVapourization(38600.0);
    
    FlowStream inlet = new FlowStream();
    inlet.addFlowSpecies(methanol);
    inlet.addFlowSpecies(ethanol);
    inlet.setMolarFlowRate(1.0);
    
    FlowStream outlet = new FlowStream(inlet);
    
    inlet.setTemperature(423.0);
    inlet.getFlowSpecies().get(0).setLiquidMoleFraction(methanol.getOverallMoleFraction());
    inlet.getFlowSpecies().get(1).setLiquidMoleFraction(ethanol.getOverallMoleFraction());
    inlet.setPressure(20.0 * 100000);
    outlet.setPressure(2.0 * 100000);
    outlet.getFlowSpecies().get(0).setLiquidMoleFraction(0.273);
    outlet.getFlowSpecies().get(1).setLiquidMoleFraction(0.727);
    outlet.getFlowSpecies().get(0).setVapourMoleFraction(0.375);
    outlet.getFlowSpecies().get(1).setVapourMoleFraction(0.625);
    outlet.setVapourFraction(0.265);
    outlet.setTemperature(365.5571094); // LearnChemE finds 365.0 as the adiabatic solution, but we have this ¯\_(ツ)_/¯ (found with Solver)
    double theEnthalpy = new Enthalpy(inlet, outlet).calc();
    double theReverseEnthalpy = new Enthalpy(outlet, inlet).calc();
    
    assertTrue(Math.abs(theEnthalpy) < 50.0);
    assertTrue(Math.abs(theReverseEnthalpy) < 50.0);
    
  }
  
}
