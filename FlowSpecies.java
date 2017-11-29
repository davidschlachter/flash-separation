/**
 * Add flow data to Species object
 */
public class FlowSpecies extends Species {
  
  private double overallMoleFraction = 0.0;
  private double liquidMoleFraction = 0.0;
  private double vapourMoleFraction = 0.0;
  
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
  
  //Constructor
  public FlowSpecies() {
    super();
  }
  
  //Copy Constructor
  public FlowSpecies (FlowSpecies source) {
    super(source);
    this.overallMoleFraction = source.overallMoleFraction;
    this.liquidMoleFraction = source.liquidMoleFraction;
    this.vapourMoleFraction = source.vapourMoleFraction;
    this.kappa = source.kappa;
    this.alpha = source.alpha;
    this.ai = source.ai;
    this.bi = source.bi;
    this.speciesA = source.speciesA;
    this.speciesB = source.speciesB;
    this.vapourFugacity = source.vapourFugacity;
    this.liquidFugacity = source.liquidFugacity;
   }
  
  // Setters
  public boolean setOverallMoleFraction(double overallMoleFraction) {
    if (overallMoleFraction >= 0.0 && overallMoleFraction <= 1.0) {
      this.overallMoleFraction = overallMoleFraction;
      return true;
    } else {
      return false;
    }
  }
  
  public boolean setLiquidMoleFraction(double liquidMoleFraction, boolean restrict) {
    if(restrict) {
      if (liquidMoleFraction >= 0.0 && liquidMoleFraction <= 1.0) {
        this.liquidMoleFraction = liquidMoleFraction;
        return true;
      } else {
        return false;
      }
    }
    else {
      this.liquidMoleFraction = liquidMoleFraction;
      return true;
    }
  }
  
  public boolean setLiquidMoleFraction(double liquidMoleFraction) {
    boolean restrict = true;
    return setLiquidMoleFraction(liquidMoleFraction, restrict);
    //return true; //will returning output of above method still set liquid mole fraction?
  }
  
  public boolean setVapourMoleFraction(double vapourMoleFraction) {
    if (vapourMoleFraction >= 0.0 && vapourMoleFraction <= 1.0) {
      this.vapourMoleFraction = vapourMoleFraction;
      return true;
    } else {
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
  
  //Equals
  public boolean equals(FlowSpecies other) {
    super.equals(other);
    if(this.overallMoleFraction == other.overallMoleFraction &&
       this.liquidMoleFraction == other.liquidMoleFraction &&
       this.vapourMoleFraction == other.vapourMoleFraction &&
       this.kappa == other.kappa &&
       this.alpha == other.alpha &&
       this.ai == other.ai &&
       this.bi == other.bi &&
       this.speciesA == other.speciesA &&
       this.liquidFugacity == other.liquidFugacity &&
       this.vapourFugacity == other.vapourFugacity) return true;
    else return false;
  }
  
  // Clone method
  public FlowSpecies clone() {
    return new FlowSpecies(this);
  }
  
}