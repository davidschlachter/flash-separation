public class AntoineCoefficients {
  
  private double a, b, c;
  private double lowerTemperatureBound, upperTemperatureBound;
  
  public AntoineCoefficients(double a, double b, double c, double lowerTemperatureBound, double upperTemperatureBound) {
    this.a = a;
    this.b = b;
    this.c = c;
    if(lowerTemperatureBound >= 0. && upperTemperatureBound >= 0){
      this.lowerTemperatureBound = lowerTemperatureBound;
      this.upperTemperatureBound = upperTemperatureBound;
    } else {
      System.out.println("Upper and lower temperature bounds must both be above absolute zero.");
      System.exit(1);
    }
  }
  
  public AntoineCoefficients(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public AntoineCoefficients(AntoineCoefficients source) {
    this.a = source.a;
    this.b = source.b;
    this.c = source.c;
    this.lowerTemperatureBound = source.lowerTemperatureBound;
    this.upperTemperatureBound = source.upperTemperatureBound;
  }
  
  // Getters and setters
  public void setA(double a) {
    this.a = a;
  }
  public void setB(double b) {
    this.b = b;
  }
  public void setC(double c) {
    this.c = c;
  }
  public void setLowerTemperatureBound(double lowerTemperatureBound) {
    this.lowerTemperatureBound = lowerTemperatureBound;
  }
  public void setUpperTemperatureBound(double upperTemperatureBound) {
    this.upperTemperatureBound = upperTemperatureBound;
  }
  
  public double getA() {
    return this.a;
  }
  public double getB() {
    return this.b;
  }
  public double getC() {
    return this.c;
  }
  public double getLowerTemperatureBound() {
    return this.lowerTemperatureBound;
  }
  public double getUpperTemperatureBound() {
    return this.upperTemperatureBound;
  }
  
}