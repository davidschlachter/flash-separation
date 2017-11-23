import junit.framework.TestCase;

public class Test_DewPressure extends TestCase {
  
  public void testSolve() {
    
    FlowStream testStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.55);
    water.setCriticalTemperature(647.0);
    water.setCriticalPressure(220550000.0);
    water.setAcentricFactor(0.345);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.45);
    ethanol.setCriticalTemperature(514.0);
    ethanol.setCriticalPressure(6148000.0);
    ethanol.setAcentricFactor(0.645);
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setTemperature(298.0);
    
    DewPressure testDewPressure = new DewPressure(testStream);
    double dewPressure = testDewPressure.solve();
    
    System.out.println("BEGINNING OF DEW PRESSURE TEST");
    System.out.println("The dew pressure was found to be: "+dewPressure+" Pascals.");
    System.out.println("END OF DEW PRESSURE TEST");
  }
  
} 
