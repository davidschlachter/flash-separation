public class Fugacity  {
  
  private FlowStream flowStream;
  
  public Fugacity(FlowStream flowStream) { 
    this.flowStream = flowStream;
  }//end of constructor
  
  
  public double[][] crossSpeciesCriticalZ(){
    double[][] criticalZValues;
    int n = flowStream.getFlowSpecies().size();
    criticalZValues = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      criticalZValues[i] = new double[i+1];
    }
    for(i=0; i<n; i++) {
      for(j=0; j<=i; j++) {
        criticalZValues[i][j] = (flowStream.getFlowSpecies().get(i).getCriticalZ() + flowStream.getFlowSpecies().get(j).getCriticalZ())/2.;
      }
    }
    return criticalZValues;
  }//end of crossSpeciesZ method 
  
  public double[][] crossSpeciesCriticalVolume(){
    double[][] criticalVolumes;
    int n = flowStream.getFlowSpecies().size();
    criticalVolumes = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      criticalVolumes[i] = new double[i+1];
    }
    for(i=0; i<n; i++){
      for(j=0; j<=i; j++){
        criticalVolumes[i][j] = (Math.pow(((Math.pow(flowStream.getFlowSpecies().get(i).getCriticalVolume(), (1.0/3.0)) + 
                                            Math.pow(flowStream.getFlowSpecies().get(j).getCriticalVolume(), (1.0/3.0)))/2.0), 3.0)) * 1e-6; //units of m3/mol
      }
    }
    return criticalVolumes;
  }//end of crossSpeciesCriticalVolume method 
  
  public double[][] crossSpeciesCriticalTemperature(){
    
    double[][] criticalTemperatures;;
    int n = flowStream.getFlowSpecies().size();
    criticalTemperatures = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      criticalTemperatures[i] = new double[i+1];
    }
    for(i = 0; i < n; i++){
      for(j = 0; j <= i; j++){
        criticalTemperatures[i][j] = Math.pow((flowStream.getFlowSpecies().get(i).getCriticalTemperature() * 
                                               flowStream.getFlowSpecies().get(j).getCriticalTemperature()),0.5);
      }
    }
    return criticalTemperatures;
    
  }//end of crossSpeciesCriticalTemperature method
  
  public double[][] crossSpeciesCriticalPressure(){
    int n = flowStream.getFlowSpecies().size();
    double[][] criticalPressures = new double[n][];
    double[][] criticalZValues = new double[n][];
    double[][] criticalTemperatures = new double[n][];
    double[][] criticalVolumes = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      criticalPressures[i] = new double[i+1];
      criticalZValues[i] = new double[i+1];
      criticalTemperatures[i] = new double[i+1];
      criticalVolumes[i] = new double[i+1];
    }
    criticalZValues = crossSpeciesCriticalZ();
    criticalTemperatures = crossSpeciesCriticalTemperature();
    criticalVolumes = crossSpeciesCriticalVolume();
    double R = 8.3145;
    for(i = 0; i < n; i++){
      for(j = 0; j <= i; j++){
        criticalPressures[i][j] = (criticalZValues[i][j] * criticalTemperatures[i][j] * R) / criticalVolumes[i][j]; //units of Pa
      }
    }
    
    return criticalPressures;
    
  }//end of crossSpeciesCriticalPressure() method  
  
  public double[][] omegaIJ(){
    
    double[][] omegaIJ;
    int n = flowStream.getFlowSpecies().size();
    omegaIJ = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      omegaIJ[i] = new double[i+1];
    }
    for(i = 0; i < n; i++){
      for(j = 0; j <= i; j++){
        omegaIJ[i][j] = (flowStream.getFlowSpecies().get(i).getAcentricFactor() + 
                         flowStream.getFlowSpecies().get(j).getAcentricFactor()) * 0.5;
      }
    }
    
    return omegaIJ;
    
  }//end of omegaIJ method
  
  public double[][] bValues(){
    int n = flowStream.getFlowSpecies().size();
    double[][] bij;
    bij = new double[n][];
    int i = 0;
    int j = 0;
    for(i=0; i<n; i++) {
      bij[i] = new double[i+1];
    }
    double criticalTemperatures[][] = crossSpeciesCriticalTemperature();
    double criticalPressures[][] = crossSpeciesCriticalPressure();
    double acentricFactors[][] = omegaIJ();
    double b0 = 0.0;
    double b1 = 0.0;
    double R = 8.3145;
    for(i = 0; i < n; i++){
      for(j = 0; j <= i; j++){
        b0 = 0.083 - (0.422/(Math.pow((flowStream.getTemperature()/criticalTemperatures[i][j]), 1.6)));
        b1 = 0.139 - (0.172/(Math.pow((flowStream.getTemperature()/criticalTemperatures[i][j]), 4.2)));
        bij[i][j] = (((b0 + b1 * acentricFactors[i][j]) * criticalTemperatures[i][j] * R) / criticalPressures[i][j]);
      }
    }
    return bij;
  }//end of bValues method
  
  public void mixtureFugacityCoefficients(){
    int n = flowStream.getFlowSpecies().size();
    double[][] bij = bValues();
    
    int i = 0;
    int j = 0; 
    int k = 0;
    double R = 8.3145;
    double T = flowStream.getTemperature();
    double P = flowStream.getPressure();
    
    for(k = 0; k < n; k++){
      double fugacityCoefficient = 0.0;
      double sumTerm = 0.0;
      for(i = 0; i < n; i++){
        for(j = 0; j < i; j++){
          if(i>k)
            sumTerm += flowStream.getFlowSpecies().get(i).getVapourMoleFraction() * 
            flowStream.getFlowSpecies().get(j).getVapourMoleFraction() * 
            ( (4 * bij[i][k]) - bij[i][i] - (2 * bij[k][k]) - (2 * bij[i][j]) + bij[j][j]);
          else
            sumTerm += flowStream.getFlowSpecies().get(i).getVapourMoleFraction() * 
            flowStream.getFlowSpecies().get(j).getVapourMoleFraction() * 
            ( (4 * bij[k][i]) - bij[i][i] - (2 * bij[k][k]) - (2 * bij[i][j]) + bij[j][j]);
        }
      }
      fugacityCoefficient = Math.exp((P / (R * T)) * (bij[k][k] + (0.5 * sumTerm)));
      flowStream.getFlowSpecies().get(k).setMixtureFugacityCoefficient(fugacityCoefficient);
      sumTerm = 0.0;
    }
  }//end of fugacity coefficients method
  
  public void beta(){
    double srkOmega = 0.08664;
    double beta = 0.0;
    int i = 0;
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      beta = srkOmega * (flowStream.getPressure() / (flowStream.getFlowSpecies().get(i).getCriticalPressure())) /
        (flowStream.getTemperature() / (flowStream.getFlowSpecies().get(i).getCriticalTemperature())); 
      flowStream.getFlowSpecies().get(i).setBeta(beta);
    }
  }
  
  public FlowStream getFlowStream() {
    return this.flowStream;
  }
  
  public void qValue(){
    double psi = 0.42748;
    double omega = 0.08664;
    double alpha = 0.0;
    double[] acentricFactors = new double[flowStream.getFlowSpecies().size()];
    double result = 0.0;
    int i = 0;
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      acentricFactors[i] = flowStream.getFlowSpecies().get(i).getAcentricFactor();
    }
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      alpha = Math.pow((1 + (0.480 + 1.574 * acentricFactors[i] - Math.pow((0.176 * acentricFactors[i]),2)) * 
                        (1 - Math.pow(flowStream.getTemperature() / flowStream.getFlowSpecies().get(i).getCriticalTemperature(), 0.5))),2);
      result = (psi * alpha) / (omega * (flowStream.getTemperature() / flowStream.getFlowSpecies().get(i).getCriticalTemperature()));
      flowStream.getFlowSpecies().get(i).setQValue(result);
    }
  }

  
  public void flowStreamZValues(){
    for(int i=0; i< flowStream.getFlowSpecies().size(); i++){
    flowStream.getFlowSpecies().get(i).zValue();
    }
  }
  
  public void activityCoefficient(){
    
    double pureSpeciesFugacityCoefficient = 0.0;
    int i = 0;
    
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      pureSpeciesFugacityCoefficient = Math.exp(flowStream.getFlowSpecies().get(i).getZValue() - 1 - Math.log(flowStream.getFlowSpecies().get(i).getZValue()
                                                                                                                - flowStream.getFlowSpecies().get(i).getBeta()) - flowStream.getFlowSpecies().get(i).getQValue()
                                                  * flowStream.getFlowSpecies().get(i).getBeta() / flowStream.getFlowSpecies().get(i).getZValue());
      flowStream.getFlowSpecies().get(i).setActivityCoefficient((flowStream.getFlowSpecies().get(i).getMixtureFugacityCoefficient() * 
                                                                 flowStream.getFlowSpecies().get(i).getVapourMoleFraction()) / 
                                                                (flowStream.getFlowSpecies().get(i).getLiquidMoleFraction() * 
                                                                 pureSpeciesFugacityCoefficient));
    }
    
  }
  
  public void largePhi(){
    
    double R = 8.3145;
    
    double[] phiSat = new double[flowStream.getFlowSpecies().size()];
    int n = flowStream.getFlowSpecies().size();
    double[][] bValues = new double[n][];
    for(int i=0; i<n; i++) {
      bValues[i] = new double[i+1];
    }
    bValues=bValues();
    for(int i=0; i<n; i++){
      phiSat[i]=Math.exp((bValues[i][i]*SaturationPressure.calc(flowStream.getFlowSpecies().get(i),flowStream.getTemperature()))/(R * flowStream.getTemperature()));
      flowStream.getFlowSpecies().get(i).setLargePhi(flowStream.getFlowSpecies().get(i).getMixtureFugacityCoefficient()/phiSat[i]);
    }  
  }
  
  public void computeNonIdealParameters(Fugacity fugacityObject){
    
    /* TODO: checks must be implemented here to make sure all parameters are in place
     * for the proper calculation of nonideal parameters */
    
    fugacityObject.mixtureFugacityCoefficients();
    fugacityObject.beta();
    fugacityObject.qValue();
    fugacityObject.flowStreamZValues();
    fugacityObject.activityCoefficient();
    if(fugacityObject.getFlowStream().getFlowSpecies().size() > 1){      // large Phi can only be computed for multicomponent streams. 
      fugacityObject.largePhi();                                           //an arrayIndexOutOfBounds error is thrown if run w/ 1 species
    }
    
  }
  
  public boolean nonIdealComputed(Fugacity unmodifiedStream){
    boolean result = true;
    
    for(int i=0; i<unmodifiedStream.getFlowStream().getFlowSpecies().size(); i++){
      
      if(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getMixtureFugacityCoefficient() == this.getFlowStream().getFlowSpecies().get(i).getMixtureFugacityCoefficient()) result = false;
      if(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getActivityCoefficient() == this.getFlowStream().getFlowSpecies().get(i).getActivityCoefficient()) result = false;
      if(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getLargePhi() == this.getFlowStream().getFlowSpecies().get(i).getLargePhi()) result = false;
      if(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getBeta() == this.getFlowStream().getFlowSpecies().get(i).getBeta()) result = false;
      if(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getQValue() == this.getFlowStream().getFlowSpecies().get(i).getQValue()) result = false;
      if(Math.abs(unmodifiedStream.getFlowStream().getFlowSpecies().get(i).getZValue() - this.getFlowStream().getFlowSpecies().get(i).getZValue()) < 0.01) result = false;

    }
    return result;
  }
  
}



