/**
 * FlowStream
 */

import java.util.ArrayList;
import java.util.List;

public class FlowStream {
  
  // The species with compositions in the flow stream
  private List<FlowSpecies> flowSpecies = new ArrayList<FlowSpecies>();
  
  // Stream properties
  private double molarFlowRate;
  private double temperature;
  private double pressure;
  
  
  // Default constructor
  public boolean FlowStream() {
    return true;
  }
  
}
