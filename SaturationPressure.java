/**
 * Auto Generated Java Class.
 */
public class SaturationPressure {
  
  public static double calc(Species species, double temperature) {
    double[] antoineConstants = species.getAntoineConstants(temperature);
    return(Math.pow(10.0, antoineConstants[0] - (antoineConstants[1]/(antoineConstants[2] + temperature))));
  }
  
}