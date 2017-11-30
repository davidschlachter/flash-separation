import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

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
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    
    // Test that testFunction is throwing the exception appropriately
    double[] testFunction = {0.0, 0.0, 0.0};
    double[] vOverF = {-1.0, 0.5, 2.0}; // Test for a guessed V/F of 0.5
    boolean[] isThrown = {false, false, false};
    
    for(int i=0; i<isThrown.length; i++) {
      try {
        testFunction[i] = testRachfordRice.testFunction(vOverF[i]);
      }
      catch(IllegalArgumentException e) {
        isThrown[i] = true;
      }
    }
    
    assertTrue("RachfordRice.testFunction(illegalVOverF).exception", isThrown[0]);
    assertEquals("RachfordRice.testFunction(illegalVOverF).value", testFunction[0], 0.0);
    assertFalse("RachfordRice.testFunction(legalVOverF).exception", isThrown[1]);
    assertTrue("RachfordRice.testFunction(legalVOverF).value", testFunction[1] > 0.207 && testFunction[1] < 0.209);
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
    System.out.println("Starting testNonIdealSolution.");
    
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.2);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(2)));
    inletStream.getFlowSpecies().get(2).setOverallMoleFraction(0.5);
    
    inletStream.setIsIdeal(false);
    inletStream.setMolarFlowRate(10.0);
    inletStream.setPressure(101325.0);
    inletStream.setTemperature(41.49+273.15);
    
    
    FlowStream solvedStream = new RachfordRice(inletStream).solve();
    
    FlowStream idealStream = new FlowStream(inletStream);
    inletStream.setIsIdeal(true);
    solvedStream = new RachfordRice(inletStream).solve();
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
    
  }
  
  // Test our solution against one from the program provided by LearnChemE at
  // https://sourceforge.net/projects/chethermo/files/latest/download
  // We've independently validated this system with this Peng Robinson solver as well
  // http://people.ds.cam.ac.uk/pjb10/thermo/mixture.html
  public void testNonIdealValidatedSolution() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.7);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    
    inletStream.setIsIdeal(false);
    inletStream.setMolarFlowRate(1.0);
    inletStream.setPressure(100000.0); // 1 bar
    inletStream.setTemperature(254.0);
    
    FlowStream idealStream = new FlowStream(inletStream);
    idealStream.setIsIdeal(true);
    
    FlowStream solvedNonIdealStream = new RachfordRice(inletStream).solve();
    /*System.out.println("********************* NON-IDEAL RESULTS ****************************");
    ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), solvedNonIdealStream, solvedNonIdealStream);*/
    
    FlowStream solvedIdealStream = new RachfordRice(new FlowStream(idealStream)).solve();
    /*System.out.println("*********************   IDEAL RESULTS   ****************************");
    ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), solvedIdealStream, solvedIdealStream);*/
    
    // First, test expected results for the ideal solution
    assertTrue(Math.abs(solvedIdealStream.getVapourFraction() - 0.75207) < 0.01);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(0).getLiquidMoleFraction() - 0.062) < 0.01);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(0).getVapourMoleFraction() - 0.910) < 0.01);
    
    // Next, test for the expected non-ideal results
    assertTrue(Math.abs(solvedNonIdealStream.getVapourFraction() - 0.7538) < 0.01);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(0).getLiquidMoleFraction() - 0.0723) < 0.01);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(0).getVapourMoleFraction() - 0.9050) < 0.01);
  }
  
  // Test if we handle non-condensable components properly. Reference is UniSim, with
  // Antoine fluid package for ideal, and Peng-Robinson for non-ideal
  // Accuracy isn't too high here for the assert statements since UniSim tends to give unreproducible
  // results -- purpose of this test is to validate handling of non-condensing components rather than
  // validation against a particular solution
  public void testNonCondensingComponentSolution() {
    List<FlowSpecies> presetSpecies = PresetSpecies.get();
    FlowStream inletStream = new FlowStream();
    
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(0)));  // Ethane
    inletStream.getFlowSpecies().get(0).setOverallMoleFraction(0.5);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(1)));  // Pentane
    inletStream.getFlowSpecies().get(1).setOverallMoleFraction(0.3);
    inletStream.addFlowSpecies(new FlowSpecies(presetSpecies.get(5)));  // Nitrogen
    inletStream.getFlowSpecies().get(2).setOverallMoleFraction(0.2);
    
    inletStream.setIsIdeal(false);
    inletStream.setMolarFlowRate(1.0);
    inletStream.setPressure(100000.0); // 1 bar
    inletStream.setTemperature(254.0);
    
    FlowStream idealStream = new FlowStream(inletStream);
    idealStream.setIsIdeal(true);
    
    FlowStream solvedNonIdealStream = new RachfordRice(inletStream).solve();
    /*System.out.println("********************* NON-IDEAL RESULTS ****************************");
    ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), solvedNonIdealStream, solvedNonIdealStream);*/
    
    FlowStream solvedIdealStream = new RachfordRice(new FlowStream(idealStream)).solve();
    /*System.out.println("*********************   IDEAL RESULTS   ****************************");
    ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), solvedIdealStream, solvedIdealStream);*/
    
    // First, test expected results for the ideal solution
    assertTrue(Math.abs(solvedIdealStream.getVapourFraction() - 0.7577) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(0).getLiquidMoleFraction() - 0.0442) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(0).getVapourMoleFraction() - 0.6457) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(1).getLiquidMoleFraction() - 0.9553) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(1).getVapourMoleFraction() - 0.0904) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(2).getLiquidMoleFraction() - 0.0000) < 0.1);
    assertTrue(Math.abs(solvedIdealStream.getFlowSpecies().get(2).getVapourMoleFraction() - 0.2638) < 0.1);
    
    // Next, test for the expected non-ideal results
    assertTrue(Math.abs(solvedNonIdealStream.getVapourFraction() - 0.7603) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(0).getLiquidMoleFraction() - 0.0482) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(0).getVapourMoleFraction() - 0.6424) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(1).getLiquidMoleFraction() - 0.9513) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(1).getVapourMoleFraction() - 0.0947) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(2).getLiquidMoleFraction() - 0.0000) < 0.1);
    assertTrue(Math.abs(solvedNonIdealStream.getFlowSpecies().get(2).getVapourMoleFraction() - 0.2629) < 0.1);
  }
  
}