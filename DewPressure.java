public class DewPressure implements Function{
  
  private FlowStream flowStream;
  
  public DewPressure(FlowStream input){
    this.flowStream = input;
  }
  
  public double solve(){
    
    int n = flowStream.getFlowSpecies().size();
    double t = 0.0;
    double p = flowStream.getPressure();  //this shouldn't change at all in a DEWT calculation
    double sumTerm = 0.0;
    for(int i=0;i<n;i++){
      double xi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();//assume overall mole fraction = vapour mole fraction at dewP conditions
      double pc = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      double omega = flowStream.getFlowSpecies().get(i).getAcentricFactor();
      double tc = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      double tSat = tc/(1-3*Math.log(p/pc)/(Math.log(10.0)*(7+7*omega)));
      t+=xi*tSat;   //take initial guess at t
    };  
    flowStream.setTemperature(t);
    
    
    
    for(int i=0; i<n; i++){    //get estimates of Ki values and liquid mole fractions
      double tc = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      double pc = flowStream.getFlowSpecies().get(i).getCriticalPressure();
      double yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction(); //make sure this assumption is correct
      double omega = flowStream.getFlowSpecies().get(i).getAcentricFactor();
      double ki = Math.exp(Math.log(pc/p)+Math.log(10)*(7.0/3.0)*(1+omega)*(1-(tc/t)));
      flowStream.getFlowSpecies().get(i).setVapourMoleFraction(yi);
      flowStream.getFlowSpecies().get(i).setKi(ki);
      flowStream.getFlowSpecies().get(i).setLiquidMoleFraction(yi/ki);
      
    } 
    double result = 0.0;
    //double[] bounds = RootFinder.getBounds(this, t, 5.0);
    result = RiddersMethod.calc(this,300.0, 400.0, 0.1, true);  //maybe these bounds are no good. review this laterget
    return result;
  }
  
  public double testFunction(double t){
    int n = flowStream.getFlowSpecies().size();
    System.out.println("t is: "+t);  //function doesn't seem to be feeding an actual number into test function
    PengRobinson nonIdealStream = new PengRobinson(flowStream);  
    
    nonIdealStream.kappaI();
    nonIdealStream.alphaI();
    nonIdealStream.individualA();
    nonIdealStream.individualB();
  
    double error = 0.0;
    
    //do{
      error = 0.0;
     // nonIdealStream.getFlowStream().setTemperature(t);
      nonIdealStream.flowStreamSmallAXValue();
      System.out.println("AX in dewpresure method is: "+flowStream.getSmallAX());
      nonIdealStream.flowStreamSmallBXValue();
      nonIdealStream.flowStreamLargeAXValue();
      nonIdealStream.flowStreamLargeBXValue();
      nonIdealStream.flowStreamSmallAYValue();
      nonIdealStream.flowStreamSmallBYValue();
      nonIdealStream.flowStreamLargeAYValue();
      nonIdealStream.flowStreamLargeBYValue(); 
      nonIdealStream.solveZCubicLiquid();
      nonIdealStream.solveZCubicVapour();
      nonIdealStream.liquidFugacity();
      nonIdealStream.vapourFugacity();
      System.out.println("Liquid Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getLiquidFugacity()+
                         "\nVapour Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getVapourFugacity());
      System.out.println("Liquid Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getLiquidFugacity()+
                         "\nVapour Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getVapourFugacity());
      System.out.println("Z_L: "+nonIdealStream.getFlowStream().getZL());
      System.out.println("Z_V: "+nonIdealStream.getFlowStream().getZV()); 
    /*  for(int i=0;i<n; i++){
        double yi = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getOverallMoleFraction();
        double phiL = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidFugacity();
        double phiV = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getVapourFugacity();
        double xiOld = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
        double xiNew=(yi*phiV)/phiL;
        nonIdealStream.getFlowStream().getFlowSpecies().get(i).setLiquidMoleFraction(xiNew);
        error += Math.abs(xiOld - nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction()); 
        System.out.println("X"+i+" old is: "+xiOld);
        System.out.println("X"+i+" new is: "+xiNew);
      } */
   // }while(error > 0.001);
    
    
    double result = 0.0;
    for(int i=0; i<n; i++){
      result+=nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
      System.out.println("Sum of liquid mole fractions is: "+result);
    }
    return (result-1); 
  } 
  
}
