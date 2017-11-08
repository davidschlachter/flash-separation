import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_FlowSpecies extends TestCase {
  
  /**
   * Test the constructor
   */
  public void testConstructor() {
    FlowSpecies testSpecies = new FlowSpecies();
    assertTrue("new FlowSpecies()", testSpecies != null);
    assertFalse("new FlowSpecies()", testSpecies == null);
  }
  
  /**
   * Test the getter and setter for the overall mole fraction
   */
  public void testOverallMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double overallMoleFraction = 0.5;
    assertTrue("Species.setOverallMoleFraction(0.5)", testSpecies.setOverallMoleFraction(overallMoleFraction));
    assertFalse("Species.setOverallMoleFraction(2.0)", testSpecies.setOverallMoleFraction(2.0));
    assertFalse("Species.setOverallMoleFraction(-2.0)", testSpecies.setOverallMoleFraction(-2.0));
    assertEquals("Species.getOverallMoleFraction()", overallMoleFraction, testSpecies.getOverallMoleFraction());
  }
  
  /**
  * Test the getter and setter for the liquid mole fraction
  */
  public void testLiquidMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double liquidMoleFraction = 0.5;
    assertTrue("Species.setLiquidMoleFraction(0.5)", testSpecies.setLiquidMoleFraction(liquidMoleFraction));
    assertFalse("Species.setLiquidMoleFraction(2.0)", testSpecies.setLiquidMoleFraction(2.0));
    assertFalse("Species.setLiquidMoleFraction(-2.0)", testSpecies.setLiquidMoleFraction(-2.0));
    assertEquals("Species.getLiquidMoleFraction()", liquidMoleFraction, testSpecies.getLiquidMoleFraction());
    
  }
  
  /**
  * Test the getter and setter for the gas mole fraction
  */
  public void testVapourMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double vapourMoleFraction = 0.5;
    assertTrue("Species.setVapourMoleFraction(0.5)", testSpecies.setVapourMoleFraction(vapourMoleFraction));
    assertFalse("Species.setVapourMoleFraction(2.0)", testSpecies.setVapourMoleFraction(2.0));
    assertFalse("Species.setVapourMoleFraction(-2.0)", testSpecies.setVapourMoleFraction(-2.0));
    assertEquals("Species.getVapourMoleFraction()", vapourMoleFraction, testSpecies.getVapourMoleFraction());
    
  }
  
  /**
  * Test the clone method
  */
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
  
}
