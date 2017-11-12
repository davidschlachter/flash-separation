import junit.framework.TestCase;

public class Test_RootFinder extends TestCase {
  
  public void testGetBounds() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double[] bounds1 = RootFinder.getBounds(testQuadratic, 0, 1);
    assertTrue("RootFinder.lowerBound", bounds1[0] > -2.1 && bounds1[0] < -1.9);
    assertTrue("RootFinder.upperBound", bounds1[1] > 1.9 && bounds1[1] < 2.1);
  }
}