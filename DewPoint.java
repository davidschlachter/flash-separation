/**
 * Class that returns the dew point of a stream of given composition at a given pressure
 */
public class DewPoint implements Function {
  
  private FlowStream flowStream;
  
  // Constructor -- requires a FlowStream with pressure specified 
  public DewPoint(FlowStream flowStream) {
    if (flowStream.getPressure() > 0 && flowStream.getFlowSpecies().size() > 0) {
      this.flowStream = new FlowStream(flowStream);
    } else {
      System.out.println("Error: flow stream for must contain a pressure and at least one species!");
      System.exit(1);
    } 
  }
  
  //flowStream getter
  public FlowStream getFlowStream(){
    return this.flowStream;
  }
  
  
  // Return the dew point for the given flowStream components at the given pressure
  public double calc() {
    // Determine if any of the species is likely to be non-condensable, and if so, ignore it
    int i;
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      if (this.flowStream.getFlowSpecies().get(i).getCriticalTemperature() == 0.0) {
        System.out.println("ERROR: Critical temperature is not specified for species "+this.flowStream.getFlowSpecies().get(i).getSpeciesName());
        System.exit(1);
      }
      if (flowStream.getTemperature() > this.flowStream.getFlowSpecies().get(i).getCriticalTemperature())
        this.flowStream.getFlowSpecies().get(i).setOverallMoleFraction(0.0);
    }
    
    // Calculate the dew point
    double[] bounds = RootFinder.getBounds(this, flowStream.getTemperature(), 1.0);
    double result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
    i = 0;
    while (Double.isNaN(result)) {
      bounds = RootFinder.getBounds(this, bounds[1], 1.0, 1.0);
      result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
      if (i > 10) break;
    }
    return result;
    
  }
  
  
  // Test function for the root finder
  public double testFunction(double temperature) {
    int i;
    double result = 0.0;
    
    double pressure = this.flowStream.getPressure();
    double activityCoefficient, overallMoleFraction, saturationPressure, largePhi;
    
    this.flowStream.setTemperature(temperature);
    Fugacity fugacity = new Fugacity(this.flowStream);  //this has just been added and may not work yet
    
    if(this.flowStream.isIdeal() == false){
    fugacity.computeNonIdealParameters();
    }
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = fugacity.getFlowStream().getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(fugacity.getFlowStream().getFlowSpecies().get(i), temperature);
      activityCoefficient = fugacity.getFlowStream().getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = fugacity.getFlowStream().getFlowSpecies().get(i).getLargePhi();
      
      result = result + (overallMoleFraction * largePhi * pressure / (activityCoefficient * saturationPressure));
      
    }
    
    return (result - 1);
  }
  
  
}