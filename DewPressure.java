/* public class DewPressure implements Function{
  
  private FlowStream flowStream;
  
  public DewPressure(FlowStream input){
    this.flowStream = input.clone();
  }
  
  public double solve(){
    
    int n = flowStream.getFlowSpecies().size();
    double t = flowStream.getTemperature();
    System.out.println("Temperature of flow stream is: "+t);
    double p = 0.0;
    double sumTerm = 0.0;
    for(int i=0;i<n;i++){
      double yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();//assume overall mole fraction = vapour mole fraction at dewP conditions
      double pSat = SaturationPressure.calc(flowStream.getFlowSpecies().get(i),t);
      System.out.println("Saturation pressure of species "+i+" is: "+pSat);
      sumTerm+=yi/pSat;   //take initial guess at p
    }  
    p=1.0/sumTerm;
    flowStream.setPressure(p);
    System.out.println("Pressure of flow stream is: "+p);
    
    
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
    //double[] bounds = RootFinder.getBounds(this, 9, 500.0);
    //System.out.println("Root boundaries found are: "+bounds[0]+" and "+bounds[1]);
    result = RiddersMethod.calc(this, flowStream.getPressure()-100, flowStream.getPressure()+100, 0.1, true);  //maybe these bounds are no good. review this laterget
    return result;
  }
  
  public double testFunction(double p){
    int n = this.flowStream.getFlowSpecies().size();
    FlowStream copyFlowStream = this.flowStream.clone();
    System.out.println("P is: "+p);  //function doesn't seem to be feeding an actual number into test function
    copyFlowStream.setPressure(p, false);
    PengRobinson nonIdealStream = new PengRobinson(copyFlowStream);
    System.out.println("True pressure is: "+nonIdealStream.getFlowStream().getPressure());
    
    nonIdealStream.kappaI();
    nonIdealStream.alphaI();
    nonIdealStream.individualA();
    nonIdealStream.individualB();
    
    for(int i=0; i<n; i++) {
      System.out.println("kappa "+i+" in dewpressure method is: "+copyFlowStream.getFlowSpecies().get(i).getKappa());
      System.out.println("alpha "+i+" in dewpressure method is: "+copyFlowStream.getFlowSpecies().get(i).getAlpha());
      System.out.println("A "+i+" in dewpressure method is: "+copyFlowStream.getFlowSpecies().get(i).getAI());
      System.out.println("B "+i+" in dewpressure method is: "+copyFlowStream.getFlowSpecies().get(i).getBI());
    }
    
    //double error = 0.0;
    
    //do{
      //error = 0.0;
     // nonIdealStream.getFlowStream().setTemperature(t);
      nonIdealStream.flowStreamSmallAXValue();
      nonIdealStream.flowStreamSmallBXValue();
      nonIdealStream.flowStreamLargeAXValue();
      nonIdealStream.flowStreamLargeBXValue();
      nonIdealStream.flowStreamSmallAYValue();
      System.out.println("aX in dewpresure method is: "+copyFlowStream.getSmallAX());
      System.out.println("bX in dewpresure method is: "+copyFlowStream.getSmallBX());
      System.out.println("AX in dewpresure method is: "+copyFlowStream.getLargeAX());
      System.out.println("BX in dewpresure method is: "+copyFlowStream.getLargeBX());
      nonIdealStream.flowStreamSmallBYValue();
      nonIdealStream.flowStreamLargeAYValue();
      nonIdealStream.flowStreamLargeBYValue();
      /*System.out.println("aY in dewpresure method is: "+copyFlowStream.getSmallAY());
      System.out.println("bY in dewpresure method is: "+copyFlowStream.getSmallBY());
      System.out.println("AY in dewpresure method is: "+copyFlowStream.getLargeAY());
      System.out.println("BY in dewpresure method is: "+copyFlowStream.getLargeBY());
      nonIdealStream.solveZCubicLiquid();
      nonIdealStream.solveZCubicVapour();
      System.out.println("Z_L: "+nonIdealStream.getFlowStream().getZL());
      System.out.println("Z_V: "+nonIdealStream.getFlowStream().getZV()); 
      nonIdealStream.liquidFugacity();
      nonIdealStream.vapourFugacity();
      System.out.println("Liquid Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getLiquidFugacity()+
                         "\nVapour Fugacity 1: "+nonIdealStream.getFlowStream().getFlowSpecies().get(0).getVapourFugacity());
      System.out.println("Liquid Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getLiquidFugacity()+
                         "\nVapour Fugacity 2: "+nonIdealStream.getFlowStream().getFlowSpecies().get(1).getVapourFugacity());
      for(int i=0;i<n; i++){
        double yi = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getOverallMoleFraction();
        double phiL = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidFugacity();
        double phiV = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getVapourFugacity();
        double xiOld = nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
        double xiNew=(yi*phiV)/phiL;
        nonIdealStream.getFlowStream().getFlowSpecies().get(i).setLiquidMoleFraction(xiNew);
        System.out.println("X"+i+" old is: "+xiOld);
        System.out.println("X"+i+" new is: "+xiNew);
        System.out.println("X"+i+" new actual is: "+nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction());
      }
   // }while(error > 0.001);
    
    
    double result = 0.0;
    for(int i=0; i<n; i++){
      result+=nonIdealStream.getFlowStream().getFlowSpecies().get(i).getLiquidMoleFraction();
      //System.out.println("Sum of liquid mole fractions is: "+result);
    }
    return (result-1); 
  } 
  
} */
