import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_FlowSpecies extends TestCase {
  
  /**
   * Test the getter and setter for the overall mole fraction
   */
  public void testOverallMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double overallMoleFraction = 0.5;
    assertEquals("Species.setOverallMoleFraction(0.5)", true, testSpecies.setOverallMoleFraction(overallMoleFraction));
    assertEquals("Species.setOverallMoleFraction(2.0)",  false,  testSpecies.setOverallMoleFraction(2.0));
    assertEquals("Species.setOverallMoleFraction(-2.0)", false, testSpecies.setOverallMoleFraction(-2.0));
    assertEquals("Species.getOverallMoleFraction()", overallMoleFraction, testSpecies.getOverallMoleFraction());
  }
  
  /**
  * Test the getter and setter for the liquid mole fraction
  */
  public void testLiquidMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double liquidMoleFraction = 0.5;
    assertEquals("Species.setLiquidMoleFraction(0.5)", true, testSpecies.setLiquidMoleFraction(liquidMoleFraction));
    assertEquals("Species.setLiquidMoleFraction(2.0)",  false,  testSpecies.setLiquidMoleFraction(2.0));
    assertEquals("Species.setLiquidMoleFraction(-2.0)", false, testSpecies.setLiquidMoleFraction(-2.0));
    assertEquals("Species.getLiquidMoleFraction()", liquidMoleFraction, testSpecies.getLiquidMoleFraction());
    
  }
  
  /**
  * Test the getter and setter for the gas mole fraction
  */
  public void testVapourMoleFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double vapourMoleFraction = 0.5;
    assertEquals("Species.setVapourMoleFraction(0.5)", true, testSpecies.setVapourMoleFraction(vapourMoleFraction));
    assertEquals("Species.setVapourMoleFraction(2.0)",  false,  testSpecies.setVapourMoleFraction(2.0));
    assertEquals("Species.setVapourMoleFraction(-2.0)", false, testSpecies.setVapourMoleFraction(-2.0));
    assertEquals("Species.getVapourMoleFraction()", vapourMoleFraction, testSpecies.getVapourMoleFraction());
    
  }
  
  /**
  * Test the getter and setter for the vapour fraction
  */
  public void testVapourFraction() {
    FlowSpecies testSpecies = new FlowSpecies();
    
    double vapourFraction = 0.5;
    assertEquals("Species.setVapourFraction(0.5)", true, testSpecies.setVapourFraction(vapourFraction));
    assertEquals("Species.setVapourFraction(2.0)",  false,  testSpecies.setVapourFraction(2.0));
    assertEquals("Species.setVapourFraction(-2.0)", false, testSpecies.setVapourFraction(-2.0));
    assertEquals("Species.getVapourFraction()", vapourFraction, testSpecies.getVapourFraction());
    
  }
  
}
