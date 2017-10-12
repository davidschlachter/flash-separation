import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

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
      if (choice == 'd') break;
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
        while (true) {
          output.println("  Mole fraction of " + inletStream.getFlowSpecies().get(i).getSpeciesName() + ": ");
          moleFraction = scan.nextDouble();
          if (moleFraction >= 0.0 && moleFraction <= 1.0) break;
        }
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
            while (true) {
              output.println("  Mole fraction of " + inletStream.getFlowSpecies().get(i).getSpeciesName() + ": ");
              moleFraction = scan.nextDouble();
              if (moleFraction >= 0.0 && moleFraction <= 1.0) break;
            }
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
        while (true) {
          output.println("\nWhat fraction of the feed is in the vapour phase?");
          moleFraction = scan.nextDouble();
          if (moleFraction >= 0.0 && moleFraction <= 1.0) break;
        }
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
    output.println("  Temperature (K): ");
    while (true) {
      nextString = scan.nextLine();
      if (nextString.isEmpty()) {
        nextDouble = 0.0;
        break;
      } else {
        nextDouble = Double.parseDouble(nextString);
        break;
      }
    }
    inletStream.setTemperature(nextDouble);
    output.println("  Pressure (Pa): ");
    while (true) {
      nextString = scan.nextLine();
      if (nextString.isEmpty()) {
        nextDouble = 0.0;
        break;
      } else {
        nextDouble = Double.parseDouble(nextString);
        break;
      }
    }
    inletStream.setPressure(nextDouble);
    output.println("  Molar flow rate (kg/s): ");
    while (true) {
      nextString = scan.nextLine();
      if (nextString.isEmpty()) {
        nextDouble = 0.0;
        break;
      } else {
        nextDouble = Double.parseDouble(nextString);
        break;
      }
    }
    inletStream.setMolarFlowRate(nextDouble);
    output.println("\nFor the outlet stream, enter the following properties if known:");
    output.println("  Temperature (K): ");
    while (true) {
      nextString = scan.nextLine();
      if (nextString.isEmpty()) {
        nextDouble = 0.0;
        break;
      } else {
        nextDouble = Double.parseDouble(nextString);
        break;
      }
    }
    outletStream.setTemperature(nextDouble);
    output.println("  Pressure (Pa): ");
    while (true) {
      nextString = scan.nextLine();
      if (nextString.isEmpty()) {
        nextDouble = 0.0;
        break;
      } else {
        nextDouble = Double.parseDouble(nextString);
        break;
      }
    }
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
    
    
    //
    // Logic for determining the problem type
    //
    RachfordRice rachfordRice = new RachfordRice(outletStream);
    double unknownTemperature;
    // First problem type: both inlet and outlet temperatures are specified
    if (outletStream.getTemperature() > 0.01 && inletStream.getTemperature() > 0.01) {
      outletStream = rachfordRice.solve();
      output.println("\nRESULTS: \n");
      this.printStreams(scan, output, inletStream, outletStream);
    } else {
    // Second and third problem types: one of the temperatures is missing, and the
    // flash is adiabatic
      Enthalpy enthalpy = new Enthalpy(inletStream, outletStream);
      unknownTemperature = RootFinder.calc(enthalpy, 0.01, 1000.0, 0.001);
      output.println("The unknown temperature is: " + unknownTemperature);
    }
    
    
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
    output.println("\nAdding a custom species\n");
  }
  
  private void printStreams(Scanner scan, PrintWriter output, FlowStream inletStream, FlowStream outletStream) {
    int i;
    String speciesName;
    double liquidMoleFraction, vapourMoleFraction, overallMoleFraction;
    
    output.println("NOTE: Zero may indicate that the value has not been set, e.g. for temperatures!\n");
    output.println("Inlet:");
    output.println("  Temperature: " + inletStream.getTemperature());
    output.println("  Pressure: " + inletStream.getPressure());
    output.println("  Molar flow rate: " + inletStream.getMolarFlowRate());
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
    output.println("  Molar flow rate: " + outletStream.getMolarFlowRate());
    output.println("  Total vapour fraction: " + inletStream.getVapourFraction());
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
  
  
}
