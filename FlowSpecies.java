/**
 * Add flow data to Species object
 */
public class FlowSpecies extends Species {
  
  private double overallMoleFraction = 0.0;
  private double liquidMoleFraction = 0.0;
  private double vapourMoleFraction = 0.0;
  
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
   }
  
  // Setters
  public boolean setOverallMoleFraction(double overallMoleFraction) {
    if (overallMoleFraction >= 0.0 && overallMoleFraction <= 1.0) {
      this.overallMoleFraction = overallMoleFraction;
      return true;
    } else {
      return false;
    }
  };
  
  public boolean setLiquidMoleFraction(double liquidMoleFraction, boolean restrict) {
    if(restrict==true) {
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
    setLiquidMoleFraction(liquidMoleFraction, restrict);
    return true; //will returning output of above method still set liquid mole fraction?
  }
  
  public boolean setVapourMoleFraction(double vapourMoleFraction) {
    if (vapourMoleFraction >= 0.0 && vapourMoleFraction <= 1.0) {
      this.vapourMoleFraction = vapourMoleFraction;
      return true;
    } else {
      return false;
    }
  };
  
  
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
  
  //Equals
  public boolean equals(FlowSpecies other) {
    super.equals(other);
    if(this.overallMoleFraction == other.overallMoleFraction &&
       this.liquidMoleFraction == other.liquidMoleFraction &&
       this.vapourMoleFraction == other.vapourMoleFraction) return true;
    else return false;
  }
  
  // Clone method
  public FlowSpecies clone() {
    return new FlowSpecies(this);
  }
  
}
