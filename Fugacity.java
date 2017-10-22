public class Fugacity /* implements Function*/ {
  
  private FlowStream flowStream;
  
  public Fugacity(FlowStream flowStream) { 
    
    int i = 0;
    boolean nonIdealInitialized = false;
    
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
    if (flowStream.getFlowSpecies().get(i).getCriticalTemperature() > 0 && flowStream.getFlowSpecies().get(i).getCriticalPressure() > 0 &&
        flowStream.getFlowSpecies().get(i).getCriticalVolume() > 0 && flowStream.getFlowSpecies().get(i).getCriticalZ() > 0) {
      nonIdealInitialized = true;
    } else {};
    }
    if(nonIdealInitialized){
    this.flowStream = flowStream;
    } else {
      System.out.println("Error: non-ideal stream must have all non-ideal parameters!");
      System.exit(1);
    
  }
  
}//end of constructor
  
  public double[][] crossSpeciesCriticalZ(){
  
  double[][] criticalZValues = new double[flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
  
  int i = 0;
  int j = 0;
  
  for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
    for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
    criticalZValues[i][j] = (flowStream.getFlowSpecies().get(i).getCriticalZ() + flowStream.getFlowSpecies().get(j).getCriticalZ()) / 2.0;
    }
  }
  
  return criticalZValues;
  
  }//end of crossSpeciesZ method 
  
  public double[][] crossSpeciesCriticalVolume(){
  
  double[][] criticalVolumes = new double[flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
  
  int i = 0;
  int j = 0;
  
   for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
    for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
    criticalVolumes[i][j] = (Math.pow(((Math.pow(flowStream.getFlowSpecies().get(i).getCriticalVolume(), (1.0/3.0)) + 
                             Math.pow(flowStream.getFlowSpecies().get(j).getCriticalVolume(), (1.0/3.0)))/2.0), 3.0)) * 1e-6; //units of m3/mol
    }
  }
   
   return criticalVolumes;
  
  }//end of crossSpeciesCriticalVolume method 
  
  public double[][] crossSpeciesCriticalTemperature(){
  
   double[][] criticalTemperatures = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
   
   int i = 0;
   int j = 0;
   
   for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
     for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
     criticalTemperatures[i][j] = Math.pow((flowStream.getFlowSpecies().get(i).getCriticalTemperature() * 
                                           flowStream.getFlowSpecies().get(j).getCriticalTemperature()),0.5);
     }
   }
   
   return criticalTemperatures;
   
  }//end of crossSpeciesCriticalTemperature method
  
  public double[][] crossSpeciesCriticalPressure(){
  
    double[][] criticalPressures = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalZValues = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalTemperatures = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    double[][] criticalVolumes = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    
    criticalZValues = crossSpeciesCriticalZ();
    criticalTemperatures = crossSpeciesCriticalTemperature();
    criticalVolumes = crossSpeciesCriticalVolume();
    
    int i = 0;
    int j = 0;
    double R = 8.3145;
    
    for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
       criticalPressures[i][j] = (criticalZValues[i][j] * criticalTemperatures[i][j] * R) / criticalVolumes[i][j]; //units of Pa
    }
  }
    
    return criticalPressures;
  
  }//end of crossSpeciesCriticalPressure() method  
  
  public double[][] omegaIJ(){
  
  double[][] omegaIJ = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
  
  int i = 0;
  int j = 0;
  
  for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
        omegaIJ[i][j] = (flowStream.getFlowSpecies().get(i).getAcentricFactor() + 
                         flowStream.getFlowSpecies().get(j).getAcentricFactor()) * 0.5;
      }
  }
  
  return omegaIJ;
  
  }//end of omegaIJ method
  
  public double[][] bValues(){
    
    double[][] bij = new double [flowStream.getFlowSpecies().size()][flowStream.getFlowSpecies().size()];
    
    double criticalTemperatures[][] = crossSpeciesCriticalTemperature();
    double criticalPressures[][] = crossSpeciesCriticalPressure();
    
    double acentricFactors[][] = omegaIJ();
    double b0 = 0.0;
    double b1 = 0.0;
    double R = 8.3145;
    
    int i = 0;
    int j = 0;
    
     for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
      for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
        b0 = 0.083 - (0.422/(Math.pow((flowStream.getTemperature()/criticalTemperatures[i][j]), 1.6)));
        b1 = 0.139 - (0.172/(Math.pow((flowStream.getTemperature()/criticalTemperatures[i][j]), 4.2)));
        bij[i][j] = (((b0 + b1 * acentricFactors[i][j]) * criticalTemperatures[i][j] * R) / criticalPressures[i][j]);
      }
     }
  
     return bij;
     
  }//end of bValues method
  
  public double[] fugacityCoefficients(){
    
    double[] results = new double[flowStream.getFlowSpecies().size()];
    double[][] bij = bValues();
    
    int i = 0;
    int j = 0; 
    int k = 0;
    double R = 8.3145;
    double T = flowStream.getTemperature();
    double P = flowStream.getPressure();
    
    for(k = 0; k < flowStream.getFlowSpecies().size(); k++){
      double fugacityCoefficient = 0.0;
      double sumTerm = 0.0;
      
      for(i = 0; i < flowStream.getFlowSpecies().size(); i++){
        for(j = 0; j < flowStream.getFlowSpecies().size(); j++){
          
          sumTerm += flowStream.getFlowSpecies().get(i).getVapourMoleFraction() * 
            flowStream.getFlowSpecies().get(j).getVapourMoleFraction() * 
            ( (4 * bij[i][k]) - bij[i][i] - (2 * bij[k][k]) - (2 * bij[i][j]) + bij[j][j]);
          
        }
      }
      
      fugacityCoefficient = Math.exp((P / (R * T)) * (bij[k][k] + (0.5 * sumTerm)));
      results[k] = fugacityCoefficient;
      sumTerm = 0.0;
    }
    
    return results;
  
  
  }//end of fugacity coefficients method
  
}//end of Fugacity class
