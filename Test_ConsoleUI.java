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
      + "testSpecies\n" //testSpecies
      + "1\n" //Vapour heat capacities
      + "1\n"
      + "1\n"
      + "1\n"
      + "1\n" //Liquid heat capacities
      + "1\n"
      + "1\n"
      + "1\n"
      + "1\n" //Antoine Coefficients
      + "1\n"
      + "1\n"
      + "1\n" //Lower T
      + "500\n" //Upper T
      + "100000\n" //Critical T
      + "y\n" //Ideal?
      + "n\n" //Check --> Properties are INCORRECT
      + "testSpecies\n" //testSpecies
      + "9.4\n" //Vapour heat capacities
      + "0.16\n"
      + "-0.000046\n"
      + "0.0\n"
      + "84.24\n" //Liquid heat capacities
      + "-0.21\n"
      + "0.00093\n"
      + "0.0000261\n"
      + "9.5\n" //Antoine Coefficients
      + "791.3\n"
      + "-6.422\n"
      + "91.33\n" //Lower T
      + "144.13\n" //Upper T
      + "305.3\n" //Critical T
      + "4900000\n" //Critical P
      + "y\n" //Ideal?
      + "y\n" //Check --> Properties are CORRECT
      + "a\n"
      + "1\n"
      + "a\n"
      + "2\n"
      + "a\n"
      + "3\n"
      + "a\n"
      + "4\n"
      + "a\n"
      + "5\n"
      + "d\n"
      + "0.2\n"
      + "0.2\n"
      + "0.2\n"
      + "0.2\n"
      + "0.1\n"
      + "0.1\n"
      + "y\n"
      + "\n"
      + "101325\n"
      + "10\n"
      + "333\n"
      + "101325\n"
      + "y\n" //Stream properties correct?
      + "y\n" //Ideal assumptions?
      + "q\n"; // Quit
    
    StringWriter stringWriter = new StringWriter();
    
    ConsoleUI console = new ConsoleUI();
    console.run(new Scanner(input), new PrintWriter(stringWriter));
    
    String newline = System.getProperty("line.separator");
    
    String output = stringWriter.toString();
    
    assertTrue("ConsoleUI add species", output.contains("testSpecies" + newline + "  Pentane"
                                                          + newline +"   Hexane" + newline + "  Cyclohexane"
                                                          + newline + "  Water" + newline + "  Nitrogen"));
  }
}