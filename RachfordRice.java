public class RachfordRice implements Function {
  
  private FlowStream flowStream;
  
  public RachfordRice(FlowStream flowStream) { 
    if (flowStream.getTemperature() > 0 && flowStream.getPressure() > 0 && flowStream.getNumberOfSpecies() > 0) {
      this.flowStream = flowStream;
    } else {
      System.out.println("Error: flow stream must contain a pressure, temperature, and at least one species!");
      System.exit(1);
    } 
  }
  
  // Solve the composition of the given flow stream
  public FlowStream solve() {
    
    // TODO: Bounds checking needs to be intelligently done!
    // Otherwise, the method will not converge!
    double vOverF = RootFinder.calc(this, 0.0, 5, 0.001);
    
    if (Double.isNaN(vOverF)) {
      System.out.println("ERROR: The value of V/F for the RachfordRice equation could not be determined.");
      System.out.println("       Typically this is because the bounds for the root solver did not necessarily");
      System.out.println("       bound the root.");
      System.exit(1);
    }
    
    int i;
    double overallMoleFraction, saturationPressure, kMinusOne;
    double liquidMoleFraction, vapourMoleFraction;
    
    for (i = 0; i < flowStream.getNumberOfSpecies(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), this.flowStream.getTemperature());
      kMinusOne = (saturationPressure/this.flowStream.getPressure())-1;
      
      liquidMoleFraction = overallMoleFraction/(1 + vOverF*kMinusOne);
      this.flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(liquidMoleFraction);
      
      vapourMoleFraction = (kMinusOne + 1) * this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      this.flowStream.getFlowSpecies().get(i).setVapourMoleFraction(vapourMoleFraction);

    }
    
    return this.flowStream;
    
  }
  
  // Test function for the root finder
  public double testFunction(double x) {
    int i;
    double result = 0.0;
    
    double temperature = this.flowStream.getTemperature();
    double pressure = this.flowStream.getPressure();
    double overallMoleFraction, saturationPressure;
    double kMinusOne;
    
    for (i = 0; i < flowStream.getNumberOfSpecies(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      kMinusOne = (saturationPressure/pressure)-1;
      
      result = result + (overallMoleFraction*kMinusOne)/(1 + x*kMinusOne);

    }
    
    return result;
  }
  
}