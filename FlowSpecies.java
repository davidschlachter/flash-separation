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
  
  public boolean setLiquidMoleFraction(double liquidMoleFraction) {
    if (liquidMoleFraction >= 0.0 && liquidMoleFraction <= 1.0) {
      this.liquidMoleFraction = liquidMoleFraction;
      return true;
    } else {
      return false;
    }
  };
  
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
  
  // Clone method
  public FlowSpecies clone() {
    return new FlowSpecies(this);
  }
  
}
