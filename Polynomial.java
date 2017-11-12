// A polynomial function class to test both root finding methods
public class Polynomial implements DifferentiableFunction {
  
  private double[] coeffs;
  
  // Constructor
  public Polynomial(double[] coeffs) {
    this.coeffs = new double[coeffs.length];
    for(int i=0; i<coeffs.length; i++) this.coeffs[i] = coeffs[i];
  }
  
  // Copy Constructor
  public Polynomial(Polynomial source) {
    this.coeffs = new double[source.coeffs.length];
    for(int i=0; i<source.coeffs.length; i++) this.coeffs[i] = source.coeffs[i];
  }
  
  // Clone method
  public Polynomial clone() {
    return new Polynomial(this);
  }
  
  // Accessors
  public double[] getCoeffs() {
    return this.coeffs;
  }
  public double getACoeff(int i) {
    return this.coeffs[i];
  }
  
  // Mutators
  public void setCoeffs(double[] coeffs) {
    this.coeffs = new double[coeffs.length];
    for(int i=0; i<coeffs.length; i++) this.coeffs[i] = coeffs[i];
  }
  public void setACoeff(double coeff, int i) {
    this.coeffs[i] = coeff;
  }
  
  // Equals method
  public boolean equals(Polynomial other) {
    if(other.coeffs == null) return false;
    else if(this.coeffs.length != other.coeffs.length) return false;
    else {
      for(int i=0; i<this.coeffs.length; i++) {
        if(this.coeffs[i] != other.coeffs[i]) return false;
      }
      return true;
    }
  }
  
  // Test function for root finding
  public double testFunction(double x) {
    double result = 0.0;
    for(int i=0; i<this.coeffs.length; i++) {
      result += this.coeffs[i]*Math.pow(x,i);
    }
    return result;
  }
  
  // Test derivative for Newton-Raphson root finding method
  public double testDerivative(double x) {
    double result = 0.0;
    for(int i=1; i<this.coeffs.length; i++) {
      result += this.coeffs[i]*i*Math.pow(x,i-1);
    }
    return result;
  }
}