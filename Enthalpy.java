public class Enthalpy implements Function {
  
  private FlowStream inlet;
  private FlowStream outlet;
  
  
  
  public Enthalpy(FlowStream inlet, FlowStream outlet) { 
    
    this.inlet = inlet;
    this.outlet = outlet;

  }
  
  public double testFunction(double testTemp){
  
    int i;
    double initialTemperature = -1.0;
    double finalTemperature = -1.0;
    double bubbleTemperature = -1.0;
    
    double overallMoleFraction, vapourMoleFraction, liquidMoleFraction, heatOfVapourization, result = 0.0;
    
    if (this.inlet.getTemperature() < 0.01) {
      initialTemperature = testTemp;
      finalTemperature = this.outlet.getTemperature();
    } else if (this.outlet.getTemperature() < 0.01) {
      finalTemperature = testTemp;
      initialTemperature = this.inlet.getTemperature();
    } else {
      finalTemperature = this.outlet.getTemperature();
      initialTemperature = this.inlet.getTemperature();
    }
    
    if (this.inlet.getNumberOfSpecies() != this.outlet.getNumberOfSpecies()) {
      System.out.println("Inlet and outlet streams do not have same number of species."); 
      System.exit(1);
    }
    
    BubblePoint bubblePoint = new BubblePoint(this.inlet);
    bubbleTemperature = bubblePoint.calc();
    
     for (i = 0; i < this.outlet.getNumberOfSpecies(); i++) {
      overallMoleFraction = this.outlet.getFlowSpecies().get(i).getOverallMoleFraction();
      vapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
      liquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
      
      result = result + vapourMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), bubbleTemperature, finalTemperature, "vapour") + 
        liquidMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
    }

     return result;

  }

}
