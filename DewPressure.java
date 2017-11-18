public class DewPressure{

  private FlowStream flowStream;
  
  public DewPressure(FlowStream input){
    this.flowStream = input;
  }
  
  public void computeDewParams(){
    
    double p = 0.0;
    double sumTerm = 0.0;
    int n = flowStream.getFlowSpecies().size();
    for(int i=0;i<n;i++){
      double yi = flowStream.getFlowSpecies().get(i).getOverallMoleFraction(); //assume overall mole fraction = vapour mole fraction at dewP conditions
      double pSat = SaturationPressure.calc(flowStream.getFlowSpecies().get(i), flowStream.getTemperature());
    sumTerm+=yi/pSat;
    }
    p = 1/sumTerm;  //take an initial guess at P
    
    PengRobinson nonIdealStream = new PengRobinson(flowStream);
  
  nonIdealStream.kappaI();
  nonIdealStream.alphaI();
  nonIdealStream.individualA();
  nonIdealStream.individualB();
  nonIdealStream.flowStreamSmallAYValue();
  nonIdealStream.flowStreamSmallBYValue();
  nonIdealStream.flowStreamLargeAYValue();
  nonIdealStream.flowStreamLargeBYValue();
  nonIdealStream.solveZCubicLiquid(); //do we need the vapour one as well?
  
    
  }
  


}