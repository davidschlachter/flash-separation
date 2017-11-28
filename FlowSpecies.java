import java.lang.IllegalArgumentException;

/**
 * Add flow data to Species object
 */

public class FlowSpecies extends Species {
  
  // Mole fractions
  private double overallMoleFraction = 0.0;
  private double liquidMoleFraction = 0.0;
  private double vapourMoleFraction = 0.0;
  
  // Individual component kapp values for PR calculations
  private double kappa = 0.0;
  
  // Individual species alpha value for PR calculations
  private double alpha = 0.0;
  
  // Individual a values for PR calculations
  private double ai = 0.0;
  
  // Individual b value for PR calculations
  private double bi = 0.0;
  
  // Individual LargeA for PR calcs
  private double speciesA = 0.0;
  
  // Individual LargeA for PR calcs
  private double speciesB = 0.0;
  
  // Individual vapour fugacity for PR
  private double vapourFugacity = 0.0;
  
  // Individual liquid fugacity for PR
  private double liquidFugacity = 0.0;
  
  //Constructor
  public FlowSpecies() {
    super();
  }
  
  // Copy constructor
  public FlowSpecies (FlowSpecies source) throws IllegalArgumentException {
    super(source);
    if(overallMoleFraction < 0 ||
       liquidMoleFraction < 0 ||
       vapourMoleFraction < 0) throw new IllegalArgumentException("Could not construct flow species. "+
                                                                  "Mole fractions must be between 0 and 1.");
    else {
      this.overallMoleFraction = source.overallMoleFraction;
      this.liquidMoleFraction = source.liquidMoleFraction;
      this.vapourMoleFraction = source.vapourMoleFraction;
    }
  }
  
  // Clone method
  public FlowSpecies clone() {
    return new FlowSpecies(this);
  }
  
  // Setters
  public void setOverallMoleFraction(double overallMoleFraction) throws IllegalArgumentException {
    if(overallMoleFraction < 0.0 || overallMoleFraction > 1.0) {
      throw new IllegalArgumentException("Overall mole fraction could not be set. "+
                                         "Mole fractions must be between 0 and 1.");
    }
    else this.overallMoleFraction = overallMoleFraction;
  }
  
  public void setLiquidMoleFraction(double liquidMoleFraction) throws IllegalArgumentException {
    if(liquidMoleFraction < 0.0 || liquidMoleFraction > 1.0) {
      throw new IllegalArgumentException("Liquid mole fraction could not be set. "+
                                         "Mole fractions must be between 0 and 1.");
    }
    else this.liquidMoleFraction = liquidMoleFraction;
  }
  
  public void setVapourMoleFraction(double vapourMoleFraction) throws IllegalArgumentException {
    if (vapourMoleFraction < 0.0 && vapourMoleFraction > 1.0) {
      throw new IllegalArgumentException("Vapour mole fraction could not be set. "+
                                         "Mole fraction must be between 0 and 1.");
    }
    else this.vapourMoleFraction = vapourMoleFraction;
  }
  
  public void setKappa(double kappa){
    this.kappa = kappa;
  }
  
  public void setAlpha(double alpha){
    this.alpha = alpha;
  }
  
  public void setAI(double ai){
    this.ai = ai;
  }
  
  public void setBI(double bi){
    this.bi = bi;
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
  public double getOverallMoleFraction() {
    return this.overallMoleFraction;
  }
  
  public double getLiquidMoleFraction() {
    return this.liquidMoleFraction;
  }
  
  public double getVapourMoleFraction() {
    return this.vapourMoleFraction;
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
  
  // Equals
  public boolean equals(FlowSpecies other) {
    super.equals(other);
    if(this.overallMoleFraction == other.overallMoleFraction &&
       this.liquidMoleFraction == other.liquidMoleFraction &&
       this.vapourMoleFraction == other.vapourMoleFraction) return true;
    else return false;
  }
}
