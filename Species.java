

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
  
  // Critical temperature (to determine condensability)
  private double criticalTemperature = 0.0;
  
  //Critical Pressure for computation of non-ideal case
  private double criticalPressure = 0.0;
  
  //Critical volume for computation of non-ideal case
  private double criticalVolume = 0.0;
  
  //Critical Z-value for computation of non-ideal case
  private double criticalZ = 0.0;
  
  //Accentricity value for computation of non-ideal case
  private double acentricFactor = 0.0;
  
  //Z value for computation of individual fugacity coefficient. Do not confuse with criticalZ value
  private double zValue = 1.0;
  
  //Beta values for computation of individual fugacity coefficient. Not Bij values. 
  private double beta = 0.0;
  
  //Q values for computation of individual fugacity coefficient
  private double qValue = 0.0;
  
  //activity coefficient for nonideal liquid behaviour
  private double activityCoefficient = 0.0;
  
  //fugacity coefficient for nonideal behaviour
  private double mixtureFugacityCoefficient = 0.0;
  
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
  
  public boolean setCriticalTemperature (double criticalTemperature) {
    if (criticalTemperature > 0.0) {
      this.criticalTemperature = criticalTemperature;
      return true;
    } else {
      System.out.println("All temperatures must be in Kelvin. Enter a positive value for temperature.");
      return false;
    }
  }
  
  public boolean setCriticalPressure (double criticalPressure) {
    if (criticalPressure > 0.0) {
      this.criticalPressure = criticalPressure;
      return true;
    } else {
      System.out.println("All pressures must be in Pascals. Enter a positive value for Pressure."); //TODO: check units and make sure this is appropriate
      return false;
    }
  }
  
  public boolean setCriticalVolume (double criticalVolume) {
    if (criticalVolume > 0.0) {
      this.criticalVolume = criticalVolume;
      return true;
    } else {
      System.out.println("All specific volumes must be in mol/m3. Enter a positive value for critical specific volume.");
      return false;
    }
  }
  
  public boolean setCriticalZ (double criticalZ) {
    if (criticalZ > 0.0) {
      this.criticalZ = criticalZ;
      return true;
    } else {
      System.out.println("All Z-values must be positive.");
      return false;
    }
  }
  
  public boolean setAcentricFactor (double acentricFactor) {
    if (acentricFactor >= -1.0 && acentricFactor <= 1.0 ) {
      this.acentricFactor = acentricFactor;
      return true;
    } else {
      System.out.println("All accentricities must be between -1.0 and 1.0. Enter an appropriate value for the acentric factor.");
      return false;
    }
    
  }
  
  public boolean setZValue(double zValue){
    if(zValue > 0){
      this.zValue = zValue;
      return true;
    } else {
      System.out.println("Z value for a pure species cannot be 0.");
      return false;
    }
  }
  
  public boolean setBeta(double beta){
    if(beta > 0){
      this.beta = beta;
      return true;
    } else {
      System.out.println("Beta value must be positive.");
      return false;
    }
  }
  
  public boolean setQValue(double qValue){
    if(qValue > 0){
      this.qValue = qValue;
      return true;
    } else {
      System.out.println("Q value must be positive.");
      return false;
    }
  }
  
  public boolean setActivityCoefficient(double activityCoefficient){
    if(activityCoefficient > 0.0){
      this.activityCoefficient = activityCoefficient;
      return true;
    } else {
      System.out.println("Activity coefficient must be greater than 0.");
      return false;
    }
  }
  
  public boolean setMixtureFugacityCoefficient(double mixtureFugacityCoefficient){
    if(mixtureFugacityCoefficient > 0.0){
      this.mixtureFugacityCoefficient = mixtureFugacityCoefficient;
      return true;
    } else {
      System.out.println("Fugacity Coefficient must be greater than 0.");
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
  
  public String getSpeciesName() {
    return this.speciesName;
  }
  
  public double getCriticalTemperature() {
    return this.criticalTemperature;
  }
  
  public double getCriticalPressure() {
    return this.criticalPressure;
  }
  
  public double getCriticalVolume() {
    return this.criticalVolume;
  }
  
  public double getCriticalZ() {
    return this.criticalZ;
  }
  
  public double getAcentricFactor() {
    return this.acentricFactor;
  }
  
  public double getZValue(){
    return this.zValue;
  } 
  
  public double getBeta(){
    return this.beta;
  }
  
  public double getQValue(){
    return this.qValue;
  }
  
  public double getActivityCoefficient(){
    return this.activityCoefficient;
  }
  
  public double getMixtureFugacityCoefficient(){
    return this.mixtureFugacityCoefficient;
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
    this.criticalTemperature = source.criticalTemperature;
    this.criticalPressure = source.criticalPressure;
    this.criticalVolume = source.criticalVolume;
    this.criticalZ = source.criticalZ;
    this.acentricFactor = source.acentricFactor;
    this.zValue = source.zValue;
    this.beta = source.beta;
    this.qValue = source.qValue;
    this.activityCoefficient = source.activityCoefficient;
    this.mixtureFugacityCoefficient = source.mixtureFugacityCoefficient;
  }
  
  
  
}
