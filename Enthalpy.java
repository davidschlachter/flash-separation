public class Enthalpy implements Function {
  
  private FlowStream inlet;
  private FlowStream outlet;
  
  public Enthalpy(FlowStream inlet, FlowStream outlet) { 
    this.inlet = new FlowStream(inlet);
    this.outlet = new FlowStream(outlet);
  }
  
  // Getters and setters
  public void setInlet(FlowStream inlet) {
    this.inlet = new FlowStream(inlet);
  }
  public void setOutlet(FlowStream outlet) {
    this.outlet = new FlowStream(outlet);
  }
  public FlowStream getInlet() {
    return this.inlet; // Permit modification of the inlet via this method
  }
  public FlowStream getOutlet() {
    return this.outlet;
  }
  
  // Calculate the enthalpy change between the inlet and the outlet streams
  public double testFunction(double testTemp){
    int i;
    double initialTemperature = -1.0;
    double finalTemperature = -1.0;
    double outletBubbleTemperature = -1.0, inletBubbleTemperature = -1.0;
    
    double vapourMoleFraction, liquidMoleFraction, heatofVapourization, result = 0.0;
    
    double inletVapourFraction, outletVapourFraction, inletFlowRate, outletFlowRate, inletVapourMoleFraction;
    double inletLiquidMoleFraction, inletLiquidFlowRate, inletVapourFlowRate, outletVapourMoleFraction;
    double outletLiquidMoleFraction, outletLiquidFlowRate, outletVapourFlowRate;
    
    inletVapourFraction = this.inlet.getVapourFraction();
    outletVapourFraction = this.outlet.getVapourFraction();
    inletFlowRate = this.inlet.getMolarFlowRate();
    outletFlowRate = this.inlet.getMolarFlowRate();
    
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
        outletLiquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        outletLiquidFlowRate = outletFlowRate*(1-outletVapourFraction)*outletLiquidMoleFraction;
        result = result + outletLiquidFlowRate * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
      }
    } else if(this.inlet.getVapourFraction() == 1.0 && this.outlet.getVapourFraction() == 1.0) {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        outletVapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        outletVapourFlowRate = outletFlowRate*outletVapourFraction*outletVapourMoleFraction;
        result = result + outletVapourFlowRate * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
      }
    } else {
      outletBubbleTemperature = new BubblePoint(this.outlet).calc();
      inletBubbleTemperature = new BubblePoint(this.inlet).calc();
      
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
          double lBound;
          if (initialTemperature > finalTemperature) {
            lBound = initialTemperature;
          } else {
            lBound = outletBubbleTemperature;
          }
          result += inletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),  initialTemperature, lBound, "liquid");
          result += outletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), lBound, finalTemperature, "vapour");
          result += (inletLiquidFlowRate-outletLiquidFlowRate)*heatofVapourization;
        }
        // If some of the vapour has condensed
        if (inletLiquidFlowRate < outletLiquidFlowRate) {
          double uBound;
          if (initialTemperature < finalTemperature) {
            uBound = finalTemperature;
          } else {
            uBound = outletBubbleTemperature;
          }
          result += outletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), uBound, finalTemperature, "liquid");
          result += inletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),  initialTemperature, uBound, "vapour");
          result -= (outletLiquidFlowRate-inletLiquidFlowRate)*heatofVapourization;
          
        }
      }
    }
    
    return result;
    
  }
  
}
