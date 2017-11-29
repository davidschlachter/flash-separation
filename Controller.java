// Takes two input streams and performs the appropriate calculation on the streams
// (typically specifiying properties of the outlet stream)

public class Controller {
  
  public static FlowStream[] calc(FlowStream inlet, FlowStream outlet) {
    
    double error = 0.001;
    
    double unknownTemperature;
    // First problem type: both inlet and outlet temperatures are specified
    if (outlet.getTemperature() > 0.0000001 && inlet.getTemperature() > 0.0000001) {
      RachfordRice rachfordRice = new RachfordRice(outlet);
      outlet = rachfordRice.solve();
      return new FlowStream[] {new FlowStream(inlet), new FlowStream(outlet)};
    } else {
      // Second and third problem types: one of the temperatures is missing, and the
      // flash is adiabatic
      
      // Determine missing temperature for initial guess
      FlowStream unspecifiedStream, specifiedStream;
      boolean inletSpecified;
      if (outlet.getTemperature() > 0.0 && inlet.getTemperature() == 0.0) {
        unspecifiedStream = inlet;
        specifiedStream = outlet;
        inletSpecified = false;
        //System.out.println("Controller: Unspecified stream is the inlet.");
      } else if (inlet.getTemperature() > 0.0 && outlet.getTemperature() == 0.0) {
        unspecifiedStream = outlet;
        specifiedStream = inlet;
        inletSpecified = true;
        //System.out.println("Controller: Unspecified stream is the outlet.");
      } else {
        // Satisfy the compiler re initializing all variables  :)
        unspecifiedStream = outlet;
        specifiedStream = inlet;
        inletSpecified = true;
        // Print an error... the problem here isn't anything we're expecting!
        System.out.println("ERROR: Both temperatures are specified!");
        System.exit(1);
      }
      
      Enthalpy enthalpy = new Enthalpy(inlet, outlet);
      double[] bounds = RootFinder.getBounds(enthalpy, specifiedStream.getTemperature(), 10.0, 0.0);
      double solvedFinalTemperaure = RiddersMethod.calc(enthalpy, bounds[0], bounds[1], 0.0001, false);
      //double solvedFinalTemperaure = Incremental.calc(enthalpy, bounds[0], bounds[1], 0.0001);
      
      if (inletSpecified == true) enthalpy.getOutlet().setTemperature(solvedFinalTemperaure);
      else enthalpy.getInlet().setTemperature(solvedFinalTemperaure);
      
      
      return new FlowStream[] {new FlowStream(enthalpy.getInlet()), new FlowStream(enthalpy.getOutlet())};
    }
    
  }
  
}