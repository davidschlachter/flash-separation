

/*
 * Species stores the physical properties of a given species
 * 
 */

public class Species {
  
  // Species name
  private String speciesName;
  
  // Vapour heat capacity constants
  private double vapourHeatCapacityA = 0.0;
  private double vapourHeatCapacityB = 0.0;
  private double vapourHeatCapacityC = 0.0;
  private double vapourHeatCapacityD = 0.0;
  
  //Liquid heat capacity constants
  private double liquidHeatCapacityA= 0.0;
  private double liquidHeatCapacityB= 0.0;
  private double liquidHeatCapacityC= 0.0;
  private double liquidHeatCapacityD= 0.0;
  
  // Antoine coefficients
  private double antoineA = 0.0;
  private double antoineB = 0.0;
  private double antoineC = 0.0;
  
  // Activity coefficient (default value is 1 for an ideal solution)
  private double activityCoefficient = 1.0;
  
  // Critical temperature (to determine condensability)
  private double criticalTemperature = 0.0;
  
  // Constructor
  public Species() {}
  
  // Setters
  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }
  
  public void setVapourHeatCapacityConstants(double heatCapacityA, double heatCapacityB, double heatCapacityC, double heatCapacityD) {
    this.vapourHeatCapacityA = heatCapacityA;
    this.vapourHeatCapacityB = heatCapacityB;
    this.vapourHeatCapacityC = heatCapacityC;
    this.vapourHeatCapacityD = heatCapacityD;
  }
  
  public void setLiquidHeatCapacityConstants(double liquidHeatCapacityA, double liquidHeatCapacityB, double liquidHeatCapacityC, double liquidHeatCapacityD) {
    this.liquidHeatCapacityA = liquidHeatCapacityA;
    this.liquidHeatCapacityB = liquidHeatCapacityB;
    this.liquidHeatCapacityC = liquidHeatCapacityC;
    this.liquidHeatCapacityD = liquidHeatCapacityD;
  }
  
  public void setAntoineConstants(double antoineA, double antoineB, double antoineC) {
    this.antoineA = antoineA;
    this.antoineB = antoineB;
    this.antoineC = antoineC;
  }
  
  public boolean setActivityCoefficient(double activityCoefficient) {
    if (activityCoefficient > 0.0) {
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
      System.out.println("All temperatures must be in Kelvin. Enter a positive value for temperature.");
      return false;
    }
  }
  
  // Getters
  public double[] getVapourHeatCapacityConstants() {
    double[] vapourHeatCapacityConstants = new double[4];
    vapourHeatCapacityConstants[0] = this.vapourHeatCapacityA;
    vapourHeatCapacityConstants[1] = this.vapourHeatCapacityB;
    vapourHeatCapacityConstants[2] = this.vapourHeatCapacityC;
    vapourHeatCapacityConstants[3] = this.vapourHeatCapacityD;
    
    return vapourHeatCapacityConstants;
  }
  
  public double[] getLiquidHeatCapacityConstants() {
    double[] liquidHeatCapacityConstants = new double[4];
    liquidHeatCapacityConstants[0] = this.liquidHeatCapacityA;
    liquidHeatCapacityConstants[1] = this.liquidHeatCapacityB;
    liquidHeatCapacityConstants[2] = this.liquidHeatCapacityC;
    liquidHeatCapacityConstants[3] = this.liquidHeatCapacityD;
    
    return liquidHeatCapacityConstants;
  }
  
  public double[] getAntoineConstants() {
    double[] antoineConstants = new double[3];
    antoineConstants[0] = this.antoineA;
    antoineConstants[1] = this.antoineB;
    antoineConstants[2] = this.antoineC;
    return antoineConstants;
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
  
  // Clone method
  public Species (Species source) {
    this.speciesName = source.speciesName;
    this.vapourHeatCapacityA = source.vapourHeatCapacityA;
    this.vapourHeatCapacityB = source.vapourHeatCapacityB;
    this.vapourHeatCapacityC = source.vapourHeatCapacityC;
    this.vapourHeatCapacityD = source.vapourHeatCapacityD;
    this.liquidHeatCapacityA = source.liquidHeatCapacityA;
    this.liquidHeatCapacityB = source.liquidHeatCapacityB;
    this.liquidHeatCapacityC = source.liquidHeatCapacityC;
    this.liquidHeatCapacityD = source.liquidHeatCapacityD;
    this.antoineA = source.antoineA;
    this.antoineB = source.antoineB;
    this.antoineC = source.antoineC;
    this.activityCoefficient = source.activityCoefficient;
    this.criticalTemperature = source.criticalTemperature;
  }
  
  
  
}
