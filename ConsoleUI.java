import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;
import java.lang.StringIndexOutOfBoundsException;

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
    boolean isIdeal;
    
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
        output.println();
      }
      
      choice = getAChar("Select an action: [a]dd species   [r]emove species   [d]one\n", scan, output);
      
      if (choice == 'a') {
        this.addSpecies(scan, output);
      } else if (choice == 'r') {
        if (this.theseSpecies.size() != 0) this.removeSpecies(scan, output);
        else {
          output.println("There are no species to remove.\n");
          continue;
        }
      } else if (choice == 'q') {
        return true;  // Exit option for testing
      } else if (choice == 'd') {
        if (this.theseSpecies.size() == 0) {
          output.println("ERROR: Must select at least one species to continue.\n");
          continue;
        } else {
          break;
        }
      } else output.println("Invalid entry:\""+choice+"\". Please try again.\n");
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
    choice = ' ';
    scan.nextLine();
    while (choice != 'y' && choice != 'n') {
      choice = getAChar("\nIs the input stream entirely in the liquid phase?\n  [y]es   [n]o\n", scan, output);
    }
    if (choice == 'y') {
      for (i = 0; i < inletStream.getFlowSpecies().size(); i++) {
        moleFraction = inletStream.getFlowSpecies().get(i).getLiquidMoleFraction();
        inletStream.getFlowSpecies().get(i).setOverallMoleFraction(moleFraction);
      }
      inletStream.setVapourFraction(0.0);
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
    scan.nextLine();
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
    outletStream.setMolarFlowRate(inletStream.getMolarFlowRate()); // Assume no reaction
    
    //
    // Show the summary of the stream properties, and confirm if they are okay!
    //
    output.println("\nSummary of stream properties entered:");
    this.printStreams(scan, output, inletStream, outletStream);
    
    choice = ' ';
    while (choice != 'y' && choice != 'n' && choice != 'q') {
      choice = getAChar("\nAre the stream properties correct?\n  [y]es   [n]o\n", scan, output);
    }
    if (choice == 'n') return false;
    if (choice == 'q') return true;  // Exit option for testing
    
    //
    // Determine if the system is ideal or non-ideal
    //
    choice = ' ';
    while(true) {
      choice = getAChar("\nWould you like to solve using ideal assumptions?\n  [y]es   [n]o\n", scan, output);
      if(choice == 'y' || choice == 'n' || choice == 'q') break;
      else {
        output.println("That is an invalid choice. Please try again.\n");
      }
    }
    switch(choice) {
      case 'y': 
        inletStream.setIsIdeal(true);
        outletStream.setIsIdeal(true);
        break;
      case 'n': 
        inletStream.setIsIdeal(false);
        outletStream.setIsIdeal(false);
        break;
      case 'q': 
        return true; //for testing
    }
    
    
    //
    // Check that the flash is possible! (outlet temperature is between dew and bubble point)
    //
    double dewPointTemperature = new DewPoint(outletStream).calc();
    double bubblePointTemperature = new BubblePoint(outletStream).calc();
    if (outletStream.getTemperature() > 0.0 && inletStream.getTemperature() > 0.0) {
      if (outletStream.getTemperature() > dewPointTemperature) {
        output.println("WARNING: The specified outlet temperature is above the dew point -- no separation will occur!");
        output.println("(Dew point is: " + dewPointTemperature + ")");
      } else if (outletStream.getTemperature() < bubblePointTemperature) {
        output.println("WARNING: The specified outlet temperature is below the bubble point -- no separation will occur!");
        output.println("(Bubble point is: " + bubblePointTemperature + ")");
      }
    } else if (outletStream.getTemperature() > 0.0) {
      if (inletStream.getTemperature() > bubblePointTemperature) {
        output.println("WARNING: The specified inlet temperature is below the bubble point -- no separation will occur!");
        output.println("(Bubble point is: " + bubblePointTemperature + ")");
      }
    } else if (inletStream.getTemperature() > 0.0) {
      if (outletStream.getTemperature() < dewPointTemperature) {
        output.println("WARNING: The specified outlet temperature is above the dew point -- no separation will occur!");
        output.println("(Dew point is: " + dewPointTemperature + ")");
      }
    }
    
    // Solve the system using the Controller class
    try {
      FlowStream[] processedStreams = Controller.calc(inletStream, outletStream);
      inletStream = processedStreams[0];
      outletStream = processedStreams[1];
    }
    catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false;
    }
    
    // Print the final results
    output.println("Composition of the inlet and solved outlet streams: \n");
    this.printStreams(scan, output, inletStream, outletStream);
    
    // Heat required to maintain operating temperature
    output.println("Heat required to maintain operating temperature: \n");
    output.println(new Enthalpy(inletStream, outletStream).calc());
    
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
      
      while (!scan.hasNextInt()) {
        scan.next();
      }
      choice = scan.nextInt();
      
      if (choice >= 0 && choice <= this.presetSpecies.size()) break;
      
    }
    
    if (choice != this.presetSpecies.size()) {
      this.theseSpecies.add(this.presetSpecies.get(choice));
    } else {
      this.addCustomSpecies(scan, output);
    }
    
    
  }
  
  private void removeSpecies(Scanner scan, PrintWriter output) {
    int i, choice;
    
    while (true) {
      output.println("\nPlease select a species to remove from the list:\n");
      for (i = 0; i < this.theseSpecies.size(); i++) {
        output.println("  " + i + " " + this.theseSpecies.get(i).getSpeciesName());
      }
      output.print("\n");
      
      while (!scan.hasNextInt()) {
        scan.next();
      }
      choice = scan.nextInt();
      
      if (choice >= 0 && choice < this.theseSpecies.size()) break;
    }
    
    this.theseSpecies.remove(choice);
    
  }
  
  private void addCustomSpecies(Scanner scan, PrintWriter output) {
    FlowSpecies customSpecies = new FlowSpecies();
    char ideal;
    double nextConstant1;
    double nextConstant2;
    double nextConstant3;
    double nextConstant4;
    double nextConstant5;
    
    output.println("\nAdding a custom species.\n");
    scan.nextLine();
    while(true) {
      while (true) {
        output.println("Enter the name of the custom species:\n");
        String speciesName = scan.nextLine();
        if(speciesName.length() == 0) {
          output.println("Error! Please enter a name of at least one character.\n");
        }
        else {
          customSpecies.setSpeciesName(speciesName);
          this.theseSpecies.add(customSpecies); 
          
          output.println("\n Enter Vapour heat capacity coefficients for A, B, C, and D:");        
          nextConstant1 = getADouble("A:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant2 = getADouble("B:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant3 = getADouble("C:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant4 = getADouble("D:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          customSpecies.setVapourHeatCapacityConstants(nextConstant1, nextConstant2, nextConstant3, nextConstant4);
          
          
          output.println("\n Enter Liquid heat capacity coefficients for A, B, C, and D:");
          nextConstant1 = getADouble("A:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant2 = getADouble("B:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant3 = getADouble("C:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant4 = getADouble("D:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          customSpecies.setLiquidHeatCapacityConstants(nextConstant1, nextConstant2, nextConstant3, nextConstant4);
          
          output.println("\nEnter Antoine equation constant (units of Pa, K) for A, B, and C :");
          nextConstant1 = getADouble("A:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant2 = getADouble("B:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant3 = getADouble("C:", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
          nextConstant4 = getADouble("Lower T:", 0.0, Double.MAX_VALUE, scan, output, true);
          nextConstant5 = getADouble("Upper T:", 0.0, Double.MAX_VALUE, scan, output, true);
          customSpecies.setAntoineConstants(new AntoineCoefficients(nextConstant1, nextConstant2, nextConstant3, nextConstant4, nextConstant5));
          
          
          output.println("Enter the critical temperature: ");          
          nextConstant1 = getADouble(" Critical temperature for "+customSpecies.getSpeciesName()+":", 0.0, Double.MAX_VALUE, scan, output, true);
          customSpecies.setCriticalTemperature(nextConstant1);
          
          ideal = ' ';
          while (ideal != 'y' && ideal != 'n') {          
            ideal = getAChar("\nWill the simulation be run in ideal-gas mode?\n  [y]es   [n]o\n", scan, output);
          }
          
          if (ideal == 'n') {
            
            scan.nextLine();
            
            output.println("Enter the critical pressure: ");
            nextConstant1 = getADouble("Critical pressure for "+customSpecies.getSpeciesName()+":", 0.0, Double.MAX_VALUE, scan, output, true);
            customSpecies.setCriticalPressure(nextConstant1);
            
            
            output.println("Enter the acentric factor for "+customSpecies.getSpeciesName()+":");
            nextConstant1 = getADouble("Acentric factor for "+customSpecies.getSpeciesName()+":", -Double.MAX_VALUE, Double.MAX_VALUE, scan, output, true);
            customSpecies.setAcentricFactor(nextConstant1);
            
          }
          
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
          if (ideal == 'n') {
            output.println("Critical temperature:             "+customSpecies.getCriticalTemperature()+" K");
            output.println("Critical pressure:                "+customSpecies.getCriticalPressure()+" Pa");
            output.println("Acentric factor:                  "+customSpecies.getAcentricFactor()+"\n");
          }
          output.println("\n------------------------------------------------\n");
          
          char choice = ' '; 
          while (choice != 'y' && choice != 'n') {          
            choice = getAChar("\nAre the custom species properties correct?\n  [y]es   [n]o\n", scan, output);
          }
          if (choice == 'y') break;
          if (choice == 'n') this.theseSpecies.remove(customSpecies) ;
        }
        
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
        while (!scan.hasNextDouble()) {
          scan.next();
        }
        userInput = scan.nextDouble();
        if (userInput >= lowerBound && userInput <= upperBound) break;
      } catch (InputMismatchException e) {
        scan.nextLine();
      }
    }
    
    return userInput;
    
  }
  
  private double getADouble(String message, double lowerBound, double upperBound, Scanner scan, PrintWriter output, boolean permitEmpty) {
    
    String nextString = " ";
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
            if (userInput < lowerBound || userInput > upperBound) continue;
          }
          break;
        }
      } catch (InputMismatchException e) {
        output.println("ERROR: Please input a valid number.");
        scan.nextLine();
      } catch (NumberFormatException e) {
        output.println("ERROR: Please input a valid number.");
      } catch (Exception e) {
        output.println("ERROR: "+e.toString());
      }
    }
    return userInput;
  }
  
  private char getAChar(String message, Scanner scan, PrintWriter output) {
    char choice;
    while (true) {
      try {
        output.println(message);
        choice = scan.next().charAt(0);
        break;
      } catch (InputMismatchException e) {
        output.println("\nWARNING: Invalid input\n");
        //scan.nextLine();
      } catch (StringIndexOutOfBoundsException e) {
        output.println("\nWARNING: Empty input\n");
        //scan.nextLine();
      }
    }
    return choice;
  }
  
}