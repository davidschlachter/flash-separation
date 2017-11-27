public class PengRobinson{
  
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
    double xi, xj;
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
          xi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
          xj = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        } else{
          xi = flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
          xj = flowStream.getFlowSpecies().get(j).getLiquidMoleFraction();
        }
        result+=aij[i][j]*xi*xj;
      }
    }
    flowStream.setSmallAX(result);
  }
  
  public void flowStreamSmallBXValue(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    double xi, xj, bi;
    
    for(int i=0; i<n; i++){
      if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
         flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
         flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
        xi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        System.out.println("Using overall mole fraction in PR: "+xi);
      } else{
        xi = flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        System.out.println("Using liquid mole fraction in PR: "+xi);
      }
      bi = flowStream.getFlowSpecies().get(i).getBI();
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
  
  public void solveZCubicLiquid(){
    double b = this.flowStream.getLargeBX();
    double a = this.flowStream.getLargeAX();
    double c0 = Math.pow(b,3.0)+Math.pow(b,2.0)-(a*b);
    double c1 = a-3*Math.pow(b,2.0)-2.0*b;
    double c2 = b-1;
    
    double q1 = ((c2*c1)/6.0)-c0/2-(Math.pow(c2,3)/27.0);
    double p1 = Math.pow(c2,2)/9.0-c1/3.0;
    double d = Math.pow(q1,2.0)-Math.pow(p1,3.0);
    
    if(d>=0){
      //System.out.println("The system has one real root");
      double z = Math.pow((q1+Math.pow(d,0.5)),(1.0/3.0))+Math.pow((q1-Math.pow(d,0.5)),(1.0/3.0))-(c2/3.0);
      this.flowStream.setZL(z);
    }else{
      double t1 = (Math.pow(q1,2.0))/(Math.pow(p1,3.0));
      double t2 = (Math.sqrt(1-t1)/Math.sqrt(t1))*q1/Math.abs(q1);
      double theta = Math.atan(t2);
      double z0 = 2*Math.sqrt(p1)*Math.cos(theta/3.0)-(c2/3.0);
      double z1 = 2*Math.sqrt(p1)*Math.cos((2*Math.PI+theta)/3.0)-(c2/3.0);
      double z2 = 2*Math.sqrt(p1)*Math.cos((4*Math.PI+theta)/3.0)-(c2/3.0);
      double[] z = new double[3];
      if(z0<z1 && z0<z2) {
        z[0] = z0;
        if(z1<z2) {
          z[1] = z1;
          z[2] = z2;
        }
        else {
          z[1] = z[2];
          z[2] = z1;
        }
      }
      else if(z1<z0 && z1<z2) {
        z[0] = z1;
        if(z0<z2) {
          z[1] = z0;
          z[2] = z2;
        }
        else {
          z[1] = z2;
          z[2] = z0;
        }
      }
      else {
        z[0] = z2;
        if(z0<z1) {
          z[1] = z0;
          z[2] = z1;
        }
        else {
          z[1] = z1;
          z[2] = z0;
        }
      }
      //double zL = Math.min(z0, Math.min(z1, z2));
      //double zV = Math.max(z0, Math.max(z1, z2));
      //double zL;
      // Check that minimum is greater than zero (ish)
      /*if(z[0] > 0) zL = z[0];
      else if(z[1] > 0) zL = z[1];
      else zL = z[2];*/
      this.flowStream.setZL(z[1]);
      //this.flowStream.setZV(z[2]);
      System.out.println("Three roots of zL are: "+z[0]+", "+z[1]+" and "+z[2]);
      double zL = Math.min(z0, Math.min(z1, z2));
      double zV = Math.max(z0, Math.max(z1, z2));
      flowStream.setZL(zL);
      // flowStream.setZV(zV);
    }
    
  }
  
  public void liquidFugacity(){
    double smallB = flowStream.getSmallBX();
    double a = flowStream.getLargeAX();
    double b = flowStream.getLargeBX();
    double zL = flowStream.getZL();
    double xj;
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    
    for(int i=0; i<n; i++){
      double lnPhiL = 0.0;
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      lnPhiL+=(bi/smallB)*(zL-1);
      lnPhiL-=Math.log(zL-b);
      double sumTerm = 0.0;
      for(int j=0; j<n; j++){
        if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
          xj = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        } else {
          xj = flowStream.getFlowSpecies().get(j).getLiquidMoleFraction();
        }
        sumTerm += xj * aij[i][j];
      }
      lnPhiL-=(a/(2*Math.sqrt(2)*b))*(((2*sumTerm)/a)-(bi/smallB))*Math.log((zL+(1+Math.sqrt(2))*b)/(zL+(1-Math.sqrt(2))*b));
      double phiL = Math.exp(lnPhiL);
      flowStream.getFlowSpecies().get(i).setLiquidFugacity(phiL);
    }
    
  }
  
  
  
  public void flowStreamSmallAYValue(){
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    double result = 0.0;
    double yi, yj;
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
          yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
          yj = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        } else{
          yi = flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
          yj = flowStream.getFlowSpecies().get(j).getVapourMoleFraction();
        }
        result+=aij[i][j]*yi*yj;
      }
    }
    flowStream.setSmallAY(result);
  }
  
  public void flowStreamSmallBYValue(){
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    double bi, yi, yj;
    
    for(int i=0; i<n; i++){
      if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
          yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        } else{
          yi = flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
        }
      bi = flowStream.getFlowSpecies().get(i).getBI();
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
    //System.out.println("LArge B value is: "+b);
    flowStream.setLargeBY(result);
  }
  
  
  public void solveZCubicVapour(){
    double b = flowStream.getLargeBY();
    double a = flowStream.getLargeAY();
    double c0 = Math.pow(b,3.0)+Math.pow(b,2.0)-(a*b);
    double c1 = a-3*Math.pow(b,2.0)-2.0*b;
    double c2 = b-1;
    
    double q1 = ((c2*c1)/6.0)-c0/2-(Math.pow(c2,3)/27.0);
    double p1 = Math.pow(c2,2)/9.0-c1/3.0;
    double d = Math.pow(q1,2.0)-Math.pow(p1,3.0);
    
    if(d>=0){
      //System.out.println("The system has one real root");
      double z = Math.pow((q1+Math.pow(d,0.5)),(1.0/3.0))+Math.pow((q1-Math.pow(d,0.5)),(1.0/3.0))-(c2/3.0);
      flowStream.setZV(z);
    }else{
      double t1 = (Math.pow(q1,2.0))/(Math.pow(p1,3.0));
      double t2 = Math.pow((1-t1),0.5)/Math.sqrt(t1)*q1/Math.abs(q1);
      double theta = Math.atan(t2);
      double z0 = 2*Math.sqrt(p1)*Math.cos(theta/3.0)-(c2/3.0);
      double z1 = 2*Math.sqrt(p1)*Math.cos((2*Math.PI+theta)/3.0)-(c2/3.0);
      double z2 = 2*Math.sqrt(p1)*Math.cos((4*Math.PI+theta)/3.0)-(c2/3.0);
      //double zL = Math.min(z0, Math.min(z1, z2));
      double zV = Math.max(z0, Math.max(z1, z2));
      //flowStream.setZL(zL);
      flowStream.setZV(zV);
      System.out.println("Three roots of zV are: "+z0+", "+z1+" and "+z2);
    }
  }
  
  public void vapourFugacity(){
    double smallB = flowStream.getSmallBY();
    double a = flowStream.getLargeAY();
    double b = flowStream.getLargeBY();
    double zV = flowStream.getZV();
    double yj;
    int n = flowStream.getFlowSpecies().size();
    double[][] aij = aij();
    
    for(int i=0; i<n; i++){
      double lnPhiV = 0.0;
      double bi = flowStream.getFlowSpecies().get(i).getBI();
      lnPhiV+=(bi/smallB)*(zV-1);
      lnPhiV-=Math.log(zV-b);
      double sumTerm = 0.0;
      for(int j=0; j<n; j++){
        if(flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0.0 &&
           flowStream.getFlowSpecies().get(i).getOverallMoleFraction() > 0.0){
          yj = flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        } else {
          yj = flowStream.getFlowSpecies().get(j).getVapourMoleFraction();
        }
        sumTerm += yj * aij[i][j];
      }
      lnPhiV-=(a/(2*Math.sqrt(2)*b))*(((2*sumTerm)/a)-(bi/smallB))*Math.log((zV+(1+Math.sqrt(2))*b)/(zV+(1-Math.sqrt(2))*b));
      double phiV = Math.exp(lnPhiV);
      flowStream.getFlowSpecies().get(i).setVapourFugacity(phiV);
    }
    
  }
  
  public void nonIdealCalcs(){
  
  this.kappaI();
  this.alphaI();
  this.individualA();
  this.individualB();
  this.flowStreamSmallAXValue();
  this.flowStreamSmallBXValue();
  this.flowStreamLargeAXValue();
  this.flowStreamLargeBXValue();
  this.flowStreamSmallAYValue();
  this.flowStreamSmallBYValue();
  this.flowStreamLargeAYValue();
  this.flowStreamLargeBYValue();
  this.solveZCubicLiquid();
  this.solveZCubicVapour();
  this.liquidFugacity();
  this.vapourFugacity();
  
  }
  
}
