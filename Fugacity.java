public class Fugacity /* implements Function */ {
  
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
    double[][] criticalPressures = new double[n][];// = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalZValues = new double[n][];// = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalTemperatures = new double[n][];// = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalVolumes = new double[n][];// = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
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
  
  public double[] fugacityCoefficients(){
    int n = flowStream.getFlowSpecies().size();
    double[] results = new double[n];
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
      results[k] = fugacityCoefficient;
      sumTerm = 0.0;
    }
    return results;
  }//end of fugacity coefficients method
  
  public FlowStream getFlowStream() {
    return this.flowStream;
  }
  
 
}//end of Fugacity class
