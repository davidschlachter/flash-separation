import junit.framework.TestCase;

public class Test_FlowSpecies extends TestCase {
  
  public void testConstructor() {
    FlowSpecies testSpecies = new FlowSpecies();
    assertTrue("new FlowSpecies()", testSpecies != null);
    assertFalse("new FlowSpecies()", testSpecies == null);
  }
  
  public void testOverallMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double overallMoleFraction = 0.5;
    //assertTrue("Species.setOverallMoleFraction(0.5)", testSpecies.setOverallMoleFraction(overallMoleFraction));
    //assertFalse("Species.setOverallMoleFraction(2.0)", testSpecies.setOverallMoleFraction(2.0));
    //assertFalse("Species.setOverallMoleFraction(-2.0)", testSpecies.setOverallMoleFraction(-2.0));
    assertEquals("Species.getOverallMoleFraction()", overallMoleFraction, testSpecies.getOverallMoleFraction());
  }
  
  public void testLiquidMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double liquidMoleFraction = 0.5;
    //assertTrue("Species.setLiquidMoleFraction(0.5)", testSpecies.setLiquidMoleFraction(liquidMoleFraction));
    //assertFalse("Species.setLiquidMoleFraction(2.0)", testSpecies.setLiquidMoleFraction(2.0));
    //assertFalse("Species.setLiquidMoleFraction(-2.0)", testSpecies.setLiquidMoleFraction(-2.0));
    assertEquals("Species.getLiquidMoleFraction()", liquidMoleFraction, testSpecies.getLiquidMoleFraction());
    
  }
  
  public void testVapourMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double vapourMoleFraction = 0.5;
    //assertTrue("Species.setVapourMoleFraction(0.5)", testSpecies.setVapourMoleFraction(vapourMoleFraction));
    //assertFalse("Species.setVapourMoleFraction(2.0)", testSpecies.setVapourMoleFraction(2.0));
    //assertFalse("Species.setVapourMoleFraction(-2.0)", testSpecies.setVapourMoleFraction(-2.0));
    assertEquals("Species.getVapourMoleFraction()", vapourMoleFraction, testSpecies.getVapourMoleFraction());
    
  }
  
  public void testClone() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double vapourMoleFraction = 0.5;
    double liquidMoleFraction = 0.5;
    double overallMoleFraction = 0.5;
    
    testSpecies.setVapourMoleFraction(vapourMoleFraction);
    testSpecies.setLiquidMoleFraction(liquidMoleFraction);
    testSpecies.setOverallMoleFraction(overallMoleFraction);
    
    FlowSpecies copiedSpecies = testSpecies.clone();
    
    assertEquals("Species.getVapourMoleFraction()", vapourMoleFraction, copiedSpecies.getVapourMoleFraction());
    assertEquals("Species.getLiquidMoleFraction()", liquidMoleFraction, copiedSpecies.getLiquidMoleFraction());
    assertEquals("Species.getOverallMoleFraction()", overallMoleFraction, copiedSpecies.getOverallMoleFraction());
  }
  
  public void testEquals() {
    FlowSpecies testFlowSpecies = new FlowSpecies();
    testFlowSpecies.setVapourMoleFraction(0.5);
    testFlowSpecies.setLiquidMoleFraction(0.5);
    testFlowSpecies.setOverallMoleFraction(0.5);
    testFlowSpecies.setSpeciesName("test");
    testFlowSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testFlowSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    testFlowSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    testFlowSpecies.setCriticalTemperature(1.0);
    testFlowSpecies.setCriticalPressure(1.0);
    testFlowSpecies.setAcentricFactor(1.0);
    
    FlowSpecies equalFlowSpecies = new FlowSpecies();
    equalFlowSpecies.setVapourMoleFraction(0.5);
    equalFlowSpecies.setLiquidMoleFraction(0.5);
    equalFlowSpecies.setOverallMoleFraction(0.5);
    equalFlowSpecies.setSpeciesName("test");
    equalFlowSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    equalFlowSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    equalFlowSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    equalFlowSpecies.setCriticalTemperature(1.0);
    equalFlowSpecies.setCriticalPressure(1.0);
    equalFlowSpecies.setAcentricFactor(1.0);
    
    FlowSpecies inequalFlowSpecies = new FlowSpecies();
    inequalFlowSpecies.setVapourMoleFraction(0.1);
    inequalFlowSpecies.setLiquidMoleFraction(0.9);
    inequalFlowSpecies.setOverallMoleFraction(0.6);
    inequalFlowSpecies.setSpeciesName("test");
    inequalFlowSpecies.setVapourHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    inequalFlowSpecies.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    inequalFlowSpecies.setAntoineConstants(new AntoineCoefficients(1.0, 1.0, 1.0));
    inequalFlowSpecies.setCriticalTemperature(1.0);
    inequalFlowSpecies.setCriticalPressure(1.0);
    inequalFlowSpecies.setAcentricFactor(1.0);
    
    assertTrue("FlowSpecies.equals(equalSpecies)", testFlowSpecies.equals(equalFlowSpecies));
    assertFalse("FlowSpecies.equals(inequalSpecies)", testFlowSpecies.equals(inequalFlowSpecies));
  }
  
}
