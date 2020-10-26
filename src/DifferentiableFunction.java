/**
 * Interface for root finding on functions with a defined derivative (Newton-Raphson method)
 */

public interface DifferentiableFunction extends Function {
  
  public double testDerivative(double x);
  
}