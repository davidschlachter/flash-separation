/**
 * Class that returns the dew point of a stream of given composition at a given pressure
 */
public class DewPoint extends Function {
  
  private FlowStream flowStream;
  
  // Constructor -- requires a FlowStream with pressure specified 
  public DewPoint(FlowStream flowStream) {
    if (flowStream.getPressure() > 0 && flowStream.getFlowSpecies().size() > 0) {
      this.flowStream = flowStream;
    } else {
      System.out.println("Error: flow stream for must contain a pressure and at least one species!");
      System.exit(1);
    } 
  }
  
  
  // Return the dew point for the given flowStream components at the given pressure
  public double calc() {
    double[] bounds = this.getBounds(flowStream.getTemperature(), 1.0);
    double accuracy = 0.0001;
    
    // Determine if any of the species is likely to be non-condensable, and if so, ignore it
    int i;
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      if (this.flowStream.getFlowSpecies().get(i).getCriticalTemperature() == 0.0) {
        System.out.println("ERROR: Critical temperature is not specified.");
        System.exit(1);
      }
      if (bounds[0] > this.flowStream.getFlowSpecies().get(i).getCriticalTemperature())
        this.flowStream.getFlowSpecies().get(i).setOverallMoleFraction(0.0);
    }
    
    // Calculate the dew point
    return RootFinder.calc(this, bounds[0], bounds[1], accuracy);
  }
  
  
  // Test function for the root finder
  public double testFunction(double temperature) {
    int i;
    double result = 0.0;
    
    double pressure = this.flowStream.getPressure();
    double activityCoefficient;
    double overallMoleFraction, saturationPressure, largePhi;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      activityCoefficient = this.flowStream.getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = this.flowStream.getFlowSpecies().get(i).getLargePhi();
      
      result = result + (overallMoleFraction * activityCoefficient * pressure / (largePhi * saturationPressure));
      
    }
    
    return (result - 1);
  }
  
  
}