public class Bisection extends RootFinder {
  
  public static double calc(Function func, double x1, double x2, double xacc) {
    return Bisection.calc(func, x1, x2, xacc, false);
  }
  
  public static double calc(Function func, double x1, double x2, double xacc, boolean verbose) {
    double dx, xmid, rtb;
    double f = func.testFunction(x1);
    double fmid = func.testFunction(x2);
    if (f*fmid >= 0.0) {
      if (verbose == true) System.out.println("ERROR: The root appears not to be bracketed. Root finding will likely fail.");
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
      if (verbose == true) System.out.println("xmid="+xmid+" fmid="+fmid);
      if (fmid <= 0.0) rtb = xmid;
      if (Math.abs(dx) < xacc || fmid == 0.0) return rtb;
    }
    if (verbose == true) System.out.println("WARNING: Exceeded maximum number of bisections!");
    return rtb;
    
  }
}