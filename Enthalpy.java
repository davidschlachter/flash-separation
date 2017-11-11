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
    
    double vapourMoleFraction, liquidMoleFraction, heatofVapourization, result = 0.0;
    
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
    
    if (this.inlet.getFlowSpecies().size() != this.outlet.getFlowSpecies().size()) {
      System.out.println("Inlet and outlet streams do not have same number of species."); 
      System.exit(1);
    }
    
    BubblePoint bubblePoint = new BubblePoint(this.inlet);
    bubbleTemperature = bubblePoint.calc();
    
    if(this.inlet.getVapourFraction() == 0.0 && this.outlet.getVapourFraction() == 0.0)
    {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        liquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        result = result + liquidMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
      }
    }
    
    else if(this.inlet.getVapourFraction() == 1.0 && this.outlet.getVapourFraction() == 1.0)
    {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        vapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        result = result + vapourMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
      }
    }
    else
    {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        vapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        liquidMoleFraction = this.inlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        heatofVapourization = this.outlet.getFlowSpecies().get(i).getHeatOfVapourization();
          
        result = result + vapourMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), bubbleTemperature, finalTemperature, "vapour") + 
          liquidMoleFraction * HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, bubbleTemperature, "liquid") + heatofVapourization;
          
      }
    }
    
    return result;
    
  }
  
}
