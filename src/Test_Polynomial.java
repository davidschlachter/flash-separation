import junit.framework.TestCase;

public class Test_Polynomial extends TestCase {
  
  public void testConstructor() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    assertTrue("new Polynomial", testQuadratic != null);
    assertFalse("new Polynomial", testQuadratic == null);
  }
  
  // Tests copy constructor, clone method and equals
  public void testEquals() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    Polynomial equalTestQuadratic = testQuadratic.clone();
    double[] difCoeffs = {7, 10, -2};
    Polynomial inequalTestQuadratic = new Polynomial(difCoeffs);
    assertTrue("Polynomial.clone()", testQuadratic.equals(equalTestQuadratic));
    assertFalse("Polynomial.clone()", testQuadratic.equals(inequalTestQuadratic));
  }
  
  public void testAccessors() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double[] equalCoeffs = testQuadratic.getCoeffs();
    assertTrue("Polynomial.getCoeffs()", coeffs[0] == equalCoeffs[0]);
    assertTrue("Polynomial.getCoeffs()", coeffs[1] == equalCoeffs[1]);
    assertTrue("Polynomial.getCoeffs()", coeffs[2] == equalCoeffs[2]);
    assertTrue("Polynomial.getACoeff()", coeffs[0] == testQuadratic.getACoeff(0));
    assertTrue("Polynomial.getACoeff()", coeffs[1] == testQuadratic.getACoeff(1));
    assertTrue("Polynomial.getACoeff()", coeffs[2] == testQuadratic.getACoeff(2));
  }
  
  public void testMutators() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double[] difCoeffs = {7, 10, -2};
    testQuadratic.setCoeffs(difCoeffs);
    double[] equalCoeffs = testQuadratic.getCoeffs();
    assertTrue("Polynomial.setCoeffs()", difCoeffs[0] == equalCoeffs[0]);
    assertTrue("Polynomial.setCoeffs()", difCoeffs[1] == equalCoeffs[1]);
    assertTrue("Polynomial.setCoeffs()", difCoeffs[2] == equalCoeffs[2]);
    
    double x = 12.0;
    testQuadratic.setACoeff(x, 0);
    assertTrue("Polynomial.setACoeff()", x == testQuadratic.getACoeff(0));
  }
  
  public void testTestFunction() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double x = 6.0;
    assertTrue("Polynomial.testFunction()", testQuadratic.testFunction(x) > 2.9 && testQuadratic.testFunction(x) < 3.1);
  }
  
  public void testTestDerivative() {
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double x = 6.0;
    assertTrue("Polynomial.testDerivative()", testQuadratic.testDerivative(x) > -14.1 && testQuadratic.testDerivative(x) < -13.9);
  }
}