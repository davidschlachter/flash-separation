public class HeatCapacity {
  
  public static double integrate(Species species, double initialTemperature, double finalTemperature, String state) throws IllegalArgumentException {
    
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
    
    // Check if all the constants for integration are empty
    boolean areEmpty = true;
    int i;
    for (i = 0; i < constants.length; i++) {if (constants[0] != 0.0) areEmpty = false;}
    if(areEmpty) throw new IllegalArgumentException("Error! No constants available to perform integration!");
    
    double result = 0.0;
    
    result += constants[0] * (finalTemperature - initialTemperature);
    result += 0.5 * (constants[1] * (finalTemperature*finalTemperature - initialTemperature*initialTemperature));
    result += (1.0/3.0) * (constants[2] * (finalTemperature*finalTemperature*finalTemperature - initialTemperature*initialTemperature*initialTemperature));
    result += (-1.0 * constants[3]) * ((1.0/finalTemperature) - (1.0/initialTemperature));
    
    return result;
  }
  
}