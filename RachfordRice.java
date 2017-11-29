import java.util.Scanner;
import java.io.PrintWriter;
import java.lang.IllegalArgumentException;

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
    
    int i;
    double dewPointTemperature = new DewPoint(this.flowStream).calc();
    double bubblePointTemperature = new BubblePoint(this.flowStream).calc();
    //System.out.println("Got dewpoint "+dewPointTemperature+" and bubblepoint "+bubblePointTemperature);
    double vOverF = RiddersMethod.calc(this, 0.0, 1.0, 0.001, false);
    
    if (this.flowStream.getTemperature() > dewPointTemperature) {
      for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
        this.flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(0.0);
        this.flowStream.getFlowSpecies().get(i).setVapourMoleFraction(this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction());
      }
      this.flowStream.setVapourFraction(1.0);
      return this.flowStream;
    } else if (this.flowStream.getTemperature() < bubblePointTemperature) {
      for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
        this.flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction());
        this.flowStream.getFlowSpecies().get(i).setVapourMoleFraction(0.0);
      }
      this.flowStream.setVapourFraction(0.0);
      return this.flowStream;
    }
    
    if (Double.isNaN(vOverF)) {
      System.out.println("WARNING: The value of V/F for the RachfordRice equation could not be determined.");
      return this.flowStream;
    }
    
    
    double overallMoleFraction, saturationPressure, kMinusOne;
    double liquidMoleFraction, vapourMoleFraction, pressure;
    double liquidFugacity, vapourFugacity;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), this.flowStream.getTemperature());
      liquidFugacity = this.flowStream.getFlowSpecies().get(i).getLiquidFugacity();
      vapourFugacity = this.flowStream.getFlowSpecies().get(i).getVapourFugacity();
      pressure = this.flowStream.getPressure();
      if(this.flowStream.getIsIdeal()==true){
        kMinusOne = (saturationPressure)/(pressure)-1;
      } else {
        kMinusOne = (liquidFugacity/vapourFugacity)-1;
      }
      
      liquidMoleFraction = overallMoleFraction/(1 + vOverF*kMinusOne);
      this.flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(liquidMoleFraction);
      
      vapourMoleFraction = (kMinusOne + 1) * this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      this.flowStream.getFlowSpecies().get(i).setVapourMoleFraction(vapourMoleFraction);
      
    }
    
    this.flowStream.setVapourFraction(vOverF);
    
    // Check if the numbers add up!
    double liquidFlow = 0., vapourFlow = 0.;
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      liquidFlow += this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction()*(1-vOverF)*this.flowStream.getMolarFlowRate();
      vapourFlow += this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction()*vOverF*this.flowStream.getMolarFlowRate();
    }
    if (Math.abs(((liquidFlow+vapourFlow)-this.flowStream.getMolarFlowRate())/this.flowStream.getMolarFlowRate()) > 0.0001) {
      System.out.println("\nERROR: Flow rates are inconsistent!!\nvOverF was found to be: " + vOverF);
    }
    
    return new FlowStream(this.flowStream);
    
  }
  
  // Test function for the root finder
  public double testFunction(double x) throws IllegalArgumentException {
    if(x<0 || x>1) throw new IllegalArgumentException("Error! Rachford-Rice V/F must be between 0 and 1.");
    int i;
    double result = 0.0;
    
    double temperature = this.flowStream.getTemperature();
    double pressure = this.flowStream.getPressure();
    double overallMoleFraction, saturationPressure, liquidFugacity, vapourFugacity;
    double kMinusOne;
    
    for (i = 0; i < flowStream.getFlowSpecies().size(); i++) {
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      if(this.flowStream.getIsIdeal()==true){
        kMinusOne = (saturationPressure)/(pressure)-1;
      } else {
        PengRobinson nonIdealStream = new PengRobinson(this.flowStream);
        nonIdealStream.nonIdealCalcs();
        liquidFugacity = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidFugacity();
        vapourFugacity = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getVapourFugacity();
        kMinusOne = ((liquidFugacity)/(vapourFugacity))-1;
      }
      result += (overallMoleFraction*kMinusOne)/(1 + x*kMinusOne) ;
      
    }
    
    if (this.flowStream.getIsIdeal() == false && Double.isNaN(x) == false) {
      //System.out.println("Setting parameters now in testFunction, and V/F here is "+x);
      double liquidMoleFraction, vapourMoleFraction;
      for (i = 0; i < this.flowStream.getFlowSpecies().size(); i++) {
        
        overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), this.flowStream.getTemperature());
        liquidFugacity = this.flowStream.getFlowSpecies().get(i).getLiquidFugacity();
        vapourFugacity = this.flowStream.getFlowSpecies().get(i).getVapourFugacity();
        pressure = this.flowStream.getPressure();
            kMinusOne = (liquidFugacity/vapourFugacity)-1;   //TODO: Find out why liquid fugacity is coming up as zero!
        
        liquidMoleFraction = overallMoleFraction/(1 + x*kMinusOne);
        this.flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(liquidMoleFraction);
        
        vapourMoleFraction = (kMinusOne + 1) * this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        this.flowStream.getFlowSpecies().get(i).setVapourMoleFraction(vapourMoleFraction);
      }
      /* System.out.println("zL here is: "+this.flowStream.getZL());
       System.out.println("Liquid fugacity here is: "+this.flowStream.getFlowSpecies().get(0).getLiquidFugacity());
       System.out.println("Liquid fugacity here is: "+this.flowStream.getFlowSpecies().get(1).getLiquidFugacity());
       System.out.println("Liquid fugacity here is: "+this.flowStream.getFlowSpecies().get(2).getLiquidFugacity());
       System.out.println("Liquid fugacity here is: "+this.flowStream.getFlowSpecies().get(3).getLiquidFugacity());
       System.out.println("Large BX here is: "+this.flowStream.getLargeBX());
       System.out.println("Large BX here is: "+this.flowStream.getLargeBX());
       System.out.println("Small AX here is: "+this.flowStream.getSmallAX());
       for(int z=0; z<this.flowStream.getFlowSpecies().size();z++){
       System.out.println("individual b"+z+" is: "+this.flowStream.getFlowSpecies().get(z).getBI());
       } */
      //ConsoleUI.printStreams(new Scanner(System.in), new PrintWriter(System.out, true), this.flowStream, this.flowStream);
    }
    
    return result; 
  }
  
  //Test derivative for Newton-Raphson root finder
  public double testDerivative(double x) {
    
    double result = 0.0;
    double overallMoleFraction, kMinusOne, saturationPressure, liquidFugacity, vapourFugacity;
    double temperature = this.flowStream.getTemperature();
    double pressure = this.flowStream.getPressure();
    
    for(int i=0; i<flowStream.getFlowSpecies().size(); i++){
      
      overallMoleFraction = this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      saturationPressure = SaturationPressure.calc(this.flowStream.getFlowSpecies().get(i), temperature);
      liquidFugacity = this.flowStream.getFlowSpecies().get(i).getLiquidFugacity();
      vapourFugacity = this.flowStream.getFlowSpecies().get(i).getVapourFugacity();
      if(this.flowStream.getIsIdeal()==true){
        kMinusOne = (saturationPressure)/(pressure)-1;
      } else {
          kMinusOne = ((liquidFugacity)/(vapourFugacity))-1;
      }
      
      
      result -= (overallMoleFraction * Math.pow(kMinusOne, 2))/(Math.pow((1+x*kMinusOne),2));
      
    }
    
    return result;
  }
  
}