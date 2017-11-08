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
    List<AntoineCoefficients> ethaneAntoine = new ArrayList<AntoineCoefficients>();
    ethaneAntoine.add(new AntoineCoefficients(9.512776612, 791.3, -6.422, 91.33, 144.13)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4&Type=ANTOINE#ANTOINE
    ethaneAntoine.add(new AntoineCoefficients(8.944066612, 659.739, -16.719, 135.74, 199.91)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4&Type=ANTOINE#ANTOINE
    ethane.setAntoineConstants(ethaneAntoine);
    ethane.setCriticalTemperature (305.3);
    flowSpecies.add(ethane);
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setSpeciesName("Pentane");
    pentane.setVapourHeatCapacityConstants(2.464, 45.351, 14.111, 0.0);
    pentane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    pentane.setAntoineConstants(new AntoineCoefficients(8.994916612, 1070.617, -40.454, 268.6, 341.37)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C109660&Mask=4&Type=ANTOINE&Plot=on
    pentane.setCriticalTemperature (469.6);
    flowSpecies.add(pentane);
    
    FlowSpecies hexane = new FlowSpecies();
    hexane.setSpeciesName("Hexane");
    hexane.setVapourHeatCapacityConstants(3.025, 53.722, 16.791, 0.0);
    hexane.setLiquidHeatCapacityConstants(1.0, 1.0, 1.0, 1.0);
    List<AntoineCoefficients> hexaneAntoine = new ArrayList<AntoineCoefficients>();
    hexaneAntoine.add(new AntoineCoefficients(8.461756612, 1044.038, -53.896, 117.7, 264.93)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    hexaneAntoine.add(new AntoineCoefficients(9.008376612, 1171.53, -48.784, 286.18, 342.69)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    hexane.setAntoineConstants(hexaneAntoine);
    hexane.setCriticalTemperature (507.6);
    flowSpecies.add(hexane);
    
    FlowSpecies cyclohexane = new FlowSpecies();
    cyclohexane.setSpeciesName("Cyclohexane");
    cyclohexane.setVapourHeatCapacityConstants(-3.876, 63.249, -20.928, 0.0);
    cyclohexane.setLiquidHeatCapacityConstants(-9.048, 0.14138, -0.00016162, 0.0);
    List<AntoineCoefficients> cyclohexaneAntoine = new ArrayList<AntoineCoefficients>();
    cyclohexaneAntoine.add(new AntoineCoefficients(9.145546612, 1316.554, -35.581, 323, 523)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.997716612, 1216.93, -48.621, 303, 343)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.176966612, 780.637, -107.29, 315.7, 353.9)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.975596612, 1203.562, -50.287, 293.06, 354.73)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexane.setAntoineConstants(cyclohexaneAntoine);
    cyclohexane.setCriticalTemperature (554.0);
    flowSpecies.add(cyclohexane);
    
    FlowSpecies water = new FlowSpecies();
    water.setSpeciesName("Water");
    water.setVapourHeatCapacityConstants(3.47, 1.45, 0.0, 0.121);
    water.setLiquidHeatCapacityConstants(8.712, 0.00125, -0.00000018, 0.0);
    List<AntoineCoefficients> waterAntoine = new ArrayList<AntoineCoefficients>();
    waterAntoine.add(new AntoineCoefficients(8.565306612, 643.748, -198.043, 379, 575)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.40792661, 1838.675, -31.737, 273, 303)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.20960661, 1733.926, -39.485, 304, 333)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.08251661, 1659.793, -45.854, 334, 363)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.08925661, 1663.125, -45.622, 344, 373.2)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(11.21534661, 2354.731, 7.559, 293, 343)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(9.660016612, 1435.264, -64.848, 255.9, 373.2)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    water.setAntoineConstants(waterAntoine);
    water.setCriticalTemperature (647.0);
    flowSpecies.add(water);
    
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setSpeciesName("Nitrogen");
    nitrogen.setVapourHeatCapacityConstants(3.28, 0.593, 0.0, 0.04);
    nitrogen.setLiquidHeatCapacityConstants(0.0, 0.0, 0.0, 0.0);
    List<AntoineCoefficients> nitrogenAntoine = new ArrayList<AntoineCoefficients>();
    nitrogenAntoine.add(new AntoineCoefficients(8.741916612, 264.651, -6.788, 78.0, 126)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7727379&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    nitrogenAntoine.add(new AntoineCoefficients(8.643636612, 257.877, -6.344, 63.14, 78.0)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7727379&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    nitrogen.setAntoineConstants(nitrogenAntoine);
    nitrogen.setCriticalTemperature (126.2);
    flowSpecies.add(nitrogen);
    
    return flowSpecies;
    
  }
  
}
