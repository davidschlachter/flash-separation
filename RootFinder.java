public class RootFinder {
  
  /* 
  * TODO: Need to implement a root finding method ourselves (but using the same API defined here :)
  * This one is copied verbatim from http://bit.ly/2xQCn5z. Our method should be able to pass the test
  * already defined for the calc method! Also, bound-checking should be done intelligently as well  :)
  */
  
  
  public static final int JMAX = 100;
  
  public static double calc(Function func, double x1, double x2, double xacc) {

    double dx, xmid, rtb;
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
    return rtb;
    
  }
  
}
