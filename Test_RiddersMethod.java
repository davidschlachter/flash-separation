import junit.framework.TestCase;

public class Test_RiddersMethod extends TestCase {
  
  public void testCalc() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double[] bounds1 = RootFinder.getBounds(testQuadratic, -1, 1);
    double[] bounds2 = RootFinder.getBounds(testQuadratic, 5, 1);
    double root1 = RiddersMethod.calc(testQuadratic, bounds1[0], bounds1[1], 0.0001);
    double root2 = RiddersMethod.calc(testQuadratic, bounds2[0], bounds2[1], 0.0001);
    
    // Note: using a starting point of 0 for root1 gives an element of (-1.2085, -1.2084) instead of (-1.2081, -1.2080)
    assertTrue("RiddersMethod.calc()", root1 > -1.2081 && root1 < -1.2080);
    assertTrue("RiddersMethod.calc()", root2 > 6.2080 && root2 < 6.2081);
  }
}