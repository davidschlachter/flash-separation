import junit.framework.TestCase;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    
    String newline = System.getProperty("line.separator");
    
    assertTrue("ConsoleUI add species", output.contains("Ethane" + newline + "  Pentane" + newline + "  Hexane"));
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
    
    String newline = System.getProperty("line.separator");
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("Ethane" + newline + "  Hexane" + newline));
  }
  
  public void testSetPropertiesAndMoleFractions() {
    String input = "a\n"       // "Add species"
      + "0\n" // Add the first species
      + "a\n" // Add another species
      + "1\n"
      + "d\n" // Add another species
      + "-1.0\n" // Try to set an invalid mole fraction
      + "0.4\n" // Set a valid mole fraction
      + "0.8\n" // Set the second too high
      + "0.4\n" // Set the fractions right this time
      + "0.6\n"
      + "n\n" // Set the other fractions too
      + "0.33333\n"
      + "0.66666\n"
      + "-1.0\n" // Finish the rest of the properties
      + "0.5\n"
      + "\n" // No feed temperature
      + "101325\n"
      + "10\n"
      + "333\n"
      + "101325\n"
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter));
    
    String newline = System.getProperty("line.separator");
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI inlet stream properties", output.contains("Inlet:" + newline + "  Temperature: 0.0" +
                                                                    newline + "  Pressure: 101325.0" + newline +
                                                                    "  Mass flow rate: 10.0" + newline +
                                                                    "  Total vapour fraction: 0.5"));
    assertTrue("ConsoleUI set mole fractions", output.contains("Ethane  0.400  0.333  0.367" + newline +
                                                               "            Pentane  0.600  0.667  0.633"));
    assertTrue("ConsoleUI outlet stream properties", output.contains("Outlet:" + newline + "  Temperature: 333.0" +
                                                                     newline + "  Pressure: 101325.0" + newline +
                                                                     "  Mass flow rate: 10.0" + newline +
                                                                     "  Total vapour fraction: 0.0"));
    assertTrue("ConsoleUI outlet mole fractions", output.contains("Ethane  0.000  0.000  0.367" + newline +
                                                                  "            Pentane  0.000  0.000  0.633"));
    assertTrue("ConsoleUI properties prompt", output.contains("  Temperature (K): " + newline + "  Pressure (Pa): " +
                                                              newline + "  Mass flow rate (kg/s): " + newline + "" +
                                                              newline + "For the outlet stream, enter the following " + 
                                                              "properties if known:" + newline + "  Temperature (K): "
                                                                + newline + "  Pressure (Pa):"));
    assertTrue("ConsoleUI mole fractions prompt", output.contains("Mole fraction of Ethane: " + newline +
                                                                  "  Mole fraction of Ethane: " + newline +
                                                                  "  Mole fraction of Pentane: " + newline + "" +
                                                                  newline + "ERROR: Mole fractions must add to 1.0 " +
                                                                  "-- please try again!" + newline + "" + newline +
                                                                  "  Mole fraction of Ethane: " + newline +
                                                                  "  Mole fraction of Pentane:"));
  }
  
  public void testAddCustomSpecies() {
    String input = "a\n" // "Add species"
      + "0\n" // Add the first species
      + "a\n" // Add another species
      + "-0.1\n" // Try to set an invalid species
      + "a\n" // Add another species
      + "6\n" // Try to set an invalid species
      + "a\n" // Add another species
      + "1\n"
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter)); //I'm unsure why this doesn't work --> need to check
    
    String newline = System.getProperty("line.separator");
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("Ethane" + newline + "  Pentane" + newline));
  }
}
