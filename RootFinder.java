public class RootFinder {
 
  //Max number of iterations
  public static final int JMAX = 100;
  
  //Static method to return bounds for a root  
  public static double[] getBounds(Function func, double initialGuess, double step) {
    double[] bounds = new double[2];
    bounds[0] = initialGuess;
    bounds[1] = initialGuess + step;
    
    double testProduct = 0.0;
    
    for(int i=0; i<JMAX; i++) {
      if((i%2)==0) bounds[0] -= step;
      else bounds[1] += step;
      
      testProduct = func.testFunction(bounds[0])*func.testFunction(bounds[1]);
      if(testProduct<0) return bounds;
    }
    
    System.out.println("WARNING: Exceeded maximum number of iterations.");
    return bounds;
  }
}
