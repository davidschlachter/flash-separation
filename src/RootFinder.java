public class RootFinder {
  
  //Max number of iterations
  public static final int JMAX = 1000;
  
  // Non-verbose getBounds that searches both forward and backward
  public static double[] getBounds(Function func, double initialGuess, double step) {
    return RootFinder.getBounds(func, initialGuess, step, false);
  }
  
  // Verbose getBounds that searches both forward and backward
  public static double[] getBounds(Function func, double initialGuess, double step, boolean verbose) {
    double[] lowBounds = {initialGuess, initialGuess};
    double[] highBounds = {initialGuess, initialGuess};
    
    double testLowProduct = 0.0;
    double testHighProduct = 0.0;
    
    for(int i=0; i<JMAX; i++) {
      lowBounds[0] -= step;
      highBounds[1] += step;
      testHighProduct = func.testFunction(highBounds[0])*func.testFunction(highBounds[1]);
      testLowProduct = func.testFunction(lowBounds[0])*func.testFunction(lowBounds[1]);
      
      if(testLowProduct<0) {
        if (verbose == true) System.out.println("Low bounds are: "+lowBounds[0]+" "+lowBounds[1]+" with product "+testLowProduct);
        return lowBounds;
      }
      else if(testHighProduct<0) {
        if (verbose == true) System.out.println("High bounds are: "+highBounds[0]+" "+highBounds[1]+" with product "+testHighProduct);
        return highBounds;
      }
      else {
        lowBounds[1] = lowBounds[0];
        highBounds[0] = highBounds[1];
      }
    }
    
    if (verbose == true) System.out.println("WARNING: Exceeded maximum number of iterations.");
    return highBounds;
  }
  
  public static double[] getBounds(Function func, double initialGuess, double step, double direction) {
  return RootFinder.getBounds(func, initialGuess, step, direction, false);
  }
  
  //Static method to return bounds for a root, searching either forward (true) or backward (false)
  public static double[] getBounds(Function func, double initialGuess, double step, double direction, boolean verbose) {
    double[] bounds = {initialGuess, initialGuess};
    double testProduct = 0.0;
    
    for(int i=0; i<JMAX; i++) {
      
      if (direction > 0.0) {
        bounds[1] += step;
      } else if (direction < 0.0) {
        bounds[0] -= step;
      } else {
        bounds[1] = (Math.pow(-1.0, i)*step)*i + bounds[0];
      }
      
      testProduct = func.testFunction(bounds[0])*func.testFunction(bounds[1]);
      if (verbose == true) System.out.println("Bounds are: "+bounds[0]+" "+bounds[1]+" with product "+testProduct);
      if (testProduct<0) {
        return bounds;
      }
      else {
        if (direction > 0.0) {
          bounds[0] = bounds[1];
        } else if (direction < 0.0) {
          bounds[1] = bounds[0];
        }
      }
    }
    
    if (verbose == true) System.out.println("WARNING: Exceeded maximum number of iterations.");
    return bounds;
  }
  
}