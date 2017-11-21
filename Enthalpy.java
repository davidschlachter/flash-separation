//import java.util.Scanner;
//import java.io.PrintWriter;

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
  
  // For the root finder!
  public double testFunction(double testTemp) {
    boolean inletSpecified;
    // Determine and set missing temperature
    FlowStream unspecifiedStream, specifiedStream;
    if (this.outlet.getTemperature() > 0.0 && this.inlet.getTemperature() == 0.0) {
      unspecifiedStream = this.inlet;
      specifiedStream = this.outlet;
      inletSpecified = false;
    } else if (this.inlet.getTemperature() > 0.0 && this.outlet.getTemperature() == 0.0) {
      unspecifiedStream = this.outlet;
      specifiedStream = this.inlet;
      inletSpecified = true;
    } else {
      // Satisfy the compiler re initializing all variables  :)
      unspecifiedStream = this.outlet;
      specifiedStream = this.inlet;
      inletSpecified = true;
      // Print an error... the problem here isn't anything we're expecting!
      System.out.println("ERROR: Both temperatures are specified!");
      System.exit(1);
    }
    
    // Calculate the enthalpy difference!
    unspecifiedStream.setTemperature(testTemp);
    unspecifiedStream = new RachfordRice(unspecifiedStream).solve();
    if (inletSpecified == true) this.outlet = unspecifiedStream; else this.inlet = unspecifiedStream;
    double difference = this.calc();
    unspecifiedStream.setTemperature(0.0);
    return difference;
  }
  
  // Calculate the enthalpy change between the inlet and the outlet streams
  public double calc() {
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
    
    finalTemperature = this.outlet.getTemperature();
    initialTemperature = this.inlet.getTemperature();
    
    
    if (this.inlet.getFlowSpecies().size() != this.outlet.getFlowSpecies().size()) {
      System.out.println("Inlet and outlet streams do not have same number of species."); 
      System.exit(1);
    }
    
    if (this.inlet.getVapourFraction() == 0.0 && this.outlet.getVapourFraction() == 0.0) {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        //System.out.println("No vapour in enthalpy!");
        outletLiquidMoleFraction = this.outlet.getFlowSpecies().get(i).getLiquidMoleFraction();
        outletLiquidFlowRate = outletFlowRate*(1-outletVapourFraction)*outletLiquidMoleFraction;
        result = result + outletLiquidFlowRate * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
      }
    } else if (this.inlet.getVapourFraction() == 1.0 && this.outlet.getVapourFraction() == 1.0) {
      for (i = 0; i < this.outlet.getFlowSpecies().size(); i++) {
        //System.out.println("No liquid in enthalpy!");
        outletVapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
        outletVapourFlowRate = outletFlowRate*outletVapourFraction*outletVapourMoleFraction;
        result = result + outletVapourFlowRate * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
      }
    } else {
      //System.out.println("System is multiphase");
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
        
        //
        // If some of the liquid has vapourized
        //
        if (inletLiquidFlowRate > outletLiquidFlowRate) {
          // How much liquid is left in the end? Calculate its change in enthalpy
          result += outletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i),  initialTemperature, finalTemperature, "liquid");
          // How much liquid changed phase? Calculate it's enthalpy change by going to standard conditions, vaporizing, then going to final conditions
          result += (inletLiquidFlowRate-outletLiquidFlowRate)*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, 298.15, "liquid");
          result += (inletLiquidFlowRate-outletLiquidFlowRate)*heatofVapourization; // Standard H.o.V.
          result += (inletLiquidFlowRate-outletLiquidFlowRate)*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), 298.15, finalTemperature, "vapour");
          // How much vapour was in the inlet and just changed temperature?
          result += inletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
        }
        //
        // If some of the vapour has condensed
        //
        if (inletLiquidFlowRate < outletLiquidFlowRate) {
          // How much vapour is left in the end? Calculate its change in enthalpy
          result += outletVapourFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "vapour");
          // How much vapour changed phase? Calculate it's enthalpy change by going to standard conditions, condensing, then going to final conditions
          result += (inletVapourFlowRate-outletVapourFlowRate)*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, 298.15, "vapour");
          result -= (inletVapourFlowRate-outletVapourFlowRate)*heatofVapourization;
          result += (inletVapourFlowRate-outletVapourFlowRate)*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), 298.15, finalTemperature, "liquid");
          // How much liquid was in the inlet and just changed temperature?
          result += inletLiquidFlowRate*HeatCapacity.integrate(this.inlet.getFlowSpecies().get(i), initialTemperature, finalTemperature, "liquid");
        }
      }
    }
    //System.out.println("Enthalpy difference: result was "+result);
    //ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), this.inlet, this.outlet);
    return result;
    
  }
  
}
