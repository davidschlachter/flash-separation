import java.lang.IllegalArgumentException;

/**
 * Class that returns the bubble point of a stream of given composition at a given pressure
 */
public class BubblePoint implements Function {
  
  private FlowStream flowStream;
  
  // Constructor -- requires a FlowStream with pressure specified 
  public BubblePoint(FlowStream flowStream) {
    if (flowStream.getPressure() > 0 && flowStream.getFlowSpecies().size() > 0) {
      this.flowStream = new FlowStream(flowStream);
    } else {
      System.out.println("Error: flow stream for must contain a pressure and at least one species!");
      System.exit(1);
    } 
  }
  
  // Return the bubble point for the given flowStream components at the given pressure
  public double calc() {
    double[] bounds = RootFinder.getBounds(this, flowStream.getTemperature(), 1.0);
    double result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
    int i = 0;
    while (Double.isNaN(result)) {
      bounds = RootFinder.getBounds(this, bounds[1], 1.0, 1.0);
      result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
      if (i > 10) break;
    }
    return result;
  }
  
  // Test function for the root finder
  public double testFunction(double x) throws IllegalArgumentException {
    if(x < 0) throw new IllegalArgumentException("Error! Bubble point calculation input temperature is below zero.");
    int i;
    double result = 0.0;
    
    double temperature = x;
    double pressure = this.flowStream.getPressure();
    double overallMoleFraction, saturationPressure;
    double activityCoefficient, largePhi;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      
      result = result + (saturationPressure *overallMoleFraction / (pressure));
      
    }
    
    return (result - 1);
  }
  
  
}