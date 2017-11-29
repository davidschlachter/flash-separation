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
        if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
           this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
           this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
          z=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        }else{
          z=this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        }
        intermediate[i] += z*aij[i][j];
      }
    }
    double result = 0.0;
    for(int i=0; i<n; i++){
      if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
        z=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      }else{
        z=this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      }
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
  
  public void streamAY(){
    
    double[][] aij = aij();
    int n = flowStream.getFlowSpecies().size();
    double[] intermediate = new double[n];
    double z;
    for(int i = 0; i<n; i++){
      for(int j = 0; j<n; j++){
        if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
           this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
           this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
          z=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
        }else{
          z=this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
        }
        intermediate[i] += z*aij[i][j];
      }
    }
    double result = 0.0;
    for(int i=0; i<n; i++){
      if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
        z=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      }else{
        z=this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
      }
      result+= intermediate[i]*z;
    }
    flowStream.setStreamAY(result);
  }
  
  public void streamBY(){
    
    int n = flowStream.getFlowSpecies().size();
    double result = 0.0;
    double y, b;
    for(int i=0; i<n; i++){
      if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
         this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
        y=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction();
      }else{
        y=this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction();
      }
      b=this.flowStream.getFlowSpecies().get(i).getSpeciesB();
      result+=y*b;
    }
    flowStream.setStreamBY(result);
  }
  
  /* public void solveFugacities(){  //error checking ongoing here
   double p, q, m, root, root0, root1, root2, qpm, tripleTheta, theta, z, z0, z1, z2, zmin, zmax;
   double liquidMixtureFugacity, vapourMixtureFugacity, bi, ai;
   double liquidSum=0.0;
   double vapourSum=0.0;
   double pressure = this.flowStream.getPressure();
   double sumTerm=0.0;
   double bx = flowStream.getStreamBX();
   double ax = flowStream.getStreamAX();
   double c0 = Math.pow(bx,3.0)+Math.pow(bx,2.0)-(ax*bx);
   double c1 = ax-3*Math.pow(bx,2.0)-2.0*bx;
   double c2 = bx-1;
   double pi = Math.PI;
   double[][] aij = aij();
   int n = this.flowStream.getFlowSpecies().size();
   double liquidSumTerm = 0.0;
   double vapourSumTerm = 0.0;
   
   double q1 = (2.0*Math.pow(c2,3.0)-9.0*c2*c1+27.0*c0)/27.0;
   double p1 = (3.0*c1-Math.pow(c2,2.0))/3.0;
   double d = Math.pow(q1,2.0)/4.0+Math.pow(p1,3.0)/27.0;
   
   if(d>=0){ 
   //System.out.println("The system has one real root");
   p=Math.pow(((-0.5*q1)+Math.sqrt(d)), 1.0/3.0);
   q=Math.pow(((-0.5*q1)-Math.sqrt(d)), 1.0/3.0);
   root = p+q;
   z=root-c2/3.0;
   liquidMixtureFugacity = pressure*1e-6*Math.exp(z-1-Math.log(z-bx)-ax/bx/2.8284*Math.log((z+2.4142*bx)/(z-0.4142*bx)));
   for(int i=0;i<n;i++){  
   bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
   liquidSum+=(bi/bx)*(z-1);
   liquidSum-=Math.log(z-bx);
   for(int j=0;j<n;j++){
   sumTerm+=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction() * aij[j][i];
   }
   liquidSum-=(ax/(2.8284*bx))*Math.log((z+2.4124*bx)/(z-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
   sumTerm=0.0;
   this.flowStream.getFlowSpecies().get(i).setLiquidFugacity(Math.exp(liquidSum));
   liquidSum=0.0;
   }
   }else{
   m = 2*Math.sqrt(-p1/3.0);
   qpm = 3.0*q1/p1/m;
   tripleTheta = Math.acos(qpm);
   theta = tripleTheta/3.0;
   root0 = m*Math.cos(theta);
   root1 = m*Math.cos(theta+4.0*pi/3.0);
   root2 = m*Math.cos(theta+2.0*pi/3.0);
   z0=root0-c2/3.0;
   z1=root1-c2/3.0;
   z2=root2-c2/3.0;
   zmin=Math.min(z0, Math.min(z1, z2));
   System.out.println("zmin is: "+zmin);
   zmax=Math.max(z0, Math.max(z1, z2));
   System.out.println("zmax is: "+zmax);
   vapourMixtureFugacity = pressure*1e-6*Math.exp(zmax-1-Math.log(zmax-bx)-ax/bx/2.8284*Math.log((zmax+2.4142*bx)/(zmax-0.4142*bx))); 
   liquidMixtureFugacity = pressure*1e-6*Math.exp(zmin-1-Math.log(zmin-bx)-ax/bx/2.8284*Math.log((zmin+2.4142*bx)/(zmin-0.4142*bx))); 
   for(int i=0;i<n;i++){  
   bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
   liquidSum+=(bi/bx)*(zmin-1);
   vapourSum+=(bi/bx)*(zmax-1);
   liquidSum-=Math.log(zmin-bx);
   vapourSum-=Math.log(zmax-bx);
   for(int j=0;j<n;j++){
   
   if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
   this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
   this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
   liquidSumTerm+=this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() * aij[i][j];
   vapourSumTerm=liquidSumTerm;
   }else{
   liquidSumTerm+=this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() * aij[j][i];  //modify these to use overall mole fraction on first iteration
   vapourSumTerm+=this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() * aij[j][i];
   }
   }
   liquidSum-=(ax/(2.8284*bx))*Math.log((zmin+2.4124*bx)/(zmin-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
   System.out.println("Liquid sum is: "+liquidSum);
   vapourSum-=(ax/(2.8284*bx))*Math.log((zmax+2.4124*bx)/(zmax-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
   System.out.println("Vapour sum is: "+vapourSum);
   sumTerm=0.0;
   
   System.out.println("Vapour sum is: "+vapourSum);
   System.out.println("Liquid sum is: "+liquidSum);
   this.flowStream.getFlowSpecies().get(i).setVapourFugacity(Math.exp(vapourSum));
   this.flowStream.getFlowSpecies().get(i).setLiquidFugacity(Math.exp(liquidSum));
   liquidSum=0.0;
   vapourSum=0.0;
   }
   
   }
   } */
  
  
  public void solveLiquidFugacities(){  //error checking ongoing here
    double p, q, m, root, root0, root1, root2, qpm, tripleTheta, theta, z, z0, z1, z2, zmin, zmax;
    double liquidMixtureFugacity, vapourMixtureFugacity, bi, ai;
    double liquidSum=0.0;
    double vapourSum=0.0;
    double pressure = this.flowStream.getPressure();
    double sumTerm=0.0;
    // double bx = flowStream.getStreamBX();
    double bx = 0.0041001;
    double ax = 0.061451;
    // double ax = flowStream.getStreamAX();
    double c0 = Math.pow(bx,3.0)+Math.pow(bx,2.0)-(ax*bx);
    double c1 = ax-3*Math.pow(bx,2.0)-2.0*bx;
    double c2 = bx-1;
    double pi = Math.PI;
    double[][] aij = aij();
    int n = this.flowStream.getFlowSpecies().size();
    
    double q1 = (2.0*Math.pow(c2,3.0)-9.0*c2*c1+27.0*c0)/27.0;
    double p1 = (3.0*c1-Math.pow(c2,2.0))/3.0;
    double d = Math.pow(q1,2.0)/4.0+Math.pow(p1,3.0)/27.0;
    
    if(d>=0){ 
      //System.out.println("The system has one real root");
      p=Math.pow(((-0.5*q1)+Math.sqrt(d)), 1.0/3.0);
      q=Math.pow(((-0.5*q1)-Math.sqrt(d)), 1.0/3.0);
      root = p+q;
      z=root-c2/3.0;
      liquidMixtureFugacity = pressure*1e-6*Math.exp(z-1-Math.log(z-bx)-ax/bx/2.8284*Math.log((z+2.4142*bx)/(z-0.4142*bx)));
      for(int i=0;i<n;i++){  
        bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
        liquidSum+=(bi/bx)*(z-1);
        liquidSum-=Math.log(z-bx);
        for(int j=0;j<n;j++){
          if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
             this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
             this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
            sumTerm+=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction() * aij[j][i];
          }else{
            sumTerm+=this.flowStream.getFlowSpecies().get(j).getLiquidMoleFraction() * aij[j][i];
          }
        }
        liquidSum-=(ax/(2.8284*bx))*Math.log((z+2.4124*bx)/(z-0.4124*bx))*((2.0*sumTerm/ax)-(bi/bx));
        sumTerm=0.0;
        this.flowStream.getFlowSpecies().get(i).setLiquidFugacity(Math.exp(liquidSum));
        liquidSum=0.0;
      }
    }else{
      m = 2*Math.sqrt(-p1/3.0);
      qpm = 3.0*q1/p1/m;
      tripleTheta = Math.acos(qpm);
      theta = tripleTheta/3.0;
      root0 = m*Math.cos(theta);
      root1 = m*Math.cos(theta+4.0*pi/3.0);
      root2 = m*Math.cos(theta+2.0*pi/3.0);
      z0=root0-c2/3.0;
      z1=root1-c2/3.0;
      z2=root2-c2/3.0;
      zmax=Math.max(z0, Math.max(z1, z2));
      zmin=Math.min(z0, Math.min(z1, z2));
      // System.out.println("zmax in liquid function is: "+zmax);
      /* System.out.println("zmin in liquid function is: "+zmin);
       System.out.println("bx in liquid fugacity calc is: "+bx);
       System.out.println("ax in liquid fugacity calc is: "+ax); */
      liquidMixtureFugacity = pressure*1e-6*Math.exp(zmax-1-Math.log(zmin-bx)-ax/bx/2.8284*Math.log((zmin+2.4142*bx)/(zmin-0.4142*bx)));
      // System.out.println("Liquid mixture fugacity is: "+liquidMixtureFugacity);
      for(int i=0;i<n;i++){  
        bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
        liquidSum+=(bi/bx)*(zmin-1);
        liquidSum-=Math.log(zmin-bx);
        for(int j=0;j<n;j++){
          if(this.flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() == 0 &&
             this.flowStream.getFlowSpecies().get(i).getVapourMoleFraction() == 0 &&
             this.flowStream.getFlowSpecies().get(i).getOverallMoleFraction() != 0){
            sumTerm+=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction() * aij[j][i];
            System.out.println("x_i="+this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction()+" aij[j][i]="+aij[j][i]);
          }else{
            sumTerm+=this.flowStream.getFlowSpecies().get(j).getLiquidMoleFraction() * aij[j][i];
            System.out.println("x_i="+this.flowStream.getFlowSpecies().get(j).getLiquidMoleFraction()+" aij[j][i]="+aij[j][i]);
          }
        }
        System.out.println("Sumterm for liquid species"+i+" is: "+sumTerm);
        liquidSum-=(ax/2.8284/bx)*Math.log((zmin+2.4142*bx)/(zmin-0.4142*bx))*((2.0*sumTerm/ax)-(bi/bx));
        sumTerm=0.0;
        this.flowStream.getFlowSpecies().get(i).setLiquidFugacity(Math.exp(liquidSum));
        // System.out.println("Liquid fugacity is: "+Math.exp(liquidSum));
        liquidSum=0.0;
      }
      
    }
  }
  
  public void solveVapourFugacities(){  //error checking ongoing here
    double p, q, m, root, root0, root1, root2, qpm, tripleTheta, theta, z, z0, z1, z2, zmin, zmax;
    double vapourMixtureFugacity, bi, ai;
    double vapourSum=0.0;
    double pressure = this.flowStream.getPressure();
    double sumTerm=0.0;
    /* double by = flowStream.getStreamBY();
     double ay = flowStream.getStreamAY();
     System.out.println("AY is :"+ay+" **************************************************************************");
     System.out.println("BY is :"+by+" **************************************************************************"); */
    double ay = 0.018135;
    double by = 0.0024102;
    double c0 = Math.pow(by,3.0)+Math.pow(by,2.0)-(ay*by);
    double c1 = ay-3*Math.pow(by,2.0)-2.0*by;
    double c2 = by-1;
    double pi = Math.PI;
    double[][] aij = aij();
    int n = this.flowStream.getFlowSpecies().size();
    
    double q1 = (2.0*Math.pow(c2,3.0)-9.0*c2*c1+27.0*c0)/27.0;
    double p1 = (3.0*c1-Math.pow(c2,2.0))/3.0;
    double d = Math.pow(q1,2.0)/4.0+Math.pow(p1,3.0)/27.0;
    
    if(d>=0){ 
      //System.out.println("The system has one real root");
      p=Math.pow(((-0.5*q1)+Math.sqrt(d)), 1.0/3.0);
      q=Math.pow(((-0.5*q1)-Math.sqrt(d)), 1.0/3.0);
      root = p+q;
      z=root-c2/3.0;
      vapourMixtureFugacity = pressure*1e-6*Math.exp(z-1-Math.log(z-by)-ay/by/2.8284*Math.log((z+2.4142*by)/(z-0.4142*by)));
      for(int i=0;i<n;i++){  
        bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
        vapourSum+=(bi/by)*(z-1);
        vapourSum-=Math.log(z-by);
        for(int j=0;j<n;j++){
          sumTerm+=this.flowStream.getFlowSpecies().get(j).getOverallMoleFraction() * aij[j][i];
        }
        vapourSum-=(ay/(2.8284*by))*Math.log((z+2.4124*by)/(z-0.4124*by))*((2.0*sumTerm/ay)-(bi/by));
        sumTerm=0.0;
        this.flowStream.getFlowSpecies().get(i).setVapourFugacity(Math.exp(vapourSum));
        // System.out.println("Vapour fugacity is: "+Math.exp(vapourSum));
        vapourSum=0.0;
      }
    }else{
      m = 2*Math.sqrt(-p1/3.0);
      qpm = 3.0*q1/p1/m;
      tripleTheta = Math.acos(qpm);
      theta = tripleTheta/3.0;
      root0 = m*Math.cos(theta);
      root1 = m*Math.cos(theta+4.0*pi/3.0);
      root2 = m*Math.cos(theta+2.0*pi/3.0);
      z0=root0-c2/3.0;
      z1=root1-c2/3.0;
      z2=root2-c2/3.0;
      zmax=Math.max(z0, Math.max(z1, z2));
      zmin=Math.min(z0, Math.min(z1, z2));
      //   System.out.println("zmax in vapour function is: "+zmax);
      //   System.out.println("zmin in vapour function is: "+zmin);
      vapourMixtureFugacity = pressure*1e-6*Math.exp(zmax-1-Math.log(zmax-by)-ay/by/2.8284*Math.log((zmax+2.4142*by)/(zmax-0.4142*by))); 
      //  System.out.println("vapourMixtureFugacity is: "+vapourMixtureFugacity);
      for(int i=0;i<n;i++){  //where debugging should begin
        bi=flowStream.getFlowSpecies().get(i).getSpeciesB();
        vapourSum+=(bi/by)*(zmax-1);
        vapourSum-=Math.log(zmax-by);
        for(int j=0;j<n;j++){
          sumTerm+=this.flowStream.getFlowSpecies().get(j).getVapourMoleFraction() * aij[j][i];
        }
        vapourSum-=(ay/(2.8284*by))*Math.log((zmax+2.4124*by)/(zmax-0.4124*by))*((2.0*sumTerm/ay)-(bi/by));
        sumTerm=0.0;
        this.flowStream.getFlowSpecies().get(i).setVapourFugacity(Math.exp(vapourSum));
        // System.out.println("Vapour fugacity is: "+Math.exp(vapourSum));
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
    //this.solveFugacities();
    this.streamAY();
    this.streamBY();
    this.solveLiquidFugacities();
    this.solveVapourFugacities();
    
    
  }
  
}