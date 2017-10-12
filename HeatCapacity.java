public class HeatCapacity {
  
  public static double integrate(Species species, double initialTemperature, double finalTemperature, String state) {
  
    double[] constants;
    
    if (state.equals("liquid")) {
      constants = species.getLiquidHeatCapacityConstants();
    } else if (state.equals("vapour")) {
      constants = species.getVapourHeatCapacityConstants();
    } else {
      System.out.println("ERROR: State of heat capacity fluid (liquid/vapour) could not be determined!");
      constants = new double[] {0.0}; // So that constants is always initialized -- avoids compiler error
      System.exit(1);
    }
    double gasConstant = 8.3145;
    
    double result = 0.0;
    
    result += constants[0] * (finalTemperature - initialTemperature);
    result += 0.5 * (constants[1] * (finalTemperature*finalTemperature - initialTemperature*initialTemperature));
    result += (1.0/3.0) * (constants[2] * (finalTemperature*finalTemperature*finalTemperature - initialTemperature*initialTemperature*initialTemperature));
    result += (-1.0 * constants[3]) * ((1.0/finalTemperature) - (1.0/initialTemperature));
    
    return result * gasConstant;
  }
  
}
