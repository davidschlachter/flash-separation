/**
 * Add flow data to Species object
 */
public class FlowSpecies extends Species {
  
  private double overallMoleFraction = 0.0;
  private double liquidMoleFraction = 0.0;
  private double gasMoleFraction = 0.0;
  private double vapourFraction = 0.0;
  
  public boolean FlowSpecies() {
    return true;
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
  
  public boolean setGasMoleFraction(double gasMoleFraction) {
    if (gasMoleFraction >= 0.0 && gasMoleFraction <= 1.0) {
      this.gasMoleFraction = gasMoleFraction;
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
  
  public double getGasMoleFraction() {
    return this.gasMoleFraction;
  }
  
  public double getVapourFraction() {
    return this.vapourFraction;
  }
  
}
