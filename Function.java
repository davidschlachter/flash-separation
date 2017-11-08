/**
 * Abstract class for functions requiring root finding
 */

public abstract class Function {
  public static final int JMAX = 100;
  
  public abstract double testFunction(double x);
  
  public double[] getBounds(double initialGuess, double step) {
    double[] bounds = new double[2];
    bounds[0] = initialGuess;
    bounds[1] = initialGuess + step;
    
    double testProduct = 0.0;
    
    for(int i=0; i<JMAX; i++) {
      if((i/2)==0) bounds[0] -= step;
      else bounds[1] += step;
      
      testProduct = testFunction(bounds[0])*testFunction(bounds[1]);
      if(testProduct<0) return bounds;
    }
    
    System.out.println("WARNING: Exceeded maximum number of iterations.");
    return bounds;
  }
}
