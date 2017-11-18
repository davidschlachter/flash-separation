public class PengRobinson{
  
  private FlowStream flowStream;
  
  //probably still needs some sort of constructor
  
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
      result = (0.45724*Math.pow((r*criticalTemp),2)*alpha)/criticalPressure;
      flowStream.getFlowSpecies().get(i).setAI(result);
    }
  }
  
  public void individualB(){
    double r = 8.3145;
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      criticalPressure = flowStream.getFlowSpecies().get(i).getCriticalPressure;
      criticalTemp = flowStream.getFlowSpecies().get(i).getCriticalTemperature();
      result = (0.0778*r*criticalTemp)/criticalPressure;
      flowStream.getFlowSpecies().get(i).setBI(result);
    }
  }
  
  //mixture parameters
  public double[] aij(){
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
  
  public void flowStreamSmallAValue(){
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        double xi = flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        double xj = flowStream.getFlowSpecies().get(j).getLiquidMoleFraction();
        result+=aij[i][j]*xi*xj;
      }
    }
    flowStream.setSmallA(result);
  }
  
  public void flowStreamSmallBValue(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    
    for(int i=0; i<n; i++){
      double xi = flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      result+=xi*bi;
    }
    flowStream.setSmallB(result);
  }
  
  public void flowStreamLargeAValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double a = flowStream.getSmallA();
    double result = (a*p)/(Math.pow((r*t),2));
    flowStream.setLargeA(result);
  }
  
  public void flowStreamLargeBValue(){
    double r = 8.3145;
    double p = flowStream.getPressure();
    double t = flowStream.getTemperature();
    double b = flowStream.getSmallB();
    double result = (b*p)/(r*t);
    flowStream.getLargeB(result);
  }
  
}