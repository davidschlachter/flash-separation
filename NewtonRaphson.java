public class NewtonRaphson extends RootFinder {
  
  //Static method to find the root of a Function's testFunction method
  public static double calc(DifferentiableFunction f, double initialGuess, double accuracy) { //input accuracy as %
    double error;
    double[] x = new double[2];
    x[0] = initialGuess;
    
    //Newton-Raphson method
    for(int i=0; i<JMAX; i++) {
      x[1] = x[0] - f.testFunction(x[0])/f.testDerivative(x[0]);
      error = 100*Math.abs((x[1]-x[0])/x[1]);
      if(error<accuracy) return x[1];
      else x[0] = x[1];
    }
    
    System.out.println("WARNING: Exceeded maximum number of iterations!");
    return x[1];
  } 
  
}
