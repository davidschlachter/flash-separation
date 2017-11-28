import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

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
  
  // Critical Pressure for computation of non-ideal case
  private double criticalPressure = 0.0;
  
  // Accentricity value for computation of non-ideal case
  private double acentricFactor = 0.0;
  
  // Constructor
  public Species() {}
  
  // Copy constructor
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
  
  // Clone method
  public Species clone() {
    return new Species(this);
  }
  
  // Setters
  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }
  
  public void setVapourHeatCapacityConstants(double vapourHeatCapacityA, double vapourHeatCapacityB, 
                                             double vapourHeatCapacityC, double vapourHeatCapacityD) {
    this.vapourHeatCapacity[0] = vapourHeatCapacityA;
    this.vapourHeatCapacity[1] = vapourHeatCapacityB;
    this.vapourHeatCapacity[2] = vapourHeatCapacityC;
    this.vapourHeatCapacity[3] = vapourHeatCapacityD;
  }
  
  public void setLiquidHeatCapacityConstants(double liquidHeatCapacityA, double liquidHeatCapacityB, 
                                             double liquidHeatCapacityC, double liquidHeatCapacityD) {
    this.liquidHeatCapacity[0] = liquidHeatCapacityA;
    this.liquidHeatCapacity[1] = liquidHeatCapacityB;
    this.liquidHeatCapacity[2] = liquidHeatCapacityC;
    this.liquidHeatCapacity[3] = liquidHeatCapacityD;
  }
  
  public void setAntoineConstants(List<AntoineCoefficients> source) throws IllegalArgumentException {
    if(source==null) throw new IllegalArgumentException("Error! Antoine coefficients list input is empty.");
    this.antoineCoefficients = new ArrayList<AntoineCoefficients>();
    for (int i=0; i<source.size(); i++) {
      this.antoineCoefficients.add(new AntoineCoefficients(source.get(i)));
    }
  }
  
  public void setAntoineConstants(AntoineCoefficients source) throws IllegalArgumentException {
    if(source==null) throw new IllegalArgumentException("Error! Antoine coefficients input is empty.");
    this.antoineCoefficients = new ArrayList<AntoineCoefficients>();
    this.antoineCoefficients.add(new AntoineCoefficients(source));
  }
  
  public void setHeatOfVapourization(double heatOfVapourization) throws IllegalArgumentException {
    if (heatOfVapourization < 0.0) throw new IllegalArgumentException("Error! Heat of vapourization must be positive.");
    else this.heatOfVapourization = heatOfVapourization;
  }
  
  public void setCriticalTemperature (double criticalTemperature) throws IllegalArgumentException {
    if (criticalTemperature < 0.0) throw new IllegalArgumentException("Error!"+
                                                                      "Critical temperature [K] must be positive.");
    else this.criticalTemperature = criticalTemperature;
  }
  
  public void setCriticalPressure (double criticalPressure) throws IllegalArgumentException {
    if (criticalPressure < 0.0) throw new IllegalArgumentException("Error! Critical pressure [Pa] must be positive.");
    else this.criticalPressure = criticalPressure;
  }
  
  public void setAcentricFactor (double acentricFactor) throws IllegalArgumentException{
    if (acentricFactor < -1.0 || acentricFactor > 1.0 ) throw new IllegalArgumentException("Error!"+
                                                                                           "Acentric factor must be "+
                                                                                           "between -1.0 and 1.0.");
    else this.acentricFactor = acentricFactor;
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
  
  public double[] getAntoineConstants(double temperature) throws IllegalArgumentException {
    if(temperature<0) throw new IllegalArgumentException("Antoine constants could not be retrieved. "+
                                                         "Temperature [K] must be positive.");
    int i; // Number of Antoine coefficients
    int j=0; // Set number
    double[] allTimeMin = new double[2]; // allTimeMin stores an upper and a lower bound
    double[] allTimeMax = new double[2]; // allTimeMax stores an upper and a lower bound
    AntoineCoefficients thisSet;
    double[] antoineConstants = new double[3];
    
    for (i=0; i<this.antoineCoefficients.size(); i++) {
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
  public double getHeatOfVapourization(double temperature) throws IllegalArgumentException {
    if(temperature<0) throw new IllegalArgumentException("Heat of vapourization could not be calculated. "+
                                                         "Temperature [K] must be positive.");
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
}
