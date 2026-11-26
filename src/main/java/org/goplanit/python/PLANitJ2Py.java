package org.goplanit.python;

import java.util.logging.Logger;

import org.goplanit.logging.Logging;
import org.goplanit.utils.exceptions.PlanItException;
import org.goplanit.io.project.PlanItSimpleProject;

import py4j.GatewayServer;

/**
 * Point of entry for the Python interface. By default Py4J uses TCP port 25333 from Python to Java and TCP port 25334
 * from Java to Python. On a "java.net.BindException: Address already in use exception", choose a port other than the
 * default by hand. A previous instance may also still be running: then close it first and try again.
 * 
 * @author markr
 */
public class PLANitJ2Py {
    
    /**
     * Logger for this class
     */
    private static Logger LOGGER = null;

    /**
     * The PLANit project available to Python users
     */
    private PlanItSimpleProject project = null;
        
    // Public
    
    /**
     * Bootstrap the gateway server
     * 
     * @param args  arguments to main method
     */
    public static void main(String[] args) {
    	try {
	        if (LOGGER == null) {
	            LOGGER = Logging.createLogger(PLANitJ2Py.class);
	          }
    	}catch (Exception e) {
  			System.out.println("Failed to instantiate logger for PLANitJ2Py");
  		}
	    	
      // create the PlanItProject entry point and configure it
      PLANitJ2Py entryPoint = new PLANitJ2Py();
      
      // start the server
      GatewayServer gatewayServer = new GatewayServer(entryPoint);
      gatewayServer.start();
    }
    
    /** Initialize the project. For now we by default create a "simple" project adopting the default I/O format. This method is invoked from
     * the Python side right after the gateway is opened in the Python wrapper (in def __init_java_gateway(self)) 
     * 
     * @param projectPath path to directory contain the project files
     * @return the generated PlanItSimpleProject object
     */
    public PlanItSimpleProject initialiseSimpleProject(final String projectPath){
      try {
        this.project = new PlanItSimpleProject(projectPath); 
      }catch(Exception e)
      {
        LOGGER.severe(e.getMessage());
      }
     
      return this.project;
    }

    /** 
     * Create an enum value on the Java side from its canonical class name, which depends on the Java structure only.
     * Creating it in Python is awkward, as the gateway prefix of the path can change, for example when the variable
     * name changes. This way Python enums can have values that match their Java counterpart, without Py4J "name
     * clutter".
     * TODO: if this can be done as neatly on the Python side, do it there instead.
     *  
     * @param enumCanonicalName         the package and class name of the enum
     * @param EnumEntryName                 the entry of the enum that we try to instantiate
     * @throws ClassNotFoundException    thrown if the class specified by the enumeration cannot be found
     * @throws PlanItException                  thrown if the generated class is not an enumeration
     * @return the enumeration value
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Enum createEnum(String enumCanonicalName, String EnumEntryName) throws ClassNotFoundException, PlanItException {
        Class<?> enumClass = Class.forName(enumCanonicalName);
        if(!enumClass.isEnum()) {
            String errorMessage = String.format("Class %s is not an enum",enumCanonicalName);
            LOGGER.severe(errorMessage );
            throw new PlanItException(errorMessage);
        }
        try {
          return Enum.valueOf((Class<Enum>) enumClass, EnumEntryName);
        }catch (IllegalArgumentException e){
          LOGGER.info("Could not convert enum for %s, trying upper case conversion");
          return Enum.valueOf((Class<Enum>) enumClass, EnumEntryName.toUpperCase());
        }
    }
    
}