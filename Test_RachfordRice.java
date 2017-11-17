import junit.framework.TestCase;

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
  
  // Test the RachfordRice solution for a stream. Source: Excel calculation
  public void testSolution() {
    FlowStream testStream = new FlowStream();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(new AntoineCoefficients(10.19621302, 1730.63, -39.724, 304.0, 333.0));
    water.setOverallMoleFraction(0.5);
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(new AntoineCoefficients(9.80607302, 1332.04, -73.95, 364.8, 513.91));
    ethanol.setOverallMoleFraction(0.5);
    testStream.addFlowSpecies(water);
    testStream.addFlowSpecies(ethanol);
    testStream.setPressure(101325.0);
    testStream.setTemperature(368.0);
    
    RachfordRice testRachfordRice = new RachfordRice(testStream);
    FlowStream solvedFlowStream = testRachfordRice.solve();
    
    double waterLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getLiquidMoleFraction();
    double waterVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(0).getVapourMoleFraction();
    double ethanolLiquidMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = solvedFlowStream.getFlowSpecies().get(1).getVapourMoleFraction();
    
    assertTrue("RachfordRice.calc()", waterLiquidMoleFraction > 0.8338 && waterLiquidMoleFraction < 0.8358);
    assertTrue("RachfordRice.calc()", waterVapourMoleFraction > 0.6912 && waterVapourMoleFraction < 0.6932);
    assertTrue("RachfordRice.calc()", ethanolLiquidMoleFraction > 0.1642 && ethanolLiquidMoleFraction < 0.1662);
    assertTrue("RachfordRice.calc()", ethanolVapourMoleFraction > 0.3068 && ethanolVapourMoleFraction < 0.3088);
  }
  
  /*  public void testNonIdealSolution() {
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
                            
    testStream.addFlowSpecies(nHexane);
    testStream.addFlowSpecies(ethanol);
   // testStream.addFlowSpecies(mcp);
   // testStream.addFlowSpecies(benzene);
    testStream.setPressure(101325.0);
    testStream.setTemperature(334.15);
    testStream.setMolarFlowRate(1.0);
    
    int count = 0;
    
<<<<<<< HEAD
    Fugacity changedObject = new Fugacity(testStream);
    Fugacity unchangedObject = new Fugacity(testStream);
=======
    FlowStream solvedFlowStream = new FlowStream();
    Fugacity testObject = new Fugacity(testStream);
    Fugacity modifiedTestObject = new Fugacity(testStream);
    modifiedTestObject.computeNonIdealParameters(modifiedTestObject);  
    assertFalse(testObject.nonIdealComputed(modifiedTestObject));
    RachfordRice testRachfordRice = new RachfordRice(modifiedTestObject.getFlowStream());
    modifiedTestObject.computeNonIdealParameters();  
    assertFalse(testObject.getFlowStream().approxEquals(modifiedTestObject.getFlowStream(), 0.01));
    RachfordRice testRachfordRice = new RachfordRice(testObject.getFlowStream());
>>>>>>> ede2beb236c13c4207635d2f149cac9777044878
    
    do{
    changedObject.computeNonIdealParameters();
    unchangedObject.computeNonIdealParameters();
    RachfordRice solveXYs = new RachfordRice(changedObject.getFlowStream());
    changedObject.setFlowStream(solveXYs.solve());
    System.out.println("Solvexys activityCoefficient 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getActivityCoefficient());
    System.out.println("Solvexys activityCoefficient 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getActivityCoefficient());
        System.out.println("Solvexys fugacityCoefficient 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getMixtureFugacityCoefficient());
    System.out.println("Solvexys fugacityCoefficient 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getMixtureFugacityCoefficient());
        System.out.println("Solvexys beta 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getBeta());
    System.out.println("Solvexys beta 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getBeta());
        System.out.println("Solvexys QValue 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getQValue());
    System.out.println("Solvexys QValue 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getQValue());
        System.out.println("Solvexys Large phi 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getLargePhi());
    System.out.println("Solvexys Large Phi 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getLargePhi());
        System.out.println("Solvexys Z Value 1 is: "+solveXYs.getFlowStream().getFlowSpecies().get(0).getZValue());
    System.out.println("Solvexys ZValue 2 is: "+solveXYs.getFlowStream().getFlowSpecies().get(1).getZValue());
    changedObject.computeNonIdealParameters();
    }while(changedObject.nonIdealParamsDiff(unchangedObject) == );
    
    double nHexaneLiquidMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(0).getLiquidMoleFraction();
    double nHexaneVapourMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(0).getVapourMoleFraction();
    double nHexaneOverallMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(0).getOverallMoleFraction();
    double ethanolLiquidMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(1).getLiquidMoleFraction();
    double ethanolVapourMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(1).getVapourMoleFraction();
    double ethanolOverallMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(1).getOverallMoleFraction();
    double mcpLiquidMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(2).getLiquidMoleFraction();
    double mcpVapourMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(2).getVapourMoleFraction();
    double mcpOverallMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(2).getOverallMoleFraction();
    double benzeneLiquidMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(3).getLiquidMoleFraction();
    double benzeneVapourMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(3).getVapourMoleFraction();
    double benzeneOverallMoleFraction = changedObject.getFlowStream().getFlowSpecies().get(3).getOverallMoleFraction();
    
    System.out.println("nHexane Liquid mole fraction is: "+nHexaneLiquidMoleFraction);
    System.out.println("nHexane vapour mole fraction is: "+nHexaneVapourMoleFraction);
    System.out.println("nHexane overall mole fraction is: "+nHexaneOverallMoleFraction);
    System.out.println("ethanol Liquid mole fraction is: "+ethanolLiquidMoleFraction);
    System.out.println("ehhanol vapour mole fraction is: "+ethanolVapourMoleFraction);
    System.out.println("ethanol overall mole fraction is: "+ethanolOverallMoleFraction);
    System.out.println("mcp Liquid mole fraction is: "+mcpLiquidMoleFraction);
    System.out.println("mcp Liquid mole fraction is: "+mcpVapourMoleFraction);
    System.out.println("mcp Liquid mole fraction is: "+mcpOverallMoleFraction);
    System.out.println("benzene Liquid mole fraction is: "+benzeneLiquidMoleFraction);
    System.out.println("benzene vapour mole fraction is: "+benzeneVapourMoleFraction);
    System.out.println("benzene overall mole fraction is: "+benzeneOverallMoleFraction); 
    System.out.println("COUNT IS: "+count); 
    
    
    
    
   // assertTrue(nHexaneLiquidMoleFraction+ethanolLiquidMoleFraction+mcpLiquidMoleFraction+benzeneLiquidMoleFraction < 1.15);   //this test failing idiciates there is a problem with the summation term in rachford rice 
    
    assertTrue("RachfordRice.calc()", nHexaneLiquidMoleFraction > 0.15 && nHexaneLiquidMoleFraction < 0.17);
    assertTrue("RachfordRice.calc()", nHexaneVapourMoleFraction > 0.26 && nHexaneVapourMoleFraction < 0.28);
    assertTrue("RachfordRice.calc()", nHexaneOverallMoleFraction > 0.245 && nHexaneOverallMoleFraction < 0.255);
    assertTrue("RachfordRice.calc()", ethanolLiquidMoleFraction > 0.56 && ethanolLiquidMoleFraction < 0.58);
    assertTrue("RachfordRice.calc()", ethanolVapourMoleFraction > 0.35 && ethanolVapourMoleFraction < 0.37);
    assertTrue("RachfordRice.calc()", ethanolOverallMoleFraction > 0.38 && ethanolOverallMoleFraction < 0.42);
    assertTrue("RachfordRice.calc()", mcpLiquidMoleFraction > 0.12 && mcpLiquidMoleFraction < 0.14);
    assertTrue("RachfordRice.calc()", mcpVapourMoleFraction > 0.205 && mcpVapourMoleFraction < 0.225);
    assertTrue("RachfordRice.calc()", mcpOverallMoleFraction > 0.15 && mcpOverallMoleFraction < 0.25);
    assertTrue("RachfordRice.calc()", benzeneLiquidMoleFraction > 0.137 && benzeneLiquidMoleFraction < 0.147);
    assertTrue("RachfordRice.calc()", benzeneVapourMoleFraction > 0.147 && benzeneVapourMoleFraction < 0.157);
    assertTrue("RachfordRice.calc()", benzeneOverallMoleFraction > 0.125 && benzeneOverallMoleFraction < 0.175); 
  }
  
} */
  
  public void testLargerFlowStream(){
  
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
    testStream.setTemperature(364.15);

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
    
    System.out.println("x1: "+nHexaneLiquidMoleFraction);
    System.out.println("y1: "+nHexaneVapourMoleFraction);
    System.out.println("x2: "+ethanolLiquidMoleFraction);
    System.out.println("y2: "+ethanolVapourMoleFraction);
    System.out.println("x3: "+mcpLiquidMoleFraction);
    System.out.println("y3: "+mcpVapourMoleFraction);
    System.out.println("x4: "+benzeneLiquidMoleFraction);
    System.out.println("y4: "+benzeneVapourMoleFraction);
    
  }
  }