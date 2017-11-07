/*
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
    ethane.setVapourHeatCapacityConstants(1.131, 19.225, -5.561, 0.0);
    ethane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    ethane.setAntoineConstants(new AntoineCoefficients(8.9440666, 659.739, -16.719, 135.74, 199.91)); // Source http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4&Type=ANTOINE
    ethane.setCriticalTemperature (305.3);
    flowSpecies.add(ethane);
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setSpeciesName("Pentane");
    pentane.setVapourHeatCapacityConstants(2.464, 45.351, 14.111, 0.0);
    pentane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentane.setAntoineConstants(new AntoineCoefficients(8.9892, 1070.617, -40.454, 0.0, 0.0));
    pentane.setCriticalTemperature (469.6);
    flowSpecies.add(pentane);
    
    FlowSpecies hexane = new FlowSpecies();
    hexane.setSpeciesName("Hexane");
    hexane.setVapourHeatCapacityConstants(3.025, 53.722, 16.791, 0.0);
    hexane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    hexane.setAntoineConstants(new AntoineCoefficients(9.00266, 1171.53, -48.784, 0.0, 0.0));
    hexane.setCriticalTemperature (507.6);
    flowSpecies.add(hexane);
    
    FlowSpecies cyclohexane = new FlowSpecies();
    cyclohexane.setSpeciesName("Cyclohexane");
    cyclohexane.setVapourHeatCapacityConstants(-3.876, 63.249, -20.928, 0.0);
    cyclohexane.setLiquidHeatCapacityConstants(-9.048, 0.14138, -0.00016162, 0.0);
    cyclohexane.setAntoineConstants(new AntoineCoefficients(9.13983, 1316.554, -35.581, 0.0, 0.0));
    cyclohexane.setCriticalTemperature (554.0);
    flowSpecies.add(cyclohexane);
    
    FlowSpecies water = new FlowSpecies();
    water.setSpeciesName("Water");
    water.setVapourHeatCapacityConstants(3.47, 1.45, 0.0, 0.121);
    water.setLiquidHeatCapacityConstants(8.712, 0.00125, -0.00000018, 0.0);
    water.setAntoineConstants(new AntoineCoefficients(10.19621, 1730.63, -39.724, 0.0, 0.0));
    water.setCriticalTemperature (647.0);
    flowSpecies.add(water);
    
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setSpeciesName("Nitrogen");
    nitrogen.setVapourHeatCapacityConstants(3.28, 0.593, 0.0, 0.04);
    nitrogen.setLiquidHeatCapacityConstants(0.0, 0.0, 0.0, 0.0);
    nitrogen.setAntoineConstants(new AntoineCoefficients(8.63792, 257.877, -6.344, 0.0, 0.0));
    nitrogen.setCriticalTemperature (126.2);
    flowSpecies.add(nitrogen);
    
    return flowSpecies;
  
  }
  
}
