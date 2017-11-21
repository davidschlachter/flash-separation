public class RiddersMethod extends RootFinder {
  
  // Static method to find the root of a Function's testFunction method
  public static double calc(Function func, double x1, double x2, double xacc) {
    // Run Ridders' method in non-verbose mode
    return RiddersMethod.calc(func, x1, x2, xacc, false);
  }
  
  
  
// Verbose version!
  public static double calc(Function func, double x1, double x2, double xacc, boolean verbose) {
    double xL, xU, xM, xR_old, xR, error;
    xL = x1;
    xU = x2;
    xR = 0;
    
    double fL, fU, fM, fR;
    
    for(int j=0; j < JMAX; j++)
    {
      xR_old = xR;
      xM = (xL + xU)/2.;
      fL = func.testFunction(xL);
      fU = func.testFunction(xU);
      fM = func.testFunction(xM);
      if (verbose == true) {
        System.out.println("Ridders Method: xL="+xL+" f(xL)="+fL+", xM="+xM+" f(xM)="+fM+", xU="+xU+" f(xU)="+fU);
      }
      xR = xM + (xM - xL)*(Math.signum(fL - fU)*fM)/(Math.sqrt(fM*fM - fL*fU));
      fR = func.testFunction(xR);
      if (Double.isNaN(xR)) {
        System.out.println("ERROR: xR in Ridder's method returned Not a Number (NaN)");
        return xR;
      }
      if(xR < xM)
      {
        if(fL*fR < 0) xU = xR;
        else if(fR*fM < 0)
        {
          xL = xR;
          xU = xM;
        }
        else xL = xR;
      }
      else
      {
        if(fL*fM < 0) xU = xM;
        else if(fM*fR < 0)
        {
          xL = xM;
          xU = xR;
        }
        else xL = xR;
      }
      error = Math.abs(xR - xR_old)/xR;
      if((j > 0) && (error < xacc)) return xR;
    }
    
    return xR;
  }
}