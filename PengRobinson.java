public class PengRobinson implements Function{
  
  private FlowStream flowStream;
  
  public PengRobinson(FlowStream flowStream){
    this.flowStream = flowStream;
  }
  
  //flowstream getter
  public FlowStream getFlowStream(){
    return this.flowStream;
  }
  
  //flowstream setter
  public void setFlowStream(FlowStream flowStream){
    this.flowStream = flowStream;
  }
  
  //individual component calculated parameters
  public void kappaI(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double omega = flowStream.getFlowSpecies().get(i).getAcentricFactor();
      result = 0.37464+1.54226*omega-0.26992*Math.pow(omega, 2.);
      flowStream.getFlowSpecies().get(i).setKappa(result);
    }
  }
  
  public void alphaI(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    double temperature = flowStream.getTemperature();
    
    for(int i=0; i<n; i++){
      double criticalTemp = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      double kappa = flowStream.getFlowSpecies().get(i).getKappa();
      result=Math.pow((1+kappa*(1-Math.pow((temperature/criticalTemp),0.5))),2.);
      flowStream.getFlowSpecies().get(i).setAlpha(result);
    }
  }
  
  public void individualA(){
    double r = 8.3145;
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double criticalTemp = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      double criticalPressure = flowStream.getFlowSpecies().get(i).getCriticalPressure();
      double alpha = flowStream.getFlowSpecies().get(i).getAlpha();
      result = (0.45724*(Math.pow((r*criticalTemp),2))*alpha)/criticalPressure;
      flowStream.getFlowSpecies().get(i).setAI(result);
    }
  }
  
  public void individualB(){
    double r = 8.3145;
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double criticalPressure = flowStream.getFlowSpecies().get(i).getCriticalPressure();
      double criticalTemp = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      result = (0.0778*r*criticalTemp)/criticalPressure;
      flowStream.getFlowSpecies().get(i).setBI(result);
    }
  }
  
  //mixture parameters
  public double[][] aij(){
    int n=flowStream.getFlowSpecies().size();
    double[][] results = new double[n][n];
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        double ai = flowStream.getFlowSpecies().get(i).getAI();
        double aj = flowStream.getFlowSpecies().get(j).getAI();
        results[i][j]=Math.pow((ai*aj),0.5);
      }
    }
    return results;
  }
  
  //mixture parameters for calculating liquid fugacity
  public void flowStreamSmallAXValue(){
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        double xi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        double xj = flowStream.getFlowSpecies().get(j).getOverallMoleFraction();
        result+=aij[i][j]*xi*xj;
      }
    }
    flowStream.setSmallAX(result);
  }
  
  public void flowStreamSmallBXValue(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double xi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      result+=xi*bi;
    }
    flowStream.setSmallBX(result);
  }
  
  public void flowStreamLargeAXValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double a = flowStream.getSmallAX();
    double result = (a*p)/(Math.pow((r*t),2));
    flowStream.setLargeAX(result);
  }
  
  public void flowStreamLargeBXValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double b = flowStream.getSmallBX();
    double result = (b*p)/(r*t);
    flowStream.setLargeBX(result);
  }
  
  public void solveZCubic(){
    double firstGuess = 0.95;
    int count = 0;
    double[] bounds = RootFinder.getBounds(this, firstGuess, 0.01);
    double result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
    while(result > 1 && count < 10){
    bounds = RootFinder.getBounds(this, firstGuess - 0.1, 0.01);
    result = RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);  //thids shit is all fucked up
    count++;
    }
    System.out.println("Z result is: "+result);
    System.out.println("count is: "+count); 
    
  }
  
  public void liquidFugacity(){
    double smallB = flowStream.getSmallBX();
    double a = flowStream.getLargeAX();
    double b = flowStream.getLargeBX();
    double zL = flowStream.getZL();
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    
    for(int i=0; i<n; i++){
      double lnPhiL = 0.0;
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      lnPhiL+=(bi/b)*(zL-1);
      lnPhiL-=Math.log(zL-b);
      double sumTerm = 0.0;
      for(int j=0; j<n; j++){
        double xj = flowStream.getFlowSpecies().get(j).getLiquidMoleFraction();
        sumTerm += xj * aij[i][j];
      }
      lnPhiL-=(a/(2*Math.sqrt(2)*b))*(((2*sumTerm)/a)-(bi/b))*Math.log((zL+(1+Math.sqrt(2)*b))/(zL+(1-Math.sqrt(2))));
      double phiL = Math.exp(lnPhiL);
      flowStream.getFlowSpecies().get(i).setLiquidFugacity(phiL);
    }
    
  }
  
  
  
  public void flowStreamSmallAYValue(){
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        double yi = flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
        double yj = flowStream.getFlowSpecies().get(j).getVapourMoleFraction();
        result+=aij[i][j]*yi*yj;
      }
    }
    flowStream.setSmallAY(result);
  }
  
  public void flowStreamSmallBYValue(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double yi = flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      result+=yi*bi;
    }
    flowStream.setSmallBY(result);
  }
  
  public void flowStreamLargeAYValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double a = flowStream.getSmallAY();
    double result = (a*p)/(Math.pow((r*t),2));
    flowStream.setLargeAY(result);
  }
  
  public void flowStreamLargeBYValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double b = flowStream.getSmallBY();
    double result = (b*p)/(r*t);
    flowStream.setLargeBY(result);
  }
  
  public void solveZCubicVapour(){
    double firstGuess = 1.0;
    int count = 0;
    double[] bounds = RootFinder.getBounds(this, firstGuess, 0.01);
    double result =  RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);
    while(result > 1 && count < 10){
    bounds = RootFinder.getBounds(this, firstGuess - 0.1, 0.01);
    result = RiddersMethod.calc(this, bounds[0], bounds[1], 0.001);  //this shit is all fucked up
    count++;
    }
    System.out.println("Z result is: "+result);
    System.out.println("count is: "+count); 
    
  }
  
  
  public void vapourFugacity(){
    double smallB = flowStream.getSmallBY();
    double a = flowStream.getLargeAY();
    double b = flowStream.getLargeBY();
    double zV = flowStream.getZV();
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    
    for(int i=0; i<n; i++){
      double lnPhiV = 0.0;
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      lnPhiV+=(bi/b)*(zV-1);
      lnPhiV-=Math.log(zV-b);
      double sumTerm = 0.0;
      for(int j=0; j<n; j++){
        double yj = flowStream.getFlowSpecies().get(j).getVapourMoleFraction();
        sumTerm += yj * aij[i][j];
      }
      lnPhiV-=(a/(2*Math.sqrt(2)*b))*(((2*sumTerm)/a)-(bi/b))*Math.log((zV+(1+Math.sqrt(2)*b))/(zV+(1-Math.sqrt(2))));
      double phiV = Math.exp(lnPhiV);
      flowStream.getFlowSpecies().get(i).setVapourFugacity(phiV);
    }
    
  }
  
    
    public double testFunction(double z){
    
    double a = this.flowStream.getLargeAX();
    double b = this.flowStream.getLargeBX();
    double result = z*z*z+(b-1)*z*z+(a-3.0*b*b-2.0*b)*z+(b*b*b+b*b-a*b);
    return result;
    
  }
  
}