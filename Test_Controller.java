import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

public class Test_Controller extends TestCase {
  
  // Test the RachfordRice solution for a fully specified ideal stream (T_in, T_out). Source: LearnChemE
  // (This test is identical to testIdealLearnChemEFlash in Test_RachfordRice)
  public void testIdealBothTemperatures() {
    FlowSpecies component1 = new FlowSpecies();
    FlowSpecies component2 = new FlowSpecies();
    component1.setAntoineConstants(new AntoineCoefficients(9.51442, 1307.22639, -23.15));
    component2.setAntoineConstants(new AntoineCoefficients(9.08012, 1172.5951, -68.15));
    component1.setCriticalTemperature(1000.0); // Just so that it's condensable, not specified in original problem
    component2.setCriticalTemperature(1000.0);
    component1.setOverallMoleFraction(0.60);
    component2.setOverallMoleFraction(0.40);
    List<FlowSpecies> species = new ArrayList<FlowSpecies>();
    species.add(component1);
    species.add(component2);
    
    FlowStream outlet = new FlowStream();
    outlet.setFlowSpecies(species);
    outlet.setTemperature(150+273.15);
    outlet.setPressure(1210*1000);
    outlet.setMolarFlowRate(1.0);
    
    FlowStream inlet = new FlowStream(outlet);
    
    FlowStream[] processedStreams = Controller.calc(inlet, outlet);
    inlet = processedStreams[0];
    outlet = processedStreams[1];
    
    assertTrue(outlet.getFlowSpecies().get(0).getLiquidMoleFraction() > 0.52 &&
               outlet.getFlowSpecies().get(0).getLiquidMoleFraction() < 0.54);
    assertTrue(outlet.getFlowSpecies().get(0).getVapourMoleFraction() > 0.76 &&
               outlet.getFlowSpecies().get(0).getVapourMoleFraction() < 0.78);
    assertTrue(outlet.getVapourFraction() > 0.30 && outlet.getVapourFraction() < 0.32);
  }
  
  // Test an ideal adiabatic flash. Source: https://www.youtube.com/watch?v=Aw4VsloWVjM and
  // https://www.youtube.com/watch?v=EhLpYbP9st0. Note that the adiabatic flash temperature has been adjusted
  // by about 1 K from the given example to match the results for this system in Test_Enthalpy.
  public void testIdealAdiabaticFlash() {
    FlowSpecies ethanol = new FlowSpecies();
    FlowSpecies methanol = new FlowSpecies();
    
    ethanol.setSpeciesName("Ethanol");
    methanol.setSpeciesName("Methanol");
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
    
    FlowStream[] processedStreams = Controller.calc(inlet, outlet);
    inlet = processedStreams[0];
    outlet = processedStreams[1];
    
    double methanolLiquidMoleFraction = outlet.getFlowSpecies().get(0).getLiquidMoleFraction();
    double methanolVapourMoleFraction = outlet.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = outlet.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = outlet.getFlowSpecies().get(1).getVapourMoleFraction();
    
    assertTrue("Controller.calc()", Math.abs(outlet.getTemperature() - 365.5571) < 0.1); // Expecting 365.5571 (see Test_Enthalpy)
    assertTrue("Controller.calc()", ethanolLiquidMoleFraction > 0.72 && ethanolLiquidMoleFraction < 0.74);
    assertTrue("Controller.calc()", ethanolVapourMoleFraction > 0.61 && ethanolVapourMoleFraction < 0.63);
    assertTrue("Controller.calc()", methanolVapourMoleFraction > 0.37 && methanolLiquidMoleFraction < 0.39);
    assertTrue("Controller.calc()", methanolLiquidMoleFraction > 0.26 && methanolLiquidMoleFraction < 0.28);
  }
  
}
