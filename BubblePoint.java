/**
 * Class that returns the dew point of a stream of given composition at a given pressure
 */
public class BubblePoint implements Function {
  
  private FlowStream flowStream;
  
  // Constructor -- requires a FlowStream with pressure specified 
  public BubblePoint(FlowStream flowStream) {
    if (flowStream.getPressure() > 0 && flowStream.getFlowSpecies().size() > 0) {
      this.flowStream = flowStream;
    } else {
      System.out.println("Error: flow stream for must contain a pressure and at least one species!");
      System.exit(1);
    } 
  }
  
  // Return the bubble point for the given flowStream components at the given pressure
  public double calc() {
    // TODO: Need to find the bounds automatically before calling RootFinder.calc!
    return RootFinder.calc(this, 100.0, 1000.0, 0.001);
  }
  
  // Test function for the root finder
  public double testFunction(double x) {
    int i;
    double result = 0.0;
    
    double temperature = x;
    double pressure = this.flowStream.getPressure();
    double overallMoleFraction, saturationPressure;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      
      result = result + ((saturationPressure / pressure)*overallMoleFraction);

    }
    
    return (result - 1);
  }
  
  
}
