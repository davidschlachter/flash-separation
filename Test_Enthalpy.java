import junit.framework.TestCase;

public class Test_Enthalpy extends TestCase {
  
  public void testConstructor() { 
    FlowStream inlet = new FlowStream();
    FlowStream outlet = new FlowStream();
    Enthalpy enthalpy = new Enthalpy(inlet, outlet);
    assertTrue("new Enthalpy(inlet, outlet)", enthalpy != null);
    assertFalse("new Enthalpy(inlet, outlet)", enthalpy == null);
  }
  
  public void testTestFunction() { 
     
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    water.setLiquidHeatCapacityConstants(3.47, 1.45, 0.0, 0.121);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    ethanol.setLiquidHeatCapacityConstants(33.866, -0.1726, 0.00034917, 0.0);
    
    FlowStream inletStream = new FlowStream();
    FlowStream outletStream = new FlowStream();
    
    outletStream.addFlowSpecies(water);
    outletStream.addFlowSpecies(ethanol);
    outletStream.setPressure(101325.0);
    outletStream.setTemperature(368.0);
    
    RachfordRice testRachfordRice = new RachfordRice(outletStream);

    outletStream = testRachfordRice.solve();
    
    inletStream.addFlowSpecies(water);
    inletStream.addFlowSpecies(ethanol);
    inletStream.setPressure(101325.0);

    
    Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
    double theEnthalpy = enthalpy.testFunction(360.0);
    
    assertTrue("Enthalpy.testFunction()", theEnthalpy > 29691.5 && theEnthalpy < 29691.7);

   
  }
  
}
