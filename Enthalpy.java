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
    
    if(this.inlet.getVapourFraction() == 0.0 && this.outlet.getVapourFraction() == 0.0) {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        liquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        result = result + liquidMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
      }
    } else if(this.inlet.getVapourFraction() == 1.0 && this.outlet.getVapourFraction() == 1.0) {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        vapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        result = result + vapourMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
      }
    } else {
      BubblePoint bubblePoint = new BubblePoint(this.inlet);
      bubbleTemperature = bubblePoint.calc();
      
      double inletVapourFraction, outletVapourFraction, inletFlowRate, outletFlowRate, inletVapourMoleFraction;
      double inletLiquidMoleFraction, inletLiquidFlowRate, inletVapourFlowRate, outletVapourMoleFraction;
      double outletLiquidMoleFraction, outletLiquidFlowRate, outletVapourFlowRate;
      
      inletVapourFraction = this.inlet.getVapourFraction();
      outletVapourFraction = this.outlet.getVapourFraction();
      inletFlowRate = this.inlet.getMolarFlowRate();
      outletFlowRate = this.inlet.getMolarFlowRate();
      
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        inletVapourMoleFraction = this.inlet.getFlowSpecies().get(i).getVapourMoleFraction();
        inletLiquidMoleFraction = this.inlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        inletLiquidFlowRate = inletFlowRate*(1-inletVapourFraction)*inletLiquidMoleFraction;
        inletVapourFlowRate = inletFlowRate*inletVapourFraction*inletVapourMoleFraction;
        
        outletVapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        outletLiquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        outletLiquidFlowRate = outletFlowRate*(1-outletVapourFraction)*outletLiquidMoleFraction;
        outletVapourFlowRate = outletFlowRate*outletVapourFraction*outletVapourMoleFraction;
        
        heatofVapourization = this.outlet.getFlowSpecies().get(i).getHeatOfVapourization();
        
        // If some of the liquid has vapourized
        if (inletLiquidFlowRate > outletLiquidFlowRate) {
          result += inletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),
                                                               initialTemperature, bubbleTemperature, "liquid");
          result += outletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),
                                                               bubbleTemperature, finalTemperature, "vapour");
          result += (inletLiquidFlowRate-outletLiquidFlowRate)*heatofVapourization;
        }
        // If the liquid has all condensed
        if (inletLiquidFlowRate < outletLiquidFlowRate) {
          result += outletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),
                                                                bubbleTemperature, finalTemperature, "liquid");
          result += inletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),
                                                               initialTemperature, bubbleTemperature, "vapour");
          result -= (outletLiquidFlowRate-inletLiquidFlowRate)*heatofVapourization;

        }
      }
    }
    
    return result;
    
  }
  
}
