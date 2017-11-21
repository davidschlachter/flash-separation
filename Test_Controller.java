import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

public class Test_Controller extends TestCase {
  
  // Test the RachfordRice solution for a fully specified ideal stream (T_in, T_out). Source: Excel calculation
  // (This test is identical testSolution in Test_RachfordRice)
  public void testIdealBothTemperatures() {
    FlowStream outletStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    water.setCriticalTemperature(647.0);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    ethanol.setCriticalTemperature(514.0);
    outletStream.addFlowSpecies(water);
    outletStream.addFlowSpecies(ethanol);
    outletStream.setPressure(101325.0);
    outletStream.setTemperature(368.0);
    
    FlowStream inletStream = new FlowStream(outletStream);
    
    Controller.calc(inletStream, outletStream);
    
    double waterLiquidMoleFraction = outletStream.getFlowSpecies().get(0).getLiquidMoleFraction();
    double waterVapourMoleFraction = outletStream.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = outletStream.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = outletStream.getFlowSpecies().get(1).getVapourMoleFraction();
    
    assertTrue("RachfordRice.calc()", waterLiquidMoleFraction > 0.8338 && waterLiquidMoleFraction < 0.8358);
    assertTrue("RachfordRice.calc()", waterVapourMoleFraction > 0.6912 && waterVapourMoleFraction < 0.6932);
    assertTrue("RachfordRice.calc()", ethanolLiquidMoleFraction > 0.1642 && ethanolLiquidMoleFraction < 0.1662);
    assertTrue("RachfordRice.calc()", ethanolVapourMoleFraction > 0.3068 && ethanolVapourMoleFraction < 0.3088);
  }
  
  // Test an ideal adiabatic flash. Source: https://www.youtube.com/watch?v=Aw4VsloWVjM and
  // https://www.youtube.com/watch?v=EhLpYbP9st0. Note that the adiabatic flash temperature has been adjusted
  // by about 1 K from the given example to match the results for this system in Test_Enthalpy.
  public void donttestIdealAdiabaticFlash() {
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
    
    Controller.calc(inlet, outlet);
    
    double methanolLiquidMoleFraction = outlet.getFlowSpecies().get(0).getLiquidMoleFraction();
    double methanolVapourMoleFraction = outlet.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = outlet.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = outlet.getFlowSpecies().get(1).getVapourMoleFraction();
    
    System.out.println("Outlet temperature for adiabatic flash: " + outlet.getTemperature());
    System.out.println("Mole fractions: " + methanolLiquidMoleFraction + " " + methanolVapourMoleFraction + " " +
                       ethanolLiquidMoleFraction + " " + ethanolVapourMoleFraction+"\n");
    
    assertTrue("Controller.calc()", outlet.getTemperature() > 366.45 && outlet.getTemperature() < 366.75); // Expecting 366.581
    assertTrue("Controller.calc()", ethanolLiquidMoleFraction > 0.72 && ethanolLiquidMoleFraction < 0.74);
    assertTrue("Controller.calc()", ethanolVapourMoleFraction > 0.61 && ethanolVapourMoleFraction < 0.63);
    assertTrue("Controller.calc()", methanolVapourMoleFraction > 0.37 && methanolLiquidMoleFraction < 0.39);
    assertTrue("Controller.calc()", methanolLiquidMoleFraction > 0.26 && methanolLiquidMoleFraction < 0.28);
  }
  
}
