/**
 * Add flow data to Species object
 */
public class FlowSpecies extends Species {
  
  private double overallMoleFraction = 0.0;
  private double liquidMoleFraction = 0.0;
  private double vapourMoleFraction = 0.0;
  private double vapourFraction = 0.0;
  
  public FlowSpecies() {
    super();
    //return true;
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
  
  public boolean setVapourFraction(double vapourFraction) {
    if (vapourFraction >= 0.0 && vapourFraction <= 1.0) {
      this.vapourFraction = vapourFraction;
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
  
  public double getVapourFraction() {
    return this.vapourFraction;
  }
  
}
