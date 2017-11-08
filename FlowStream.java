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
  
  // Acceptable error for approxEquals
  private double error = 0.001;
  
  
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
    this.error = source.error;
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
  
  public boolean setPressure(double pressure) {
    if (pressure >= 0) {
      this.pressure = pressure;
      return true;
    } else {
      return false;
    }
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
  
  // Equals
  public boolean approxEquals(FlowStream target) {
    boolean equals = true;
    int i;
    FlowSpecies sourceSpecies, targetSpecies;
    
    if (Math.abs((this.molarFlowRate - target.molarFlowRate)/this.molarFlowRate) > this.error) equals = false;
    if (Math.abs((this.temperature - target.getTemperature())/this.temperature) > this.error) equals = false;
    if (Math.abs((this.pressure - target.getPressure())/this.pressure) > this.error) equals = false;
    if (Math.abs((this.vapourFraction - target.getVapourFraction())/this.vapourFraction) > this.error) equals = false;
    
    if (this.flowSpecies.size() != target.flowSpecies.size()) {
      equals = false;
      return false;
    }
    
    for (i = 0; i < this.flowSpecies.size(); i++) {
      sourceSpecies = this.flowSpecies.get(i);
      targetSpecies = target.flowSpecies.get(i);
      if (Math.abs((sourceSpecies.getOverallMoleFraction() - targetSpecies.getOverallMoleFraction())/sourceSpecies.getOverallMoleFraction()) > this.error) equals = false;
      if (Math.abs((sourceSpecies.getLiquidMoleFraction() - targetSpecies.getLiquidMoleFraction())/sourceSpecies.getLiquidMoleFraction()) > this.error) equals = false;
      if (Math.abs((sourceSpecies.getVapourMoleFraction() - targetSpecies.getVapourMoleFraction())/sourceSpecies.getVapourMoleFraction()) > this.error) equals = false;
      if (Math.abs((sourceSpecies.getCriticalPressure() - targetSpecies.getCriticalPressure())/sourceSpecies.getCriticalPressure()) > this.error) equals = false;
      if (Math.abs((sourceSpecies.getCriticalTemperature() - targetSpecies.getCriticalTemperature())/sourceSpecies.getCriticalTemperature()) > this.error) equals = false;
      if (Math.abs((sourceSpecies.getCriticalVolume() - targetSpecies.getCriticalVolume())/sourceSpecies.getCriticalVolume()) > this.error) equals = false;
    }
    
    return equals;
    
  }
  
  //Clone method
  public FlowStream clone() {
    return new FlowStream(this);
  }
  
}
