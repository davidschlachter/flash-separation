/**
 * Add flow data to Species object
 */
public class FlowSpecies extends Species {
  
  private double overallMoleFraction = 0;
  private double liquidMoleFraction = 0;
  private double gasMoleFraction = 0;
  
  public boolean FlowSpecies() {
    return true;
  }
  
}
