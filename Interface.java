import java.util.Scanner;

public class Interface {

  public void Interface () {
  
    System.out.println("Welcome to Flash Separator 5000.");
      System.out.println("How many species will you be separating today?");
      
      Scanner scanner = new Scanner(System.in);
      int numberOfSpecies = scanner.nextInt();
      
      for(int i=1;i<=numberOfSpecies;i++) {
      
        System.out.println("Enter the name of species "+i);
        String speciesName = scanner.nextLine();
        //Species speciesName = new Species();   //need to find a better method to create species objects and name them properly
        //Species.setSpeciesName(speciesName);
      }
  
  }//end of Interface method
  
}//end of Interface class