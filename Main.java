import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  
    public static void main(String[] args) {
      
      System.out.println("Welcome to Flash Separator 5000!");
      System.out.println("Enter the names of the species you would like to separate");
      System.out.println("without using any special characters or spaces. For example,");
      System.out.println("write AceticAcid instead of Acetic Acid.");
      
      Scanner scan = new Scanner(System.in);
      ArrayList<String> speciesNames = new ArrayList<String>();
      
      boolean listIncomplete = true;
      String name;
      
      while(listIncomplete){
      
        name = scan.nextLine();
        
        if(name.length()<=0){
        listIncomplete = false;
        System.out.println("List complete.");
        } else {
        speciesNames.add(name);
        }//end of if statement
      }//end of while loop
        
        //speciesNames.trimToSize();
        System.out.println("the size of the list is "+speciesNames.size());
        

        scan.close();
      }//end of main method
     
      
    }//end of class
    
