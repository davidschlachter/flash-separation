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
    
    double overallMoleFraction, vapourMoleFraction, heatOfVapourization, result = 0.0;
    
    System.out.println("Inside: " + this.inlet.getTemperature() + " " + this.outlet.getTemperature());
    
    if (this.inlet.getTemperature() < 0.01) {
      initialTemperature = testTemp;
      finalTemperature = this.outlet.getTemperature();
    } else if (this.outlet.getTemperature() < 0.01) {
      finalTemperature = testTemp;
      initialTemperature = this.inlet.getTemperature();
    } else {
      System.out.println("Failure to initialize temperatures to real values."); 
      System.exit(1);
    }
    
    if (this.inlet.getNumberOfSpecies() != this.outlet.getNumberOfSpecies()) {
      System.out.println("Inlet and outlet streams do not have same number of species."); 
      System.exit(1);
    }
    
     for (i = 0; i < this.outlet.getNumberOfSpecies(); i++) {
      overallMoleFraction = this.outlet.getFlowSpecies().get(i).getOverallMoleFraction();
      vapourMoleFraction = this.outlet.getFlowSpecies().get(i).getVapourMoleFraction();
      heatOfVapourization = this.outlet.getFlowSpecies().get(i).getHeatOfVapourization();
      
      result = result + vapourMoleFraction * (heatOfVapourization) + 
        overallMoleFraction * HeatCapacity.integrate(this.outlet.getFlowSpecies().get(i), initialTemperature, finalTemperature);
    }

     return result;

  }

}
