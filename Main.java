import java.util.Scanner;
import java.io.PrintWriter;

public class Main {
  
  public static void main(String[] args) {
    
    ConsoleUI console = new ConsoleUI();
    boolean status = false;
    while (status == false) status = console.run(new Scanner(System.in), new PrintWriter(System.out, true));    
    
  }

}