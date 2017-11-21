// Takes two input streams and performs the appropriate calculation on the streams
// (typically specifiying properties of the outlet stream)

public class Controller {
  
  public static void calc(FlowStream inlet, FlowStream outlet) {
    
    // Acceptable error
    double error = 0.001;
    
    double unknownTemperature;
    // First problem type: both inlet and outlet temperatures are specified
    if (outlet.getTemperature() > 0.0000001 && inlet.getTemperature() > 0.0000001) {
      RachfordRice rachfordRice = new RachfordRice(outlet);
      outlet = rachfordRice.solve();
    } else {
      // Second and third problem types: one of the temperatures is missing, and the
      // flash is adiabatic
      
      // Determine missing temperature for initial guess
      FlowStream unspecifiedStream, specifiedStream;
      if (outlet.getTemperature() > 0.0 && inlet.getTemperature() == 0.0) {
        unspecifiedStream = inlet;
        specifiedStream = outlet;
      } else if (inlet.getTemperature() > 0.0 && outlet.getTemperature() == 0.0) {
        unspecifiedStream = outlet;
        specifiedStream = inlet;
      } else {
        // Satisfy the compiler re initializing all variables  :)
        unspecifiedStream = outlet;
        specifiedStream = inlet;
        // Print an error... the problem here isn't anything we're expecting!
        System.out.println("ERROR: Both temperatures are specified!");
        System.exit(1);
      }
      
      Enthalpy enthalpy = new Enthalpy(inlet, outlet);
      double[] bounds = RootFinder.getBounds(enthalpy, specifiedStream.getTemperature(), 10.0, 0.0);
      double solvedFinalTemperaure = RiddersMethod.calc(enthalpy, bounds[0], bounds[1], 0.0001, true);
      int i = 0;
     
      enthalpy.getOutlet().setTemperature(solvedFinalTemperaure);
      double enthalpy3 = enthalpy.calc();
      
    }
    
  }
  
}
