/*
 * Species stores the physical properties of a given species
 * 
 */

public class Species {
  
  // Species name
  private String speciesName;
  
  // Heat capacity constants
  private double heatCapacityA = 0;
  private double heatCapacityB = 0;
  private double heatCapacityC = 0;
  private double heatCapacityD = 0;
  
  // Antoine coefficients
  private double antoineA = 0;
  private double antoineB = 0;
  private double antoineC = 0;
  
  // Heat of vapourization
  private double heatOfVapourization = 0;
  
  // Activity coefficient (default value is 1 for an ideal solution)
  private double activityCoefficient = 1;

  // Critical temperature (to determine condensability)
  private double criticalTemperature = 0;

  // default constructor method
  public  Species() {
    //return true;
  }
  
  
  // Setters
  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }
  
  public void setHeatCapacityConstants(double heatCapacityA, double heatCapacityB, double heatCapacityC, double heatCapacityD) {
    this.heatCapacityA = heatCapacityA;
    this.heatCapacityB = heatCapacityB;
    this.heatCapacityC = heatCapacityC;
    this.heatCapacityD = heatCapacityD;
  }
  
  public void setAntoineConstants(double antoineA, double antoineB, double antoineC) {
    this.antoineA = antoineA;
    this.antoineB = antoineB;
    this.antoineC = antoineC;
  }
  
  public void setHeatOfVapourization(double heatOfVapourization) {
    this.heatOfVapourization = heatOfVapourization;
  }
  
  public boolean setActivityCoefficient(double activityCoefficient) {
    if (activityCoefficient > 0.0 && activityCoefficient <= 1.0) {
      this.activityCoefficient = activityCoefficient;
      return true;
    } else {
      return false;
    }
  }

  public boolean setCriticalTemperature (double criticalTemperature) {
    if (criticalTemperature > 0.0) {
      this.criticalTemperature = criticalTemperature;
      return true;
    } else {
      return false;
    }
  }
  
  // Getters
  public double[] getHeatCapacityConstants() {
    double[] heatCapacityConstants = new double[4];
    heatCapacityConstants[0] = this.heatCapacityA;
    heatCapacityConstants[1] = this.heatCapacityB;
    heatCapacityConstants[2] = this.heatCapacityC;
    heatCapacityConstants[3] = this.heatCapacityD;
    
    return heatCapacityConstants;
  }
  
  public double[] getAntoineConstants() {
    double[] antoineConstants = new double[3];
    antoineConstants[0] = this.antoineA;
    antoineConstants[1] = this.antoineB;
    antoineConstants[2] = this.antoineC;
    
    return antoineConstants;
  }
  
  public double getHeatOfVapourization() {
    return this.heatOfVapourization;
  }
  
  public double getActivityCoefficient() {
    return this.activityCoefficient;
  }
  
  public String getSpeciesName() {
    return this.speciesName;
  }

  public double getCriticalTemperature() {
    return this.criticalTemperature;
  }
  
}
