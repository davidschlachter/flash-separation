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
  public double calc() throws IllegalArgumentException {
    int i;
    // Determine if any of the species is likely to be non-condensable, and if so, ignore it
    for (i = 0; i < this.flowStream.getFlowSpecies().size(); i++) {
      if (this.flowStream.getFlowSpecies().get(i).getCriticalTemperature() == 0.0) throw new IllegalArgumentException("ERROR: Critical temperature is not specified for species "+this.flowStream.getFlowSpecies().get(i).getSpeciesName());
    }
    
    double[] bounds = RootFinder.getBounds(this, flowStream.getTemperature(), 50.0);
    double result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
    i = 0;
    while (Double.isNaN(result)) {
      bounds = RootFinder.getBounds(this, bounds[1], 1.0, 1.0);
      result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
      if (i > 10) break;
    }
    if (result < 0.0) throw new IllegalArgumentException("Error! Bubble point calculation gave a temperature is below zero K: "+result);
    return result;
  }
  
  // Test function for the root finder
  public double testFunction(double x) {
    int i;
    double result = 0.0;
    
    FlowStream testStream = new FlowStream(this.flowStream);
    
    // Determine if any of the species is likely to be non-condensable, and if so, ignore it
    for (i = 0; i < testStream.getFlowSpecies().size(); i++) {
      if (testStream.getTemperature() > testStream.getFlowSpecies().get(i).getCriticalTemperature())
        testStream.getFlowSpecies().get(i).setOverallMoleFraction(0.0);
    }
    
    double temperature = x;
    double pressure = testStream.getPressure();
    double overallMoleFraction, saturationPressure;
    
    for (i = 0; i < testStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = testStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(testStream.getFlowSpecies().get(i), temperature);
      
      result = result + (saturationPressure *overallMoleFraction / (pressure));
      
    }
    
    return (result - 1);
  }
  
  
}