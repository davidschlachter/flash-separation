import junit.framework.TestCase;


public class Test_Controller extends TestCase {
  
  // Test the RachfordRice solution for a fully specified stream (T_in, T_out). Source: Excel calculation
  // (This test is identical testSolution in Test_RachfordRice)
  public void testBothTemperatures() {
    FlowStream outletStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
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
  
}
