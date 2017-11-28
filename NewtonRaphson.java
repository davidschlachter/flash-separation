public class NewtonRaphson extends RootFinder {
  
  // Non-verbose Newton-Raphson method
  public static double calc(DifferentiableFunction f, double initialGuess, double accuracy) {
    return NewtonRaphson.calc(f, initialGuess, accuracy, false);
  }
  
  //Static method to find the root of a Function's testFunction method
  public static double calc(DifferentiableFunction f, double initialGuess, double accuracy, boolean verbose) { //input accuracy as %
    double error;
    double[] x = new double[2];
    double testFunction, testDerivative;
    x[0] = initialGuess;
    
    //Newton-Raphson method
    for(int i=0; i<JMAX; i++) {
      testFunction = f.testFunction(x[0]);
      testDerivative = f.testDerivative(x[0]);
      x[1] = x[0] - testFunction/testDerivative;  
      error = 100*Math.abs((x[1]-x[0])/x[1]);
      if (verbose == true) System.out.println("NewtonRaphson: at x[0]="+x[0]+" f="+testFunction+" and f'="+testDerivative+", giving x[1]="+x[1]);
      if(error<accuracy) return x[1];
      else x[0] = x[1];
    }
    
    if (verbose == true) System.out.println("WARNING: Exceeded maximum number of iterations!");
    return x[1];
  } 
  
}
