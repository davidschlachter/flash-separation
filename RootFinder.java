public class RootFinder {
  
  /* 
  * TODO: Need to implement a root finding method ourselves (but using the same API defined here :)
  * This one is copied verbatim from http://bit.ly/2xQCn5z. Our method should be able to pass the test
  * already defined for the calc method! Also, bound-checking should be done intelligently as well  :)
  */
  
  public static final int JMAX = 100;
  
  public static double calc(Function func, double x1, double x2, double xacc) {

    //Completely commented out because I'm honestly not sure what each thing does
    /*double dx, xmid, rtb;
    double f = func.testFunction(x1);
    double fmid = func.testFunction(x2);
    if (f*fmid >= 0.0) {
      System.out.println("ERROR: The root appears not to be bracketed. Root finding will likely fail.");
    }
    if (f < 0.0) {
      dx = x2 - x1;
      rtb = x1;
    } else {
      dx = x1 - x2;
      rtb = x2;
    }
    for (int j=0; j < JMAX; j++) {
      dx *= 0.5;
      xmid = rtb + dx;
      fmid = func.testFunction(xmid);
      if (fmid <= 0.0) rtb = xmid;
      if (Math.abs(dx) < xacc || fmid == 0.0) return rtb;
    }
    System.out.println("WARNING: Exceeded maximum number of bisections!");
    return rtb;*/
    
    //Here is my initial (and likely inefficient) Ridder's Method code :)
    int maxIterations = 100;
    
    double xL, xU, xM, xR_old, xR, error;
    xL = x1;
    xU = x2;
    xR = 0;
    
    double fL, fU, fM, fR;
    
    for(int j=0; j < maxIterations; j++)
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
