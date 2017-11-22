import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class ConsoleUI {
  
  private List<FlowSpecies> presetSpecies;
  private List<FlowSpecies> theseSpecies;
  
  // Constructor
  public ConsoleUI() {
    this.presetSpecies = PresetSpecies.get();
    this.theseSpecies = new ArrayList<FlowSpecies>();
  }
  
  // Run the ConsoleUI
  public boolean run(Scanner scan, PrintWriter output) {
    
    int i;
    char choice;
    double nextDouble;
    String nextString;
    boolean firstRun = true;
    
    
    //
    // Get the list of species that will be simulated
    //
    output.println("\nSelect species for the simulation:");
    while (true) {
      
      output.println("Current species selected:\n");
      if (this.theseSpecies.size() == 0) {
        output.println("  [ No species selected ]\n");
      } else {
        for (i = 0; i < this.theseSpecies.size(); i++) {
          output.println("  " + this.theseSpecies.get(i).getSpeciesName());
        }
        output.print("\n");
      }
      output.println("Select an action: [a]dd species   [r]emove species   [d]one\n");
      
      if (firstRun == false) {
        scan.nextLine();
      } else {
        firstRun = false;
      }
      choice = scan.nextLine().charAt(0);
      
      if (choice == 'a') this.addSpecies(scan, output);
      if (choice == 'r') this.removeSpecies(scan, output);
      if (choice == 'q') return true;  // Exit option for testing
      if (choice == 'd') {
        if (this.theseSpecies.size() == 0) {System.exit(1);} else {break;}
      }
    }
    
    FlowStream inletStream  = new FlowStream();
    FlowStream outletStream = new FlowStream();
    for (i = 0; i < this.theseSpecies.size(); i++) {
      inletStream.addFlowSpecies(new FlowSpecies(this.theseSpecies.get(i)));
      outletStream.addFlowSpecies(new FlowSpecies(this.theseSpecies.get(i)));
    }
    
    //
    // Get the mole fractions of these species in the inlet stream
    //
    output.println("\nFor each species in the inlet stream, please set the liquid mole fractions: \n");
    double moleFraction = -1.0, moleFractionSum = 0.0;
    while (true) {
      for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
        moleFraction = getADouble("  Mole fraction of " + inletStream.getFlowSpecies().get(i).getSpeciesName() + ": ", 0.0, 1.0, scan, output);
        inletStream.getFlowSpecies().get(i).setLiquidMoleFraction(moleFraction);
        moleFractionSum += moleFraction; 
      }
      if (moleFractionSum > 0.9999 && moleFractionSum < 1.0001) {
        break;
      } else {
        output.println("\nERROR: Mole fractions must add to 1.0 -- please try again!\n");
        moleFractionSum = 0.0;
      }
    }
    
    //
    // Are these the same as the overall mole fractions? If not, do the right thing  :) 
    //
    while (true) {
      
      output.println("\nIs the input stream entirely in the liquid phase?");
      output.println("  [y]es   [n]o\n");
      scan.nextLine();
      choice = scan.nextLine().charAt(0);
      if (choice == 'y') {
        for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
          moleFraction = inletStream.getFlowSpecies().get(i).getLiquidMoleFraction();
          inletStream.getFlowSpecies().get(i).setOverallMoleFraction(moleFraction);
        }
        inletStream.setVapourFraction(0.0);
        break;
      }
      if (choice == 'n') {
        output.println("\nFor each species in the inlet stream, please set the vapour mole fractions: \n");
        moleFraction = -1.0;
        moleFractionSum = 0.0;
        while (true) {
          for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
            moleFraction = getADouble("  Mole fraction of " + inletStream.getFlowSpecies().get(i).getSpeciesName() + ": ", 0.0, 1.0, scan, output);
            inletStream.getFlowSpecies().get(i).setVapourMoleFraction(moleFraction);
            moleFractionSum += moleFraction; 
          }
          if (moleFractionSum > 0.9999 && moleFractionSum < 1.0001) {
            break;
          } else {
            output.println("\nERROR: Mole fractions must add to 1.0 -- please try again!\n");
            moleFractionSum = 0.0;
          }
        }
        moleFraction = getADouble("\nWhat fraction of the feed is in the vapour phase?", 0.0, 1.0, scan, output);
        inletStream.setVapourFraction(moleFraction);
        for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
          moleFraction = inletStream.getFlowSpecies().get(i).getLiquidMoleFraction() * (1.0 - inletStream.getVapourFraction())
            + inletStream.getFlowSpecies().get(i).getVapourMoleFraction() * inletStream.getVapourFraction();
          inletStream.getFlowSpecies().get(i).setOverallMoleFraction(moleFraction);
        }
        nextString = scan.nextLine();
      }
      break;
    }
    
    //
    // Transfer over the overall mole fractions to the outlet stream
    //
    for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
      moleFraction = inletStream.getFlowSpecies().get(i).getOverallMoleFraction();
      outletStream.getFlowSpecies().get(i).setOverallMoleFraction(moleFraction);
    }
    
    
    //
    // Set general stream properties (T, P, flowrate)
    //
    output.println("\nFor the input stream, enter the following properties if known:");
    nextDouble = getADouble("  Temperature (K): ", 0.0, Double.MAX_VALUE, scan, output, true);
    inletStream.setTemperature(nextDouble);
    nextDouble = getADouble("  Pressure (Pa): ", 0.0, Double.MAX_VALUE, scan, output, true);
    inletStream.setPressure(nextDouble);
    nextDouble = getADouble("  Mass flow rate (kg/s): ", 0.0, Double.MAX_VALUE, scan, output, true);
    inletStream.setMolarFlowRate(nextDouble);
    output.println("\nFor the outlet stream, enter the following properties if known:");
    nextDouble = getADouble("  Temperature (K): ", 0.0, Double.MAX_VALUE, scan, output, true);
    outletStream.setTemperature(nextDouble);
    nextDouble = getADouble("  Pressure (Pa): ", 0.0, Double.MAX_VALUE, scan, output, true);
    outletStream.setPressure(nextDouble);
    outletStream.setMolarFlowRate(inletStream.getMolarFlowRate());
    
    //
    // Show the summary of the stream properties, and confirm if they are okay!
    //
    output.println("\nSummary of stream properties entered:");
    this.printStreams(scan, output, inletStream, outletStream);
    
    while (true) {
      output.println("\nAre the stream properties correct?");
      output.println("  [y]es   [n]o");
      choice = scan.nextLine().charAt(0);
      if (choice == 'y') break;
      if (choice == 'n') return false;
      if (choice == 'q') return true;  // Exit option for testing
    }
    
    //
    // Check that the flash is possible! (outlet temperature is between dew and bubble point)
    //
    DewPoint dewPoint = new DewPoint(outletStream);
    double dewPointTemperature = dewPoint.calc();
    BubblePoint bubblePoint = new BubblePoint(outletStream);
    double bubblePointTemperature = bubblePoint.calc();
    if (outletStream.getTemperature() > 0.01 && inletStream.getTemperature() > 0.01) {
      if (outletStream.getTemperature() < bubblePointTemperature) {
        output.println("WARNING: The specified outlet temperature is below the bubble point -- no separation will occur!");
        output.println("(Bubble point is: " + bubblePointTemperature + ")");
      }
      if (outletStream.getTemperature() > dewPointTemperature) {
        output.println("WARNING: The specified outlet temperature is above the dew point -- no separation will occur!");
        output.println("(Dew point is: " + dewPointTemperature + ")");
      }
    } else if (inletStream.getTemperature() < 0.01) {
      if (inletStream.getTemperature() > bubblePointTemperature) {
        output.println("WARNING: The specified inlet temperature is below the bubble point -- no separation will occur!");
        output.println("(Bubble point is: " + bubblePointTemperature + ")");
      }
    } else if (outletStream.getTemperature() < 0.01) {
      if (outletStream.getTemperature() < dewPointTemperature) {
        output.println("WARNING: The specified outlet temperature is above the dew point -- no separation will occur!");
        output.println("(Dew point is: " + dewPointTemperature + ")");
      }
    }
    
    // Calculate what's missing in the stream(s)
    FlowStream[] processedStreams = Controller.calc(inletStream, outletStream);
    inletStream = processedStreams[0];
    outletStream = processedStreams[1];
    
    
    // Print the final results
    output.println("\nRESULTS: \n");
    this.printStreams(scan, output, inletStream, outletStream);
    
    scan.close();
    return true;
  }
  
  
  
  private void addSpecies(Scanner scan, PrintWriter output) {
    int i, choice;
    
    while (true) {
      output.println("\nPlease select a species to add from the list, or specify a new species:\n");
      
      for (i = 0; i < this.presetSpecies.size(); i++) {
        output.println("  " + i + " " + this.presetSpecies.get(i).getSpeciesName());
      }
      output.println("------------------------");
      output.println("  " + i + " New species...\n");
      
      choice = scan.nextInt();
      
      if (choice >= 0 && choice <= this.presetSpecies.size()) break;
      
    }
    
    if (choice != this.presetSpecies.size()) {
      this.theseSpecies.add(this.presetSpecies.get(choice));
    } else {
      this.addCustomSpecies(scan, output);
    }
    
    
  };
  
  private void removeSpecies(Scanner scan, PrintWriter output) {
    int i, choice;
    
    while (true) {
      output.println("\nPlease select a species to remove from the list:\n");
      
      for (i = 0; i < this.theseSpecies.size(); i++) {
        output.println("  " + i + " " + this.theseSpecies.get(i).getSpeciesName());
      }
      output.print("\n");
      
      choice = scan.nextInt();
      
      if (choice >= 0 && choice < this.presetSpecies.size()) break;
      
    }
    
    this.theseSpecies.remove(choice);
    
  };
  
  private void addCustomSpecies(Scanner scan, PrintWriter output) {
    FlowSpecies customSpecies = new FlowSpecies();
    char ideal;
    
    output.println("\nAdding a custom species.\n");
    while(true){
      while(true){
        output.println("Enter the name of the custom species (names should not include spaces):\n");
        
        String speciesName = scan.next();
        if(speciesName.length() != 0){
          customSpecies.setSpeciesName(speciesName);
          this.theseSpecies.add(customSpecies);
        }
        output.println("\nEnter vapour heat capacity coefficient A:");
        double double1 = scan.nextDouble();
        output.println("\nEnter vapour heat capacity coefficient B:");
        double double2 = scan.nextDouble();
        output.println("\nEnter vapour heat capacity coefficient C:");
        double double3 = scan.nextDouble();
        output.println("\nEnter vapour heat capacity coefficient D:");
        double double4 = scan.nextDouble();
        customSpecies.setVapourHeatCapacityConstants(double1, double2, double3, double4);
        
        output.println("\nEnter liquid heat capacity coefficient A:");
        double1 = scan.nextDouble();
        output.println("\nEnter liquid heat capacity coefficient B:");
        double2 = scan.nextDouble();
        output.println("\nEnter liquid heat capacity coefficient C:");
        double3 = scan.nextDouble();
        output.println("\nEnter liquid heat capacity coefficient D:");
        double4 = scan.nextDouble();
        customSpecies.setLiquidHeatCapacityConstants(double1, double2, double3, double4);
        
        output.println("\nEnter Antoine equation constant A (units of Pa, K):");
        double1 = scan.nextDouble();
        output.println("\nEnter Antoine equation constant B (units of Pa, K):");
        double2 = scan.nextDouble();
        output.println("\nEnter Antoine equation constant C (units of Pa, K):");
        double3 = scan.nextDouble();
        output.println("\nEnter the lower temperature bound for these constants (K):");
        double4 = scan.nextDouble();
        output.println("\nEnter the upper temperature bound for these constants (K):");
        double double5 = scan.nextDouble();
        customSpecies.setAntoineConstants(new AntoineCoefficients(double1, double2, double3, double4, double5));
        
        output.println("Enter the critical temperature for "+customSpecies.getSpeciesName()+":");
        double1 = scan.nextDouble();
        customSpecies.setCriticalTemperature(double1);
        
        output.println("Will the simulation be run in ideal-gas mode?");
        output.println("[y]es / [n]o");
        ideal = scan.next().charAt(0);
        
        if(ideal == 'n'){
          
          output.println("Enter the critical pressure for "+customSpecies.getSpeciesName()+":");
          double1 = scan.nextDouble();
          customSpecies.setCriticalPressure(double1);
          
          output.println("Enter the critical volume for "+customSpecies.getSpeciesName()+":");
          double1 = scan.nextDouble();
          customSpecies.setCriticalVolume(double1);
          
          output.println("Enter the critical Z-value for "+customSpecies.getSpeciesName()+":");
          double1 = scan.nextDouble();
          customSpecies.setCriticalZ(double1);
          
          output.println("Enter the acentric factor for "+customSpecies.getSpeciesName()+":");
          double1 = scan.nextDouble();
          customSpecies.setAcentricFactor(double1);
        } else {}
        
        double[] verificationPrint4 = new double[4];
        double[] verificationPrint3 = new double[3]; //can i avoid initializing two arrays to accomodate different lengths?
        
        output.println("Are these properties correct?\n");
        output.println("------------------------------------------------\n");
        verificationPrint4 = customSpecies.getVapourHeatCapacityConstants();
        output.println("Vapour heat capacity coefficients: A="+verificationPrint4[0]+" B="+verificationPrint4[1]+
                       " C="+verificationPrint4[2]+" D="+verificationPrint4[3]);
        verificationPrint4 = customSpecies.getLiquidHeatCapacityConstants();
        output.println("Liquid heat capacity coefficients: A="+verificationPrint4[0]+" B="+verificationPrint4[1]+
                       " C="+verificationPrint4[2]+" D="+verificationPrint4[3]);
        verificationPrint3 = customSpecies.getAntoineConstants(1.0); // Hacky -- should use a real number / accurate range
        output.println("Antoine equation constants:        A="+verificationPrint3[0]+" B="+verificationPrint3[1]+" C="+verificationPrint3[2]);
        if(ideal == 'n'){
          output.println("Critical temperature:             "+customSpecies.getCriticalTemperature()+" K");
          output.println("Critical pressure:                "+customSpecies.getCriticalPressure()+" Pa");
          output.println("Critical volume:                  "+customSpecies.getCriticalVolume()+" m^3/mol");
          output.println("Critical Z-value:                 "+customSpecies.getCriticalZ());
          output.println("Acentric factor:                  "+customSpecies.getAcentricFactor()+"\n");
        } else{}
        output.println("\n------------------------------------------------\n");
        
        char choice; 
        output.println("\nAre the stream properties correct?");
        output.println("  [y]es   [n]o");
        choice = scan.next().charAt(0);
        if (choice == 'y') break;
        if (choice == 'n') this.theseSpecies.remove(customSpecies) ;
      }
      
      
      break;
      
    }
    
    
    
  }
  
  public static void printStreams(Scanner scan, PrintWriter output, FlowStream inletStream, FlowStream outletStream) {
    int i;
    String speciesName;
    double liquidMoleFraction, vapourMoleFraction, overallMoleFraction;
    
    output.println("NOTE: Zero may indicate that the value has not been set, e.g. for temperatures!\n");
    output.println("Inlet:");
    output.println("  Temperature: " + inletStream.getTemperature());
    output.println("  Pressure: " + inletStream.getPressure());
    output.println("  Mass flow rate: " + inletStream.getMolarFlowRate());
    output.println("  Total vapour fraction: " + inletStream.getVapourFraction());
    output.println("  Species and mole fractions: ");
    output.printf("    %15s  %5s  %5s  %5s%n", "Name", " liq ", " vap ", "total");
    output.println("----------------------------------------");
    for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
      speciesName = inletStream.getFlowSpecies().get(i).getSpeciesName();
      liquidMoleFraction = inletStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      vapourMoleFraction = inletStream.getFlowSpecies().get(i).getVapourMoleFraction();
      overallMoleFraction = inletStream.getFlowSpecies().get(i).getOverallMoleFraction();
      output.printf("    %15s  %.3f  %.3f  %.3f%n", speciesName, liquidMoleFraction, vapourMoleFraction, overallMoleFraction);
    }
    output.println("Outlet:");
    output.println("  Temperature: " + outletStream.getTemperature());
    output.println("  Pressure: " + outletStream.getPressure());
    output.println("  Mass flow rate: " + outletStream.getMolarFlowRate());
    output.println("  Total vapour fraction: " + outletStream.getVapourFraction());
    output.println("  Species and mole fractions: ");
    output.printf("    %15s  %5s  %5s  %5s%n", "Name", "liq", "vap", "total");
    output.println("----------------------------------------");
    for (i = 0; i < outletStream.getFlowSpecies().size(); i++) {
      speciesName = outletStream.getFlowSpecies().get(i).getSpeciesName();
      liquidMoleFraction = outletStream.getFlowSpecies().get(i).getLiquidMoleFraction();
      vapourMoleFraction = outletStream.getFlowSpecies().get(i).getVapourMoleFraction();
      overallMoleFraction = outletStream.getFlowSpecies().get(i).getOverallMoleFraction();
      output.printf("    %15s  %.3f  %.3f  %.3f%n", speciesName, liquidMoleFraction, vapourMoleFraction, overallMoleFraction);
    }
  }
  
  private double getADouble(String message, double lowerBound, double upperBound, Scanner scan, PrintWriter output) {
    
    double userInput = 0.0;
    String nextString;
    
    while (true) {
      try {
        output.println(message);
        userInput = scan.nextDouble();
        if (userInput >= lowerBound && userInput <= upperBound) break;
      } catch (InputMismatchException e) {
        scan.nextLine();
      }
    }
    
    return userInput;
    
  }
  
  private double getADouble(String message, double lowerBound, double upperBound, Scanner scan, PrintWriter output, boolean permitEmpty) {
    
    String nextString;
    double userInput = 0.0;
    
    while (true) {
      try {
        output.println(message);
        nextString = scan.nextLine();
        if (nextString.isEmpty()) {
          userInput = 0.0;
          break;
        }
        else {
          userInput = Double.parseDouble(nextString);
          if (lowerBound != upperBound) {
            if (userInput <= lowerBound || userInput >= upperBound) continue;
          }
          break;
        }
      } catch (InputMismatchException e) {
        scan.nextLine();
      }
    }
    return userInput;
  }
  
}
