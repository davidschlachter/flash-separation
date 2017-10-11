import junit.framework.TestCase;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class Test_ConsoleUI extends TestCase {
  
  public void testAddSpecies() {
    String input = "a\n"       // "Add species"
      + "0\n" // Add the first species
      + "a\n" // Add another species
      + "1\n"
      + "a\n" // Add another species
      + "2\n"
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter));
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("Ethane\n  Pentane\n  Hexane"));
  }
  
  public void testRemoveSpecies() {
    String input = "a\n"       // "Add species"
      + "0\n" // Add the first species
      + "a\n" // Add another species
      + "1\n"
      + "a\n" // Add another species
      + "2\n"
      + "r\n" // Remove the middle species
      + "1\n"
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter));
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("Ethane\n  Hexane\n"));
  }
  
}
