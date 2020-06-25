package org.planit.python;

import java.util.logging.Logger;
import org.planit.exceptions.PlanItException;
import org.planit.io.project.PlanItSimpleProject;
import org.planit.logging.Logging;
import py4j.GatewayServer;

/**
 * Point of entry for the Python interface. Note that:
 * Py4J by default uses the TCP port 25333 to communicate from Python to Java and TCP port 25334 to communicate from Java to Python.
 * If you encounter "java.net.BindException: Address already in use exception", you should manually choose a different port than the default one.
 * It is also possible a previous instance is still running. In that case first close the previous instance and try again. 
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
     
      return (PlanItSimpleProject)this.project;      
    }

    /** 
     * Due to the difficulty of creating an enum in Python (the gateway prefix to the path is inconvenient since it might change when changing
     *  the variable name for example) we instead construct them on the Java side by providing their canonical class name solely based on the Java structure
     *  This has the benefit that we can create Python enum counterparts whose values simply reflect their Java counterpart without any Py4J "name clutter"
     *  TODO: If it is possible to do this as elegantly on the Python side than we should aim for that instead.
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
            String errorMessage = "Class is not an enum";
            LOGGER.severe(errorMessage);
            throw new PlanItException(errorMessage);
        }
        return Enum.valueOf((Class<Enum>)enumClass,EnumEntryName);        
    }
    
}