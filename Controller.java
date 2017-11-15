// Takes two input streams and performs the appropriate calculation on the streams
// (typically specifiying properties of the outlet stream)

public class Controller {
  
  public static void calc(FlowStream inlet, FlowStream outlet) {
    
    // Acceptable error
    double error = 0.001; // K
    
    
    RachfordRice rachfordRice = new RachfordRice(outlet);
    double unknownTemperature;
    // First problem type: both inlet and outlet temperatures are specified
    if (outlet.getTemperature() > 0.0 && inlet.getTemperature() > 0.0) {
      outlet = rachfordRice.solve();
    } else {
      // Second and third problem types: one of the temperatures is missing, and the
      // flash is adiabatic
      double dewPointTemperature = new DewPoint(outlet).calc();
      double bubblePointTemperature = new BubblePoint(outlet).calc();
      
      // Guess for the final temperature
      double guessTemp = (dewPointTemperature + bubblePointTemperature)/2;
      
      // Determine and set missing temperature
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
      // Initial guess for stream temperature
      unspecifiedStream.setTemperature(guessTemp);
      unspecifiedStream = new RachfordRice(unspecifiedStream).solve();
      
      double putativeAdiabaticTemperature = RiddersMethod.calc(new Enthalpy(specifiedStream, unspecifiedStream), 0.01, 1000.0, 0.001);
      
      while (Math.abs(putativeAdiabaticTemperature - guessTemp) > error) {
        guessTemp = putativeAdiabaticTemperature;
        
        unspecifiedStream.setTemperature(guessTemp);
        unspecifiedStream = new RachfordRice(unspecifiedStream).solve();
        
        putativeAdiabaticTemperature = RiddersMethod.calc(new Enthalpy(specifiedStream, unspecifiedStream), 0.01, 1000.0, 0.001);
      }
      
      Enthalpy enthalpy = new Enthalpy(inlet, outlet);
      unknownTemperature = RiddersMethod.calc(enthalpy, 0.01, 1000.0, 0.001);
      
    }
    
  }
  
}
