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
                                                                    "  Molar flow rate: 10.0" + newline +
                                                                    "  Total vapour fraction: 0.5"));
    assertTrue("ConsoleUI set mole fractions", output.contains("Ethane  0.4000  0.3333  0.3667" + newline +
                                                               "            Pentane  0.6000  0.6667  0.6333"));
    assertTrue("ConsoleUI outlet stream properties", output.contains("Outlet:" + newline + "  Temperature: 333.0" +
                                                                     newline + "  Pressure: 101325.0" + newline +
                                                                     "  Molar flow rate: 10.0" + newline +
                                                                     "  Total vapour fraction: 0.0"));
    assertTrue("ConsoleUI outlet mole fractions", output.contains("Ethane  0.0000  0.0000  0.3667" + newline +
                                                                  "            Pentane  0.0000  0.0000  0.6333"));
    assertTrue("ConsoleUI properties prompt", output.contains("  Temperature (K): " + newline + "  Pressure (Pa): " +
                                                              newline + "  Molar flow rate (mol/s): " + newline + "" +
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
      + "6\n"
      + "\n" //No input
      + "testSpecies1\n" //testSpecies1
      + "20.49\n" //Vapour heat capacities
      + "0.377\n"
      + "-0.000117\n"
      + "0.0\n"
      + "168.6\n" //Liquid heat capacities
      + "0.0\n"
      + "0.0\n"
      + "0.0\n"
      + "8.99\n" //Antoine Coefficients
      + "1070.617\n"
      + "-40.454\n"
      + "268.6\n" //Lower T
      + "341.37\n" //Upper T
      + "469.6\n" //Critical T
      + "y\n" //Ideal?
      + "n\n" //Check --> Properties are INCORRECT
      + "testSpecies1\n" //testSpecies1
      + "20.49\n" //Vapour heat capacities
      + "0.377\n"
      + "-0.000117\n"
      + "0.0\n"
      + "168.6\n" //Liquid heat capacities
      + "0.0\n"
      + "0.0\n"
      + "0.0\n"
      + "8.99\n" //Antoine Coefficients
      + "1070.617\n"
      + "-40.454\n"
      + "268.6\n" //Lower T
      + "341.37\n" //Upper T
      + "469.6\n" //Critical T
      + "y\n" //Ideal?
      + "n\n" //Check --> Properties are CORRECT11
      + "a\n" // "Add species"
      + "6\n"
      + "testSpecies2\n" //testSpecies2
      + "20.49\n" //Vapour heat capacities
      + "0.377\n"
      + "-0.000117\n"
      + "0.0\n"
      + "168.6\n" //Liquid heat capacities
      + "0.0\n"
      + "0.0\n"
      + "0.0\n"
      + "8.99\n" //Antoine Coefficients
      + "1070.617\n"
      + "-40.454\n"
      + "268.6\n" //Lower T
      + "341.37\n" //Upper T
      + "469.6\n" //Critical T
      + "y\n" //Ideal?
      + "y\n" //Check
      + "d\n"
      + "0.4\n" //Mole fraction testSpecies1
      + "0.6\n" //Mole fraction testSpecies2
      + "y\n" //Liquid phase?
      + "\n" // Feed conditions
      + "101325\n"
      + "10\n"
      + "333\n" //Outlet conditions
      + "101325\n"
      + "y\n"
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter));
    
    String newline = System.getProperty("line.separator");
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("testSpecies1" + newline + "  testSpecies2" + newline)); //test Ideal case!
  }
}