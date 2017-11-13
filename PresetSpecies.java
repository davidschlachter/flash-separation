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
    ethane.setVapourHeatCapacityConstants(9.4036540338, 0.159845489655, -0.0000462367109478, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    ethane.setLiquidHeatCapacityConstants(84.241323, -0.208833, 0.00092758, 0.00002610); // Source: http://nvlpubs.nist.gov/nistpubs/jres/80A/jresv80An5-6p739_A1b.pdf
    List<AntoineCoefficients> ethaneAntoine = new ArrayList<AntoineCoefficients>();
    ethaneAntoine.add(new AntoineCoefficients(9.512776612, 791.3, -6.422, 91.33, 144.13)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4&Type=ANTOINE#ANTOINE
    ethaneAntoine.add(new AntoineCoefficients(8.944066612, 659.739, -16.719, 135.74, 199.91)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4&Type=ANTOINE#ANTOINE
    ethane.setAntoineConstants(ethaneAntoine);
    ethane.setHeatOfVapourization(15500.3); // Average of http://webbook.nist.gov/cgi/cbook.cgi?ID=C74840&Mask=4
    ethane.setCriticalTemperature(305.3);
    flowSpecies.add(ethane);
    
    FlowSpecies pentane = new FlowSpecies();
    pentane.setSpeciesName("Pentane");
    pentane.setVapourHeatCapacityConstants(20.4868289472, 0.3770690663898, -0.0001173253422378, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    pentane.setLiquidHeatCapacityConstants(168.6, 0.0, 0.0, 0.0); //http://webbook.nist.gov/cgi/cbook.cgi?ID=C109660&Mask=7#Thermo-Condensed
    pentane.setAntoineConstants(new AntoineCoefficients(8.994916612, 1070.617, -40.454, 268.6, 341.37)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C109660&Mask=4&Type=ANTOINE&Plot=on
    pentane.setHeatOfVapourization(27623.75); // Average of http://webbook.nist.gov/cgi/cbook.cgi?ID=C109660&Mask=4
    pentane.setCriticalTemperature(469.6);
    flowSpecies.add(pentane);
    
    FlowSpecies hexane = new FlowSpecies();
    hexane.setSpeciesName("Hexane");
    hexane.setVapourHeatCapacityConstants(25.151240895, 0.4466694093756, -0.0001396080945018, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    hexane.setLiquidHeatCapacityConstants(189.1, 0, 0, 0); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=2#Thermo-Condensed
    List<AntoineCoefficients> hexaneAntoine = new ArrayList<AntoineCoefficients>();
    hexaneAntoine.add(new AntoineCoefficients(8.461756612, 1044.038, -53.896, 117.7, 264.93)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    hexaneAntoine.add(new AntoineCoefficients(9.008376612, 1171.53, -48.784, 286.18, 342.69)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    hexane.setAntoineConstants(hexaneAntoine);
    hexane.setHeatOfVapourization(30927.77778); // Average of http://webbook.nist.gov/cgi/cbook.cgi?ID=C110543&Mask=4
    hexane.setCriticalTemperature(507.6);
    flowSpecies.add(hexane);
    
    FlowSpecies cyclohexane = new FlowSpecies();
    cyclohexane.setSpeciesName("Cyclohexane");
    cyclohexane.setVapourHeatCapacityConstants(-32.2268461848, 0.5258812678902, -0.0001740050146944, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    cyclohexane.setLiquidHeatCapacityConstants(-75.2292322704, 1.175498326524, -0.001343782992876, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    List<AntoineCoefficients> cyclohexaneAntoine = new ArrayList<AntoineCoefficients>();
    cyclohexaneAntoine.add(new AntoineCoefficients(9.145546612, 1316.554, -35.581, 323, 523)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.997716612, 1216.93, -48.621, 303, 343)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.176966612, 780.637, -107.29, 315.7, 353.9)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexaneAntoine.add(new AntoineCoefficients(8.975596612, 1203.562, -50.287, 293.06, 354.73)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4&Type=ANTOINE&Plot=on
    cyclohexane.setAntoineConstants(cyclohexaneAntoine);
    cyclohexane.setHeatOfVapourization(31690.4); // Average of http://webbook.nist.gov/cgi/cbook.cgi?ID=C110827&Mask=4
    cyclohexane.setCriticalTemperature(554.0);
    flowSpecies.add(cyclohexane);
    
    FlowSpecies water = new FlowSpecies();
    water.setSpeciesName("Water");
    water.setVapourHeatCapacityConstants(28.851315, 0.012056025, 0.0, 100605.45); // Source: Smith & Van Ness, Appendix C, Table C1 -- all constants in book multiplied by R, units are J/mol, K
    water.setLiquidHeatCapacityConstants(72.4355737776, 0.01039307475, -0.000001496602764, 0.0); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    List<AntoineCoefficients> waterAntoine = new ArrayList<AntoineCoefficients>();
    waterAntoine.add(new AntoineCoefficients(8.565306612, 643.748, -198.043, 379, 575)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.40792661, 1838.675, -31.737, 273, 303)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.20960661, 1733.926, -39.485, 304, 333)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(10.08251661, 1659.793, -45.854, 334, 363)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    //waterAntoine.add(new AntoineCoefficients(10.08925661, 1663.125, -45.622, 344, 373.2)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(11.21534661, 2354.731, 7.559, 293, 343)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    waterAntoine.add(new AntoineCoefficients(9.660016612, 1435.264, -64.848, 255.9, 378.999)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7732185&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    water.setAntoineConstants(waterAntoine);
    water.setHeatOfVapourization(40714.5); // http://gchem.cm.utexas.edu/data/section2.php?target=heat-transition.php
    water.setCriticalTemperature(647.0);
    flowSpecies.add(water);
    
    FlowSpecies nitrogen = new FlowSpecies();
    nitrogen.setSpeciesName("Nitrogen");
    nitrogen.setVapourHeatCapacityConstants(27.271428144, 0.0049304746614, 0, 33257.8392); // Smith & Van Ness, Appendix C, Table C1. Corrected to return C_p instead of C_p / R
    nitrogen.setLiquidHeatCapacityConstants(28.57, 0.0, 0.0, 0.0); // https://technifab.com/cryogenic-resource-library/cryogenic-fluids/liquid-nitrogen/
    List<AntoineCoefficients> nitrogenAntoine = new ArrayList<AntoineCoefficients>();
    nitrogenAntoine.add(new AntoineCoefficients(8.741916612, 264.651, -6.788, 78.0, 126)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7727379&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    nitrogenAntoine.add(new AntoineCoefficients(8.643636612, 257.877, -6.344, 63.14, 78.0)); // http://webbook.nist.gov/cgi/cbook.cgi?ID=C7727379&Mask=4&Type=ANTOINE&Plot=on#ANTOINE
    nitrogen.setAntoineConstants(nitrogenAntoine);
    nitrogen.setHeatOfVapourization(5850.0); // Average of http://webbook.nist.gov/cgi/cbook.cgi?ID=C7727379&Mask=4
    nitrogen.setCriticalTemperature(126.2);
    flowSpecies.add(nitrogen);
    
    return flowSpecies;
    
  }
  
}
