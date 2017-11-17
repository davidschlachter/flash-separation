public class RachfordRice implements DifferentiableFunction {
  
  private FlowStream flowStream;
  
  public RachfordRice(FlowStream flowStream) { 
    if (flowStream.getTemperature() > 0 && flowStream.getPressure() > 0 && flowStream.getFlowSpecies().size() > 0) {
      this.flowStream = flowStream;
    } else {
      System.out.println("Error: flow stream must contain a pressure, temperature, and at least one species!");
      System.exit(1);
    } 
  }
  
  public FlowStream getFlowStream(){
    return this.flowStream;
  }
  
  public void setFlowStream(FlowStream newStream){
    this.flowStream = new FlowStream(newStream);
  }
  
  // Solve the composition of the given flow stream
  public FlowStream solve() {
    
    double vOverF = NewtonRaphson.calc(this, 0.5, 0.1);
    
    
    if (Double.isNaN(vOverF)) {
      System.out.println("ERROR: The value of V/F for the RachfordRice equation could not be determined.");
      System.out.println("       Typically this is because the bounds for the root solver did not necessarily");
      System.out.println("       bound the root.");
      //System.exit(1); 
      return this.flowStream;
    }
    
    int i;
    double overallMoleFraction, saturationPressure, kMinusOne;
    double liquidMoleFraction, vapourMoleFraction, pressure;
    double activityCoefficient, largePhi;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), this.flowStream.getTemperature());
      activityCoefficient = this.flowStream.getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = this.flowStream.getFlowSpecies().get(i).getLargePhi();
      pressure = this.flowStream.getPressure();
      kMinusOne = (activityCoefficient * saturationPressure)/(pressure * largePhi)-1;
      
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
    double overallMoleFraction, saturationPressure, activityCoefficient, largePhi;
    double kMinusOne;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      activityCoefficient = this.flowStream.getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = this.flowStream.getFlowSpecies().get(i).getLargePhi();
      kMinusOne = (activityCoefficient * saturationPressure)/(pressure * largePhi)-1;
      
      result = (result + (overallMoleFraction*kMinusOne)/(1 + x*kMinusOne)) ;
      
    }
    
    return result; 
  }
  
  //Test derivative for Newton-Raphson root finder
  public double testDerivative(double x) {
    
    double result = 0.0;
    double overallMoleFraction, kMinusOne, saturationPressure, activityCoefficient, largePhi;
    double temperature = this.flowStream.getTemperature();
    double pressure = this.flowStream.getPressure();
    
    for(int i=0; i<flowStream.getFlowSpecies().size(); i++){
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      activityCoefficient = this.flowStream.getFlowSpecies().get(i).getActivityCoefficient();
      largePhi = this.flowStream.getFlowSpecies().get(i).getLargePhi();
      kMinusOne = (activityCoefficient * saturationPressure)/(pressure * largePhi)-1;
      
      result = result - (overallMoleFraction * Math.pow(kMinusOne, 2))/(Math.pow((1+x*kMinusOne),2));
      
    }
    
    return result;
  }
  
}
