public class Incremental extends RootFinder {
  
  // Step through a test function from x1 to x2 taking a given number of steps,
  // printing x and y values pairs to the console
  //
  public static double calc(Function func, double x1, double x2, int steps) {
    double i;
    double f=0.0;
    // Do 1000 steps from x1 to x2
    for (i = x1; i < x2; i = i+((x2-x1)/steps)) {
      f = func.testFunction(i);
      System.out.println(i+"\t"+f);
    }
    System.out.println("WARNING: Exceeded maximum number of interval searches!");
    return f;
  }
  
  // Default: take 1000 steps
  public static double calc(Function func, double x1, double x2) {
    return Incremental.calc(func, x1, x2, 1000);
  } 
}