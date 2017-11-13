public class RootFinder {
 
  //Max number of iterations
  public static final int JMAX = 1000;
  
  //Static method to return bounds for a root  
  public static double[] getBounds(Function func, double initialGuess, double step) {
    double[] lowBounds = {initialGuess, initialGuess};
    double[] highBounds = {initialGuess, initialGuess};
    
    double testLowProduct = 0.0;
    double testHighProduct = 0.0;
    
    for(int i=0; i<JMAX; i++) {
      lowBounds[0] -= step;
      highBounds[1] += step;
      
      testLowProduct = func.testFunction(lowBounds[0])*func.testFunction(lowBounds[1]);
      testHighProduct = func.testFunction(highBounds[0])*func.testFunction(highBounds[1]);
      if(testLowProduct<0) return lowBounds;
      else if(testHighProduct<0) return highBounds;
      else {
        lowBounds[1] = lowBounds[0];
        highBounds[0] = highBounds[1];
      }
    }
    
    System.out.println("WARNING: Exceeded maximum number of iterations.");
    return highBounds; // have to figure out what a good idea to do here is
  }
}
