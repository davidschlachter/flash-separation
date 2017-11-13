import junit.framework.TestCase;

public class Test_NewtonRaphson extends TestCase {
  
  public void testCalc() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double root1 = NewtonRaphson.calc(testQuadratic, 0, 0.0001);
    double root2 = NewtonRaphson.calc(testQuadratic, 5, 0.0001);
    assertTrue("NewtonRaphson.calc()", root1 > -1.2081 && root1 < -1.2080);
    assertTrue("NewtonRaphson.calc()", root2 > 6.2080 && root2 < 6.2081);
  }
}