import junit.framework.TestCase;

public class Test_RootFinder extends TestCase {
  
  public void testRootFinders() {
    
    // Instanstiate a quadratic function
    double[] coeffs = {15, 10, -2};
    Polynomial testQuadratic = new Polynomial(coeffs);
    double error = 0.0001;
    
    // Test RootFinder.getBounds() method
    double[] bounds1 = RootFinder.getBounds(testQuadratic, 0, 1);
    double[] bounds2 = RootFinder.getBounds(testQuadratic, 5, 1);
    assertTrue("RootFinder.getBounds().lowerBound1", bounds1[0] > -2.1 && bounds1[0] < -1.9);
    assertTrue("RootFinder.getBounds().upperBound1", bounds1[1] > -1.1 && bounds1[1] < -0.9);
    assertTrue("RootFinder.getBounds().lowerBound2", bounds2[0] > 5.9 && bounds2[0] < 6.1);
    assertTrue("RootFinder.getBounds().upperBound2", bounds2[1] > 6.9 && bounds2[1] < 7.1);
    
    // Test Bisection.calc() method
    double bisectionRoot1 = Bisection.calc(testQuadratic, bounds1[0], bounds1[1], error);
    double bisectionRoot2 = Bisection.calc(testQuadratic, bounds2[0], bounds2[1], error);
    assertTrue("Bisection.calc().root1", bisectionRoot1 > -1.2082 && bisectionRoot1 < -1.2080); // less accurate
    assertTrue("Bisection.calc().root2", bisectionRoot2 > 6.2080 && bisectionRoot2 < 6.2082);
    
    // Test NewtonRaphson.calc() method
    double newtonRoot1 = RiddersMethod.calc(testQuadratic, bounds1[0], bounds1[1], error);
    double newtonRoot2 = RiddersMethod.calc(testQuadratic, bounds2[0], bounds2[1], error);
    assertTrue("NewtonRaphson.calc()", newtonRoot1 > -1.2081 && newtonRoot1 < -1.2080);
    assertTrue("NewtonRaphson.calc()", newtonRoot2 > 6.2080 && newtonRoot2 < 6.2081);
    
    // Test RiddersMethod.calc() method
    double riddersRoot1 = RiddersMethod.calc(testQuadratic, bounds1[0], bounds1[1], error);
    double riddersRoot2 = RiddersMethod.calc(testQuadratic, bounds2[0], bounds2[1], error);
    assertTrue("RiddersMethod.calc()", riddersRoot1 > -1.2081 && riddersRoot1 < -1.2080);
    assertTrue("RiddersMethod.calc()", riddersRoot2 > 6.2080 && riddersRoot2 < 6.2081);
  }
}