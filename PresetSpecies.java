/**
 * Return the list of preset species.
 * 
 * ALL UNITS MUST BE SI (kg, K, J, s, Pa)
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class PresetSpecies {
  
  public static List<FlowSpecies> get() {
    
    List<FlowSpecies> flowSpecies = new ArrayList<FlowSpecies>();
    
    FlowSpecies water = new FlowSpecies();
    water.setAntoineConstants(10.19621302, 1730.63, -39.724);
    flowSpecies.add(water);
    
    FlowSpecies ethanol = new FlowSpecies();
    ethanol.setAntoineConstants(9.80607302, 1332.04, -73.95);
    flowSpecies.add(ethanol);
    
    return flowSpecies;
  
  }
  
}
