import junit.framework.TestCase;

public class Test_RootFinder extends TestCase {
  
  public void testGetBounds() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double[] bounds1 = RootFinder.getBounds(testQuadratic, 0, 1);
    double[] bounds2 = RootFinder.getBounds(testQuadratic, 5, 1);
    assertTrue("RootFinder.lowerBound1", bounds1[0] > -2.1 && bounds1[0] < -1.9);
    assertTrue("RootFinder.upperBound1", bounds1[1] > 1.9 && bounds1[1] < 2.1);
    assertTrue("RootFinder.lowerBound2", bounds2[0] > 3.9 && bounds2[0] < 4.1);
    assertTrue("RootFinder.upperBound2", bounds2[1] > 6.9 && bounds2[1] < 7.1);
  }
}