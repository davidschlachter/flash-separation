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
    
    FlowSpecies ethane = new FlowSpecies();
    ethane.setSpeciesName("Ethane");
    ethane.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    ethane.setAntoineConstants(1.0, 1.0, 1.0);
    ethane.setHeatOfVapourization(1.0);
    ethane.setActivityCoefficient(1.0);
    ethane.setCriticalTemperature (300.0);
    flowSpecies.add(ethane);
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setSpeciesName("Pentane");
    pentane.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentane.setAntoineConstants(1.0, 1.0, 1.0);
    pentane.setHeatOfVapourization(1.0);
    pentane.setActivityCoefficient(1.0);
    pentane.setCriticalTemperature (300.0);
    flowSpecies.add(pentane);
    
    FlowSpecies hexane = new FlowSpecies();
    hexane.setSpeciesName("Hexane");
    hexane.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    hexane.setAntoineConstants(1.0, 1.0, 1.0);
    hexane.setHeatOfVapourization(1.0);
    hexane.setActivityCoefficient(1.0);
    hexane.setCriticalTemperature (300.0);
    flowSpecies.add(hexane);
    
    FlowSpecies cyclohexane = new FlowSpecies();
    cyclohexane.setSpeciesName("Cyclohexane");
    cyclohexane.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    cyclohexane.setAntoineConstants(1.0, 1.0, 1.0);
    cyclohexane.setHeatOfVapourization(1.0);
    cyclohexane.setActivityCoefficient(1.0);
    cyclohexane.setCriticalTemperature (300.0);
    flowSpecies.add(cyclohexane);
    
    FlowSpecies water = new FlowSpecies();
    water.setSpeciesName("Water");
    water.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    water.setAntoineConstants(1.0, 1.0, 1.0);
    water.setHeatOfVapourization(1.0);
    water.setActivityCoefficient(1.0);
    water.setCriticalTemperature (300.0);
    flowSpecies.add(water);
    
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setSpeciesName("Nitrogen");
    nitrogen.setHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    nitrogen.setAntoineConstants(1.0, 1.0, 1.0);
    nitrogen.setHeatOfVapourization(1.0);
    nitrogen.setActivityCoefficient(1.0);
    nitrogen.setCriticalTemperature (300.0);
    flowSpecies.add(nitrogen);
    
    return flowSpecies;
  
  }
  
}
