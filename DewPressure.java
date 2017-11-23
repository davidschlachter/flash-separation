public class DewPressure implements Function{
  
  private FlowStream flowStream;
  
  public DewPressure(FlowStream input){
    this.flowStream = input;
  }
  
  public double solve(){
    
    int n = flowStream.getFlowSpecies().size();
    double p = 0.0;
    double t = flowStream.getTemperature();  //this shouldn't change at all in a DEWP calculation
    double sumTerm = 0.0;
    for(int i=0;i<n;i++){
      double yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction(); //assume overall mole fraction = vapour mole fraction at dewP conditions
      double pSat = SaturationPressure.calc(flowStream.getFlowSpecies().get(i), flowStream.getTemperature());
      sumTerm+=yi/pSat;
    }
    p = 1/sumTerm;  //take an initial guess at P
    
    
    
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
    //double[] bounds = RootFinder.getBounds(this, 1.0, 100.0, 1.0);
    result = RiddersMethod.calc(this, 1.0, 1000000.0, 0.01, true);  //maybe these bounds are no good. review this laterget
    return result;
  }
  
  public double testFunction(double p){
    int n = flowStream.getFlowSpecies().size();
    
    PengRobinson nonIdealStream = new PengRobinson(flowStream);  
    
    nonIdealStream.kappaI();
    nonIdealStream.alphaI();
    nonIdealStream.individualA();
    nonIdealStream.individualB();
    
    
    double error = 0.0;
    
    //do{
      //error = 0.0;
      nonIdealStream.getFlowStream().setPressure(p);
      nonIdealStream.flowStreamSmallAXValue();
      nonIdealStream.flowStreamSmallBXValue();
      nonIdealStream.flowStreamLargeAXValue();
      nonIdealStream.flowStreamLargeBXValue();
      nonIdealStream.flowStreamSmallAYValue();
      nonIdealStream.flowStreamSmallBYValue();
      nonIdealStream.flowStreamLargeAYValue();
      nonIdealStream.flowStreamLargeBYValue(); 
      nonIdealStream.solveZCubicLiquid();
      nonIdealStream.solveZCubicVapour();
      //System.out.println("
      nonIdealStream.liquidFugacity();
      nonIdealStream.vapourFugacity();
      System.out.println("Liquid Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getLiquidFugacity()+
                         "\nVapour Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getVapourFugacity());
      System.out.println("Liquid Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getLiquidFugacity()+
                         "\nVapour Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getVapourFugacity());
      System.out.println("Z_L: "+nonIdealStream.getFlowStream().getZL());
      System.out.println("Z_V: "+nonIdealStream.getFlowStream().getZV());
      /*for(int i=0;i<n; i++){
        double yi = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getOverallMoleFraction();
        double phiL = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidFugacity();
        double phiV = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getVapourFugacity();
        double xiOld = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
        nonIdealStream.getFlowStream().getFlowSpecies().get(i).setLiquidMoleFraction((yi*phiV)/phiL);
        //error += Math.abs(xiOld - nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction()); 
        
      }*/
    //}while(error > 0.001);
    
    //iterate over P with the equation P=sum(piSat*xi)
    
    double result = 0.0;
    for(int i=0; i<n; i++){
      result+=nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
    }
    return (result-1); 
  } 
  
}
