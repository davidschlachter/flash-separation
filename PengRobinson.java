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
      result = (0.45724*(Math.pow((r*criticalTemp),2))*alpha)/(criticalPressure*1e-6);
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
      result = (0.0778*r*criticalTemp)/(criticalPressure*1e-6);
      flowStream.getFlowSpecies().get(i).setBI(result);
    }
  }
  
  public void speciesA(){
    
    double r = 8.3145;
    double p = this.flowStream.getPressure() * 1e-6;
    double t = this.flowStream.getTemperature();
    int n = this.flowStream.getFlowSpecies().size();
    double a;
    
    for (int i=0; i<n; i++){
      a=this.flowStream.getFlowSpecies().get(i).getAI();
      this.flowStream.getFlowSpecies().get(i).setSpeciesA((a*p)/(r*r*t*t));
    }
    
  }
  
  public void speciesB(){
    
    double r = 8.3145;
    double p = this.flowStream.getPressure() * 1e-6;
    double t = this.flowStream.getTemperature();
    int n = this.flowStream.getFlowSpecies().size();
    double b;
    
    for (int i=0; i<n; i++){
      b=this.flowStream.getFlowSpecies().get(i).getBI();
      this.flowStream.getFlowSpecies().get(i).setSpeciesB((b*p)/(r*t));
    }
    
  }
  
//mixture parameters
  public double[][] aij(){
    int n=flowStream.getFlowSpecies().size();
    double[][] results = new double[n][n];
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        double ai = flowStream.getFlowSpecies().get(i).getSpeciesA();
        double aj = flowStream.getFlowSpecies().get(j).getSpeciesA();
        results[i][j]=Math.pow((ai*aj),0.5);
      }
    }
    return results;
  }
  
  public void streamAX(){
    
    double[][] aij = aij();
    int n = flowStream.getFlowSpecies().size();
    double[] intermediate = new double[n];
    double z;
    for(int i = 0; i<n; i++){
      for(int j = 0; j<n; j++){
        z=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction();
        intermediate[i] += z*aij[i][j];
      }
    }
    double result = 0.0;
    for(int i=0; i<n; i++){
      z=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      result+= intermediate[i]*z;
    }
    flowStream.setStreamAX(result);
  }
  
  public void streamBX(){
    
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    double x, b;
    for(int i=0; i<n; i++){
      if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
        x=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      }else{
          x=this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        }
        b=this.flowStream.getFlowSpecies().get(i).getSpeciesB();
        result+=x*b;
    }
    flowStream.setStreamBX(result);
  }
  
  
  public void solveFugacities(){  //error checking ongoing here
    double p, q, m, root, root0, root1, root2, qpm, tripleTheta, theta, z, z0, z1, z2, zmin, zmax;
    double liquidMixtureFugacity, vapourMixtureFugacity, pressure, bi, ai;
    double liquidSum=0.0;
    double vapourSum=0.0;
    double sumTerm=0.0;
    double bx = flowStream.getStreamBX();
    double ax = flowStream.getStreamAX();
//    double ay = flowStream.getStreamAY();
//    double by = flowStream.getStreamBY();
    double c0 = Math.pow(bx,3.0)+Math.pow(bx,2.0)-(ax*bx);
    double c1 = ax-3*Math.pow(bx,2.0)-2.0*bx;
    double c2 = bx-1;
    double pi = Math.PI;
    double[][] aij = aij();
    
    double q1 = (2.0*Math.pow(c2,3.0)-9.0*c2*c1+27.0*c0)/27.0;
    double p1 = (3.0*c1-Math.pow(c2,2.0))/3.0;
    double d = Math.pow(q1,2.0)/4.0+Math.pow(p1,3.0)/27.0;
    
    if(d>=0){ //checks done up to this point
      //System.out.println("The system has one real root");
      p=Math.pow(((-0.5*q1)+Math.sqrt(d)), 1.0/3.0);
      q=Math.pow(((-0.5*q1)-Math.sqrt(d)), 1.0/3.0);
      root = p+q;
      System.out.println("ONLY ONE ROOT! figure out what this means and fix.");  //gotta finish this up
    }else{
      m = 2*Math.sqrt(-p1/3.0);
      qpm = 3.0*q1/p1/m;
      tripleTheta = Math.acos(qpm);
      theta = tripleTheta/3.0;
      root0 = m*Math.cos(theta);
      root1 = m*Math.cos(theta+4.0*pi/3.0);
      root2 = m*Math.cos(theta+2.0*pi/3.0);
      int n = this.flowStream.getFlowSpecies().size();
      z0=root0-c2/3.0;
      z1=root1-c2/3.0;
      z2=root2-c2/3.0;
      zmin=Math.min(z0, Math.min(z1, z2));
      zmax=Math.max(z0, Math.max(z1, z2));
      pressure = this.flowStream.getPressure();
      vapourMixtureFugacity = pressure*1e-6*Math.exp(zmax-1-Math.log(zmax-bx)-ax/bx/2.8284*Math.log((zmax+2.4142*bx)/(zmax-0.4142*bx)));
      liquidMixtureFugacity = pressure*1e-6*Math.exp(zmin-1-Math.log(zmin-bx)-ax/bx/2.8284*Math.log((zmin+2.4142*bx)/(zmin-0.4142*bx))); 
      for(int i=0;i<n;i++){  
        bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
        vapourSum+=(bi/bx)*(zmax-1);
        liquidSum+=(bi/bx)*(zmin-1);
        vapourSum-=Math.log(zmax-bx);
        liquidSum-=Math.log(zmin-bx);
        for(int j=0;j<n;j++){
          sumTerm+=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction() * aij[j][i];
        }
        vapourSum-=(ax/(2.8284*bx))*Math.log((zmax+2.4124*bx)/(zmax-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
        liquidSum-=(ax/(2.8284*bx))*Math.log((zmin+2.4124*bx)/(zmin-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
        sumTerm=0.0;
        this.flowStream.getFlowSpecies().get(i).setVapourFugacity(Math.exp(vapourSum));
        this.flowStream.getFlowSpecies().get(i).setLiquidFugacity(Math.exp(liquidSum));
        liquidSum=0.0;
        vapourSum=0.0;
      }
      
    }
  }
  
  public void nonIdealCalcs(){
    
    this.kappaI();
    this.alphaI();
    this.individualA();
    this.individualB();
    this.speciesA();
    this.speciesB();
    this.streamAX();
    this.streamBX();
   // this.streamAY();
   // this.streamBY();
    this.solveFugacities();
    
  }
  
}