/*
 * Species stores the physical properties of a given species
 * 
 */

public class Species {
  
  // Heat capacity constants
  private double c1 = 0;
  private double c2 = 0;
  private double c3 = 0;
  private double c4 = 0;
  
  // Antoine coefficients
  private double a = 0;
  private double b = 0;
  private double c = 0;
  
  // Heat of vapourization
  private heatOfVapourization = 0;
  
  // Activity coefficient (default value is 1 for an ideal solution)
  private activityCoefficient = 1;
  
  // default constructor method
  public boolean Species() {
    return true;
  }
  
}
