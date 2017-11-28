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
  private double smallAX = 0.0;  
  private double smallBX = 0.0;
  private double largeAX = 0.0;
  private double largeBX = 0.0;
  private double smallAY = 0.0;
  private double smallBY = 0.0;
  private double largeAY = 0.0;
  private double largeBY = 0.0;
  private double zL = 0.0;
  private double zV = 0.0;
  private boolean isIdeal = true;
  private double streamA = 0.0;
  private double streamB = 0.0;
  
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
  
  public void setSmallAX(double smallAX){
    this.smallAX = smallAX;
  }
  
  public void setSmallBX(double smallBX){
    this.smallBX = smallBX;
  }
  
  public void setLargeAX(double largeAX){
    this.largeAX = largeAX;
  }
  
  public void setLargeBX(double largeBX){
    this.largeBX = largeBX;
  }
  
  public void setSmallAY(double smallAY){
    this.smallAY = smallAY;
  }
  
  public void setSmallBY(double smallBY){
    this.smallBY = smallBY;
  }
  
  public void setLargeAY(double largeAY){
    this.largeAY = largeAY;
  }
  
  public void setLargeBY(double largeBY){
    this.largeBY = largeBY;
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
  
  public void setStreamA(double streamA){
    this.streamA = streamA;
  }
  
  public void setStreamB(double streamB){
    this.streamB = streamB;
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
  
  public double getSmallAX(){
    return this.smallAX;
  }
  
  public double getSmallBX(){
    return this.smallBX;
  }
  
  public double getLargeAX(){
    return this.largeAX;
  }
  
  public double getLargeBX(){
    return this.largeBX;
  }
  
  public double getSmallAY(){
    return this.smallAY;
  }
  
  public double getSmallBY(){
    return this.smallBY;
  }
  
  public double getLargeAY(){
    return this.largeAY;
  }
  
  public double getLargeBY(){
    return this.largeBY;
  }
  
  public double getZL(){
    return this.zL;
  }
  
  public double getZV(){
    return this.zV;
  }
  
  public double getStreamA(){
    return this.streamA;
  }
  
  public double getStreamB(){
    return this.streamB;
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
