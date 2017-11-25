import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

public class Test_RachfordRice extends TestCase {
  
  public void testConstructor() {
    FlowStream testStream = new FlowStream();
    
    // Rachford-Rice object requires at least one species, and a specified pressure and temperature for the stream
    testStream.addFlowSpecies(new FlowSpecies());
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    
    assertTrue("new RachfordRice(testStream)", testRachfordRice != null);
    assertFalse("new RachfordRice(testStream)", testRachfordRice == null);
  }
  
  // Test that the testFunction returns expected values (source: manual calculation in Excel)
  public void testTestFunction() {
    FlowStream testStream = new FlowStream();
    
    // Test function result for water and ethanol at 1 atm, 368 K
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    testStream.setTemperature(368.0);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    // Test for a guessed V/F of 0.5
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    double testFunction = testRachfordRice.testFunction(0.5);
    
    assertTrue("RachfordRice.testFunction()", testFunction > 0.207 && testFunction < 0.209);
  }
  
  // Test the ideal Rachford Rice solution against this example from LearnChemE:
  // https://www.youtube.com/watch?v=bs2T5oCfRak
  public void testIdealLearnChemEFlash() {
    FlowSpecies component1 = new FlowSpecies();
    FlowSpecies component2 = new FlowSpecies();
    // Antoine coefficients converted with http://www.envmodels.com/freetools.php?menu=antoine
    component1.setAntoineConstants(new AntoineCoefficients(9.51442, 1307.22639, -23.15));
    component2.setAntoineConstants(new AntoineCoefficients(9.08012, 1172.5951, -68.15));
    // So that calculations work!
    component1.setCriticalTemperature(1000.0);
    component2.setCriticalTemperature(1000.0);
    component1.setOverallMoleFraction(0.60);
    component2.setOverallMoleFraction(0.40);
    List<FlowSpecies> species = new ArrayList<FlowSpecies>();
    species.add(component1);
    species.add(component2);
    
    FlowStream test = new FlowStream();
    test.setFlowSpecies(species);
    test.setTemperature(150+273.15);
    test.setPressure(1210*1000);
    test.setMolarFlowRate(1.0);
    
    test = new RachfordRice(test).solve();
    
    assertTrue(test.getFlowSpecies().get(0).getLiquidMoleFraction() > 0.52 &&
               test.getFlowSpecies().get(0).getLiquidMoleFraction() < 0.54);
    assertTrue(test.getFlowSpecies().get(0).getVapourMoleFraction() > 0.76 &&
               test.getFlowSpecies().get(0).getVapourMoleFraction() < 0.78);
    assertTrue(test.getVapourFraction() > 0.30 && test.getVapourFraction() < 0.32);
  }
  
  
    public void testNonIdealSolution() {
   FlowStream testStream = new FlowStream();
   
   FlowSpecies nHexane = new FlowSpecies();
   nHexane.setAntoineConstants(new AntoineCoefficients(9.00165, 1170.87529, -48.833, 1., 1000.));
   nHexane.setOverallMoleFraction(0.25);
   nHexane.setVapourMoleFraction(0.25);
   nHexane.setLiquidMoleFraction(0.25);
   nHexane.setCriticalTemperature(507.6);
   nHexane.setCriticalPressure(3025000.);
   nHexane.setCriticalZ(0.266);
   nHexane.setCriticalVolume(0.000371);
   nHexane.setAcentricFactor(0.301);
   FlowSpecies ethanol = new FlowSpecies();
   ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 1., 1000.));
   ethanol.setOverallMoleFraction(0.4);
   ethanol.setVapourMoleFraction(0.4);
   ethanol.setLiquidMoleFraction(0.4);
   ethanol.setCriticalTemperature(513.9);
   ethanol.setCriticalPressure(6148000.);
   ethanol.setCriticalZ(0.240);
   ethanol.setCriticalVolume(0.000167);
   ethanol.setAcentricFactor(0.645);
   FlowSpecies mcp = new FlowSpecies();
   mcp.setAntoineConstants(new AntoineCoefficients(8.98773, 1186.059, -47.108, 1., 1000.));
   mcp.setOverallMoleFraction(0.2);
   mcp.setVapourMoleFraction(0.2);
   mcp.setLiquidMoleFraction(0.2);
   mcp.setCriticalTemperature(532.73);
   mcp.setCriticalPressure(3784000.);
   mcp.setCriticalZ(0.272);
   mcp.setCriticalVolume(0.000318);
   mcp.setAcentricFactor(0.2302);
   FlowSpecies benzene = new FlowSpecies();
   benzene.setAntoineConstants(new AntoineCoefficients(8.9854, 1184.23854, -55.578, 1., 1000.));
   benzene.setOverallMoleFraction(0.15);
   benzene.setVapourMoleFraction(0.15);
   benzene.setLiquidMoleFraction(0.15);
   benzene.setCriticalTemperature(562.2);
   benzene.setCriticalPressure(4898000.);
   benzene.setCriticalZ(0.271);
   benzene.setCriticalVolume(0.000259);
   benzene.setAcentricFactor(0.210); 
    
   }
  
  public void testLargerFlowStream(){
    //System.out.println("\nStarting testLargerFlowStream");
    FlowStream testStream = new FlowStream();
    
    FlowSpecies nHexane = new FlowSpecies();
    nHexane.setAntoineConstants(new AntoineCoefficients(9.00165, 1170.87529, -48.833, 1., 1000.));
    nHexane.setOverallMoleFraction(0.25);
    nHexane.setCriticalTemperature(507.6);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 1., 1000.));
    ethanol.setOverallMoleFraction(0.4);
    ethanol.setCriticalTemperature(514);
    FlowSpecies mcp = new FlowSpecies();
    mcp.setAntoineConstants(new AntoineCoefficients(8.98773, 1186.059, -47.108, 1., 1000.));
    mcp.setOverallMoleFraction(0.2);
    mcp.setCriticalTemperature(532.8);
    FlowSpecies benzene = new FlowSpecies();
    benzene.setAntoineConstants(new AntoineCoefficients(8.9854, 1184.23854, -55.578, 1., 1000.));
    benzene.setOverallMoleFraction(0.15);
    benzene.setCriticalTemperature(562);
    
    testStream.addFlowSpecies(nHexane);
    testStream.addFlowSpecies(ethanol);
    testStream.addFlowSpecies(mcp);
    testStream.addFlowSpecies(benzene);
    testStream.setPressure(101325.0);
    testStream.setTemperature(348.5);
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    FlowStream solvedFlowStream = testRachfordRice.solve();
    
    double nHexaneLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getLiquidMoleFraction();
    double nHexaneVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getVapourMoleFraction();
    double mcpLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(2).getLiquidMoleFraction();
    double mcpVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(2).getVapourMoleFraction();
    double benzeneLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(3).getLiquidMoleFraction();
    double benzeneVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(3).getVapourMoleFraction();
    
    /*System.out.println("x1: "+nHexaneLiquidMoleFraction);
    System.out.println("y1: "+nHexaneVapourMoleFraction);
    System.out.println("x2: "+ethanolLiquidMoleFraction);
    System.out.println("y2: "+ethanolVapourMoleFraction);
    System.out.println("x3: "+mcpLiquidMoleFraction);
    System.out.println("y3: "+mcpVapourMoleFraction);
    System.out.println("x4: "+benzeneLiquidMoleFraction);
    System.out.println("y4: "+benzeneVapourMoleFraction);*/
    
  }

}