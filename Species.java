import java.util.ArrayList;
import java.util.List;

/*
 * Species stores the physical properties of a given species
 * 
 */

public class Species{
  
  // Species name
  private String speciesName;
  
  // Heat capacity constants
  private double[] vapourHeatCapacity = new double[4];
  private double[] liquidHeatCapacity = new double[4];
  
  // Antoine coefficients
  private List<AntoineCoefficients> antoineCoefficients;
  
  // Heat of vapourization
  private double heatOfVapourization = 0.0;
  
  // Critical temperature
  private double criticalTemperature = 0.0;
  
  //Critical Pressure for computation of non-ideal case
  private double criticalPressure = 0.0;
  
  
  //Accentricity value for computation of non-ideal case
  private double acentricFactor = 0.0;
  
  //individual component kapp values for PR calculations
  private double kappa = 0.0;
  
  //individual species alpha value for PR calculations
  private double alpha = 0.0;
  
  //individual a values for PR calculations
  private double ai = 0.0;
  
  //individual b value for PR calculations
  private double bi = 0.0;
  
  //individual LargeA for PR calcs
  private double speciesA = 0.0;
  
  //individual LargeA for PR calcs
  private double speciesB = 0.0;
  
  //individual vapour fugacity for PR
  private double vapourFugacity = 0.0;
  
  //individual liquid fugacity for PR
  private double liquidFugacity = 0.0;
  
  // Constructor
  public Species() {}
  
  //Copy Constructor
  public Species (Species source) {
    int i;
    this.speciesName = source.speciesName;
    this.vapourHeatCapacity[0] = source.vapourHeatCapacity[0];
    this.vapourHeatCapacity[1] = source.vapourHeatCapacity[1];
    this.vapourHeatCapacity[2] = source.vapourHeatCapacity[2];
    this.vapourHeatCapacity[3] = source.vapourHeatCapacity[3];
    this.liquidHeatCapacity[0] = source.liquidHeatCapacity[0];
    this.liquidHeatCapacity[1] = source.liquidHeatCapacity[1];
    this.liquidHeatCapacity[2] = source.liquidHeatCapacity[2];
    this.liquidHeatCapacity[3] = source.liquidHeatCapacity[3];
    if (source.antoineCoefficients != null) {
      this.antoineCoefficients = new ArrayList<AntoineCoefficients>();
      for (i = 0; i < source.antoineCoefficients.size(); i++) {
        this.antoineCoefficients.add(new AntoineCoefficients(source.antoineCoefficients.get(i)));
      }
    }
    this.heatOfVapourization = source.heatOfVapourization;
    this.criticalTemperature = source.criticalTemperature;
    this.criticalPressure = source.criticalPressure;
    this.acentricFactor = source.acentricFactor;
  }
  
  // Setters
  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }
  
  public void setVapourHeatCapacityConstants(double heatCapacityA, double heatCapacityB, double heatCapacityC, double heatCapacityD) {
    this.vapourHeatCapacity[0] = heatCapacityA;
    this.vapourHeatCapacity[1] = heatCapacityB;
    this.vapourHeatCapacity[2] = heatCapacityC;
    this.vapourHeatCapacity[3] = heatCapacityD;
  }
  
  public void setLiquidHeatCapacityConstants(double liquidHeatCapacityA, double liquidHeatCapacityB, double liquidHeatCapacityC, double liquidHeatCapacityD) {
    this.liquidHeatCapacity[0] = liquidHeatCapacityA;
    this.liquidHeatCapacity[1] = liquidHeatCapacityB;
    this.liquidHeatCapacity[2] = liquidHeatCapacityC;
    this.liquidHeatCapacity[3] = liquidHeatCapacityD;
  }
  
  public void setAntoineConstants(List<AntoineCoefficients> source) {
    int i;
    this.antoineCoefficients = new ArrayList<AntoineCoefficients>();
    for (i = 0; i < source.size(); i++) {
      this.antoineCoefficients.add(new AntoineCoefficients(source.get(i)));
    }
  }
  
  public void setAntoineConstants(AntoineCoefficients source) {
    this.antoineCoefficients = new ArrayList<AntoineCoefficients>();
    this.antoineCoefficients.add(new AntoineCoefficients(source));
  }
  
  public boolean setHeatOfVapourization(double heatOfVapourization) {
    if (heatOfVapourization >= 0.0) {
      this.heatOfVapourization = heatOfVapourization;
      return true;
    } else {
      System.out.println("Enter a positive value for heat of vapourization.");
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
  
  public boolean setCriticalPressure (double criticalPressure) {
    if (criticalPressure > 0.0) {
      this.criticalPressure = criticalPressure;
      return true;
    } else {
      System.out.println("All pressures must be in Pascals. Enter a positive value for Pressure."); //TODO: check units and make sure this is appropriate
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
  
  public void setKappa(double kappa){    // TODO: develop restrictions on setters for PR
    this.kappa = kappa;
  }
  
  public void setAlpha(double alpha){
    this.alpha = alpha;
  }
  
  public void setAI(double ai){
    this.ai = ai;
  }
  
  public void setBI(double bi){
    this.bi=bi;
  }
  
  public void setSpeciesA(double speciesA){
    this.speciesA = speciesA;
  }
  
  public void setSpeciesB(double speciesB){
    this.speciesB = speciesB;
  }
  
  public void setLiquidFugacity(double liquidFugacity){
    this.liquidFugacity = liquidFugacity;
  }
  
  public void setVapourFugacity(double vapourFugacity){
    this.vapourFugacity = vapourFugacity;
  }

  
  // Getters
  
  public double[] getVapourHeatCapacityConstants() {
    double[] vapourHeatCapacityConstants = new double[4];
    vapourHeatCapacityConstants[0] = this.vapourHeatCapacity[0];
    vapourHeatCapacityConstants[1] = this.vapourHeatCapacity[1];
    vapourHeatCapacityConstants[2] = this.vapourHeatCapacity[2];
    vapourHeatCapacityConstants[3] = this.vapourHeatCapacity[3];
    
    return vapourHeatCapacityConstants;
  }
  
  public double[] getLiquidHeatCapacityConstants() {
    double[] liquidHeatCapacityConstants = new double[4];
    liquidHeatCapacityConstants[0] = this.liquidHeatCapacity[0];
    liquidHeatCapacityConstants[1] = this.liquidHeatCapacity[1];
    liquidHeatCapacityConstants[2] = this.liquidHeatCapacity[2];
    liquidHeatCapacityConstants[3] = this.liquidHeatCapacity[3];
    
    return liquidHeatCapacityConstants;
  }
  
  public double[] getAntoineConstants(double temperature) {
    int i; // Number of Antoine coefficients
    int j=0; // Set number
    double[] allTimeMin = new double[2]; // allTimeMin stores an upper and a lower bound
    double[] allTimeMax = new double[2]; // allTimeMax stores an upper and a lower bound
    AntoineCoefficients thisSet;
    double[] antoineConstants = new double[3];
    
    for (i = 0; i < this.antoineCoefficients.size(); i++) {
      thisSet = this.antoineCoefficients.get(i);
      // Find a set of Antoine coefficients for the given temperature
      if (temperature >= thisSet.getLowerTemperatureBound() && temperature <= thisSet.getUpperTemperatureBound()) {
        antoineConstants[0] = thisSet.getA();
        antoineConstants[1] = thisSet.getB();
        antoineConstants[2] = thisSet.getC();
        return antoineConstants;
      }
      else {
        if (thisSet.getLowerTemperatureBound() < allTimeMin[1]) {
          allTimeMin[1] = thisSet.getLowerTemperatureBound();
          allTimeMin[0] = i;
        }
        else if (thisSet.getUpperTemperatureBound() > allTimeMax[1]) {
          allTimeMax[1] = thisSet.getUpperTemperatureBound();
          allTimeMax[0] = i;
        }
      }
    }
    // Check if the temperature is greater than stored max. and min. values
    if (temperature < allTimeMin[1]) {
      j = (int) Math.round(allTimeMin[0]);
    }
    else if (temperature > allTimeMax[1]) {
      j = (int) Math.round(allTimeMax[0]);
    }
    
    // If no match could be found, return the first set
    thisSet = this.antoineCoefficients.get(j);
    
    antoineConstants[0] = thisSet.getA();
    antoineConstants[1] = thisSet.getB();
    antoineConstants[2] = thisSet.getC();
    return antoineConstants;
  }
  
  public String getSpeciesName() {
    return this.speciesName;
  }
  
  public double getHeatOfVapourization() {
    return this.heatOfVapourization;
  }
  
  // Use the Watson correlation (see reference 5 in doi 10.1002/aic.690110226) to return the heat
  // of vapourization at a given temperature
  public double getHeatOfVapourization(double temperature) {
    double reducedTemperature = temperature/this.criticalTemperature;
    double standardReducedTemperature = 298.15/this.criticalTemperature;
    return Math.pow((1-reducedTemperature)/(1-standardReducedTemperature),0.38)*this.heatOfVapourization;
  }
  
  public double getCriticalTemperature() {
    return this.criticalTemperature;
  }
  
  public double getCriticalPressure() {
    return this.criticalPressure;
  }
  
  public double getAcentricFactor() {
    return this.acentricFactor;
  }
  
  public double getKappa(){
    return this.kappa;
  }
  
  public double getAlpha(){
    return this.alpha;
  }
  
  public double getAI(){
    return this.ai;
  }
  
  public double getBI(){
    return this.bi;
  }
  
  public double getSpeciesA(){
    return this.speciesA;
  }
  
  public double getSpeciesB(){
    return this.speciesB;
  }
  
  public double getLiquidFugacity(){
    return this.liquidFugacity;
  }
  
  public double getVapourFugacity(){
    return this.vapourFugacity;
  }
  
  
  //Equals
  public boolean equals(Species other) {
    
    if(this.antoineCoefficients.size() == other.antoineCoefficients.size()) {
      for(int i=0; i<this.antoineCoefficients.size(); i++) {
        if(this.antoineCoefficients.get(i).getA() != other.antoineCoefficients.get(i).getA() ||
           this.antoineCoefficients.get(i).getB() != other.antoineCoefficients.get(i).getB() ||
           this.antoineCoefficients.get(i).getC() != other.antoineCoefficients.get(i).getC() ||
           this.antoineCoefficients.get(i).getLowerTemperatureBound() != other.antoineCoefficients.get(i).getLowerTemperatureBound() ||
           this.antoineCoefficients.get(i).getUpperTemperatureBound() != other.antoineCoefficients.get(i).getUpperTemperatureBound()) {
          return false;
        }
      }
    }
    else return false;
    
    if(this.speciesName.equalsIgnoreCase(other.speciesName) &&
       this.vapourHeatCapacity[0] == other.vapourHeatCapacity[0] &&
       this.vapourHeatCapacity[1] == other.vapourHeatCapacity[1] &&
       this.vapourHeatCapacity[2] == other.vapourHeatCapacity[2] &&
       this.vapourHeatCapacity[3] == other.vapourHeatCapacity[3] &&
       this.liquidHeatCapacity[0] == other.liquidHeatCapacity[0] &&
       this.liquidHeatCapacity[1] == other.liquidHeatCapacity[1] &&
       this.liquidHeatCapacity[2] == other.liquidHeatCapacity[2] &&
       this.liquidHeatCapacity[3] == other.liquidHeatCapacity[3] &&
       this.heatOfVapourization == other.heatOfVapourization &&
       this.criticalTemperature == other.criticalTemperature &&
       this.criticalPressure == other.criticalPressure &&
       this.acentricFactor == other.acentricFactor) return true;
    else return false;
  }
  
  // Clone method
  public Species clone() {
    return new Species(this);
  }
  
}