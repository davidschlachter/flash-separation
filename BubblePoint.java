/**
 * Class that returns the dew point of a stream of given composition at a given pressure
 */
public class BubblePoint extends Function {
  
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
    double[] bounds = this.getBounds(flowStream.getTemperature(), 1.0);
    return RootFinder.calc(this, bounds[0], bounds[1], 0.001);
  }
  
  // Test function for the root finder
  public double testFunction(double x) {
    int i;
    double result = 0.0;
    
    double temperature = x;
    double pressure = this.flowStream.getPressure();
    double overallMoleFraction, saturationPressure;
    double activityCoefficient, largePhi;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      activityCoefficient = this.flowStream.getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = this.flowStream.getFlowSpecies().get(i).getLargePhi();
      
      result = result + (saturationPressure * activityCoefficient * overallMoleFraction / (pressure * largePhi));
      
    }
    
    return (result - 1);
  }
  
  
}
