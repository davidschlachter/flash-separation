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
  public void run(Scanner scan, PrintWriter output) {
    
    int i;
    char choice;
    boolean firstRun = true;
    
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
      if (choice == 'q') return;  // Exit option for testing
      if (choice == 'd') break;
    }
    
    FlowStream inletStream  = new FlowStream();
    FlowStream outletStream = new FlowStream();
    inletStream.setFlowSpecies(this.theseSpecies);
    outletStream.setFlowSpecies(this.theseSpecies);
    
    scan.close();
  
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
  
  
}
