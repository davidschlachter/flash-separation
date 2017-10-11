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
  public void run() {
    
    Scanner scan = new Scanner(System.in);
    
    int i;
    char choice;
    
    while (0 == 0) {
      
      System.out.println("\nSelect species for the simulation:");
      System.out.println("Current species selected:");
      if (this.theseSpecies.size() == 0) {
        System.out.println("[ No species selected ]\n");
      } else {
        for (i = 0; i < this.theseSpecies.size(); i++) {
          System.out.println(i + " " + this.theseSpecies.get(i).getSpeciesName());
        }
      }
      System.out.println("Select an action: [a]dd species   [r]emove species   [d]one\n");

      choice = scan.nextLine().charAt(0);
      
      if (choice == 'a') this.addSpecies();
      if (choice == 'r') this.removeSpecies();
      if (choice == 'd') break;
    }
    
    scan.close();
  
  }
  
  private void addSpecies() {
    System.out.println("Adding a species");
  };
  
  private void removeSpecies() {
    System.out.println("Removing a species");
  };
  
  
}
