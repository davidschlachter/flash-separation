/**
 * FlowStream
 */

import java.util.ArrayList;
import java.util.List;

public class FlowStream {
  
  // The species with compositions in the flow stream
  private List<FlowSpecies> flowSpecies;
  
  // Stream properties
  private double molarFlowRate = 0.0;
  private double temperature = 0.0;
  private double pressure = 0.0;
  private double vapourFraction = 0.0;
  private double zL = 0.0;
  private double zV = 0.0;
  private boolean isIdeal = true;
  private double streamAX = 0.0;
  private double streamBX = 0.0;
  private double streamAY = 0.0;
  private double streamBY= 0.0;
  // Default constructor
  public FlowStream() {
    flowSpecies = new ArrayList<FlowSpecies>();
  }
  
  // Copy constructor
  public FlowStream (FlowStream source) {
    int i;
    this.flowSpecies = new ArrayList<FlowSpecies>();
    for (i = 0; i < source.flowSpecies.size(); i++) {
      this.flowSpecies.add(new FlowSpecies(source.flowSpecies.get(i)));
    }
    this.molarFlowRate = source.molarFlowRate;
    this.temperature = source.temperature;
    this.pressure = source.pressure;
    this.vapourFraction = source.vapourFraction;
    this.isIdeal = source.isIdeal;
  }
  
  // Setters
  public void setFlowSpecies(List<FlowSpecies> flowSpecies) {
    this.flowSpecies = flowSpecies;
  }
  
  public boolean addFlowSpecies(FlowSpecies flowSpecies) {
    return(this.flowSpecies.add(flowSpecies)); // add method returns true if successful
  }
  
  public boolean setMolarFlowRate(double molarFlowRate) {
    if (molarFlowRate >= 0) {
      this.molarFlowRate = molarFlowRate;
      return true;
    } else {
      return false;
    }
  }
  
  public boolean setVapourFraction(double vapourFraction) {
    if (vapourFraction >= 0.0 && vapourFraction <= 1.0) {
      this.vapourFraction = vapourFraction;
      return true;
    } else {
      return false;
    }
  };
  
  public boolean setTemperature(double temperature) {
    if (temperature >= 0) {
      this.temperature = temperature;
      return true;
    } else {
      return false;
    }
  }
  
  public boolean setPressure(double pressure, boolean restrict) {
    if(restrict == true){
      if (pressure >= 0) {
        this.pressure = pressure;
        return true;
      } else {
        return false;
      }
    }
    else {
      this.pressure = pressure;
      return true;
    }
  }
  
  public boolean setPressure(double pressure) {
    if (pressure >= 0) {
      this.pressure = pressure;
      return true;
    } else {
      return false;
    }
  }
  
  public void setZL(double zL){
    this.zL = zL;
  }
  
  public void setZV(double zV){
    this.zV=zV;
  }
  
  public void setIsIdeal(boolean isIdeal){
    this.isIdeal=isIdeal;
  }
  
  public void setStreamAX(double streamAX){
    this.streamAX = streamAX;
  }
  
  public void setStreamBX(double streamBX){
    this.streamBX = streamBX;
  }
  
  public void setStreamAY(double streamAY){
    this.streamAY = streamAY;
  }
  
  public void setStreamBY(double streamBY){
    this.streamBY = streamBY;
  }
  
  
  // Getters
  public List<FlowSpecies> getFlowSpecies() {
    return(this.flowSpecies);
  }
  
  public double getMolarFlowRate() {
    return this.molarFlowRate;
  }
  
  public double getTemperature() {
    return this.temperature;
  }
  
  public double getPressure() {
    return this.pressure;
  }
  
  public double getVapourFraction() {
    return this.vapourFraction;
  }
  
  public double getZL(){
    return this.zL;
  }
  
  public double getZV(){
    return this.zV;
  }
  
  public double getStreamAX(){
    return this.streamAX;
  }
  
  public double getStreamAY(){
    return this.streamAY;
  }
  
  public double getStreamBX(){
    return this.streamBX;
  }
  
  public double getStreamBY(){
    return this.streamBY;
  }
  
  public boolean getIsIdeal(){
    return this.isIdeal;
  }
  
  // Equals
  public boolean approxEquals(FlowStream target, double error) {
    boolean equals = true;
    int i;
    FlowSpecies sourceSpecies, targetSpecies;
    
    if (Math.abs((this.molarFlowRate - target.molarFlowRate)/this.molarFlowRate) > error) equals = false;
    if (Math.abs((this.temperature - target.getTemperature())/this.temperature) > error) equals = false;
    if (Math.abs((this.pressure - target.getPressure())/this.pressure) > error) equals = false;
    if (this.vapourFraction != 0.0){
      if (Math.abs((this.vapourFraction - target.getVapourFraction())/this.vapourFraction) > error) equals = false;
    }
    
    if (this.flowSpecies.size() != target.flowSpecies.size()) {
      equals = false;
      return false;
    }
    
    for (i = 0; i < this.flowSpecies.size(); i++) {
      sourceSpecies = this.flowSpecies.get(i);
      targetSpecies = target.flowSpecies.get(i);
      if (Math.abs((sourceSpecies.getOverallMoleFraction() - targetSpecies.getOverallMoleFraction())/sourceSpecies.getOverallMoleFraction()) > error) equals = false;
      if (Math.abs((sourceSpecies.getLiquidMoleFraction() - targetSpecies.getLiquidMoleFraction())/sourceSpecies.getLiquidMoleFraction()) > error) equals = false;
      if (Math.abs((sourceSpecies.getVapourMoleFraction() - targetSpecies.getVapourMoleFraction())/sourceSpecies.getVapourMoleFraction()) > error) equals = false;
      if (Math.abs((sourceSpecies.getCriticalPressure() - targetSpecies.getCriticalPressure())/sourceSpecies.getCriticalPressure()) > error) equals = false;
      if (Math.abs((sourceSpecies.getCriticalTemperature() - targetSpecies.getCriticalTemperature())/sourceSpecies.getCriticalTemperature()) > error) equals = false;
    }
    
    return equals;
    
  }
  
  
  //Clone method
  public FlowStream clone() {
    return new FlowStream(this);
  }
  
}