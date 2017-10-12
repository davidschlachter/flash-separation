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
  
  
  // Default constructor
  public FlowStream() {
    flowSpecies = new ArrayList<FlowSpecies>();
    //return true;
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
    
    
}
