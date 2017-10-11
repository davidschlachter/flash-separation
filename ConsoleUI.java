import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
  
  private List<FlowSpecies> presetSpecies;
  private List<FlowSpecies> theseSpecies;
  
  // Constructor
  public ConsoleUI() {
    this.presetSpecies = PresetSpecies.get();
    this.theseSpecies = new ArrayList<FlowSpecies>();
  }
  
  // Run the ConsoleUI
  public void run(Scanner scan) {
    
    int i;
    char choice;
    
    System.out.println("\nSelect species for the simulation:");
    
    while (0 == 0) {
     
      System.out.println("Current species selected:\n");
      if (this.theseSpecies.size() == 0) {
        System.out.println("  [ No species selected ]\n");
      } else {
        for (i = 0; i < this.theseSpecies.size(); i++) {
          System.out.println("  " + this.theseSpecies.get(i).getSpeciesName());
        }
        System.out.print("\n");
      }
      System.out.println("Select an action: [a]dd species   [r]emove species   [d]one\n");

      choice = scan.nextLine().charAt(0);
      
      if (choice == 'a') this.addSpecies(new Scanner(System.in));
      if (choice == 'r') this.removeSpecies(new Scanner(System.in));
      if (choice == 'd') break;
    }
    
    FlowStream inletStream  = new FlowStream();
    FlowStream outletStream = new FlowStream();
    inletStream.setFlowSpecies(this.theseSpecies);
    outletStream.setFlowSpecies(this.theseSpecies);
    
    scan.close();
  
  }
  
  private void addSpecies(Scanner scan) {
    int i, choice;
    
    while (0 == 0) {
      System.out.println("\nPlease select a species to add from the list, or specify a new species:\n");
      
      for (i = 0; i < this.presetSpecies.size(); i++) {
        System.out.println("  " + i + " " + this.presetSpecies.get(i).getSpeciesName());
      }
      System.out.println("------------------------");
      System.out.println("  " + i + " New species...\n");
      
      choice = scan.nextInt();
      
      if (choice >= 0 && choice <= this.presetSpecies.size()) break;
      
    }
    
    if (choice != this.presetSpecies.size()) {
      this.theseSpecies.add(this.presetSpecies.get(choice));
    } else {
      this.addCustomSpecies(new Scanner(System.in));
    }
    

    scan.close();
  };
  
  private void removeSpecies(Scanner scan) {
    int i, choice;
    
    while (0 == 0) {
      System.out.println("\nPlease select a species to remove from the list:\n");
      
      for (i = 0; i < this.theseSpecies.size(); i++) {
        System.out.println("  " + i + " " + this.theseSpecies.get(i).getSpeciesName());
      }
      System.out.print("\n");
      
      choice = scan.nextInt();
      
      if (choice >= 0 && choice < this.presetSpecies.size()) break;
      
    }
    
    this.theseSpecies.remove(choice);
    

    scan.close();
  };
  
  private void addCustomSpecies(Scanner scan) {
    System.out.println("\nAdding a custom species\n");
  }
  
  
}
