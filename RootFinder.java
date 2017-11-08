public class RootFinder {
 
  public static final int JMAX = 100;
  
  // Static method to find the root of a Function's testFunction method
  public static double calc(Function func, double x1, double x2, double xacc) {
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
      xR = xM + (xM - xL)*(Math.signum(fL - fU)*fM)/(Math.sqrt(fM*fM - fL*fU));
      fR = func.testFunction(xR);
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
    
    System.out.println("WARNING: Exceeded maximum number of iterations!");
    return xR;
  }
}
