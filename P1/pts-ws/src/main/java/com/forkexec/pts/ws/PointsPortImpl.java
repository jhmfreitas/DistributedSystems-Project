package com.forkexec.pts.ws;

import java.util.Collection;
import java.util.List;

import javax.jws.WebService;


import com.forkexec.pts.domain.Points;
import com.forkexec.pts.domain.User;
import com.forkexec.pts.domain.exceptions.*;
import com.forkexec.pts.ws.*;
//import com.forkexec.pts.ws.cli.*;


import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINamingException;



/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.pts.ws.PointsPortType", wsdlLocation = "PointsService.wsdl", name = "PointsWebService", portName = "PointsPort", targetNamespace = "http://ws.pts.forkexec.com/", serviceName = "PointsService")
public class PointsPortImpl implements PointsPortType {

    /**
     * The Endpoint manager controls the Web Service instance during its whole
     * lifecycle.
     */
    private final PointsEndpointManager endpointManager;

    /** Constructor receives a reference to the endpoint manager. */
    public PointsPortImpl(final PointsEndpointManager endpointManager) {
	this.endpointManager = endpointManager;
    }

    // Main operations -------------------------------------------------------

    @Override
	public void activateUser(final String userEmail) throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        //TODO

        Points pointsServer = Points.getInstance();

        synchronized(pointsServer) {
            try {
                pointsServer.activateUser(userEmail);
            } catch (EmailAlreadyExistsFaultException e) {
                this.EmailAlreadyExists("Email already exists");
            } catch (InvalidEmailFaultException e) {
                this.InvalidEmail("Invalid email");
            }
        }

    }

    @Override
    public int pointsBalance(final String userEmail) throws InvalidEmailFault_Exception {
        //TODO

        Points pointsServer = Points.getInstance();

        synchronized(pointsServer) {
            try {
                User user = pointsServer.getUser(userEmail);
                int balance = user.pointsBalance();
                return balance;
            }
            catch (InvalidEmailFaultException e) {
                this.InvalidEmail("Invalid email");
            }
        }

        return -1;
    }
    
    @Override
    public int addPoints(final String userEmail, final int pointsToAdd) 
	    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception {
            //TODO

            Points pointsServer = Points.getInstance();

            synchronized(pointsServer) {
                try {
                    int balance = pointsServer.addPoints(userEmail,pointsToAdd);
                    return balance;
                }
                catch (InvalidEmailFaultException e) {
                    this.InvalidEmail("Invalid email");
                }
                catch (InvalidPointsFaultException e) {
                    this.InvalidPoints("Invalid points");
                }
            }

            return -1;
    }


    @Override
    public int spendPoints(final String userEmail, final int pointsToSpend)
	    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
        //TODO

        Points pointsServer = Points.getInstance();

        synchronized(pointsServer) {
            try {
                int balance = pointsServer.spendPoints(userEmail,pointsToSpend);
                return balance;
            }
            catch (InvalidEmailFaultException e) {
                this.InvalidEmail("Invalid email");
            }
            catch (InvalidPointsFaultException e) {
                this.InvalidPoints("Invalid points");
            }
            catch (NotEnoughBalanceFaultException e) {
                this.NotEnoughBalance("Not enough balance");
            }
        }
        return -1;
    }



    // Control operations ----------------------------------------------------
    // TODO
    /** Diagnostic operation to check if service is running. */
    @Override
    public String ctrlPing(String inputMessage) {

	// If no input is received, return a default name.
	if (inputMessage == null || inputMessage.trim().length() == 0)
	    inputMessage = "friend";

	// If the park does not have a name, return a default.
	String wsName = endpointManager.getWsName();
	if (wsName == null || wsName.trim().length() == 0)
	    wsName = "Park";


    
    // If the park does not have a name, return a default.
	wsName = endpointManager.getWsName();
	if (wsName == null || wsName.trim().length() == 0)
		wsName = "Points";

	// Build a string with a message to return.
	final StringBuilder builder = new StringBuilder();
	builder.append("Hello ").append(inputMessage);
	builder.append(" from ").append(wsName);
	return builder.toString();


    }





    /** Return all variables to default values. */
    @Override
    public void ctrlClear() {
        //TODO

        Points.getInstance().reset();

    }

    /** Set variables with specific values. */
    @Override
    public void ctrlInit(final int startPoints) throws BadInitFault_Exception {
        //TODO

        if (startPoints < 0) throwBadInit("Invalid initialization values!");

        // Access points server
        Points pointsServer = Points.getInstance();


        synchronized(pointsServer) {
            try {
                pointsServer.setInitialBalance(startPoints);
            } catch(BadInitFaultException exception) {
                throwBadInit("Cannot set initial balance with value < 0");
            }
        }

    }

    // Exception helpers -----------------------------------------------------

    /** Helper to throw a new BadInit exception. */
    private void throwBadInit(final String message) throws BadInitFault_Exception {
        final BadInitFault faultInfo = new BadInitFault();
        faultInfo.message = message;
        throw new BadInitFault_Exception(message, faultInfo);
    }

    /** Helper to throw a new EmailAlreadyExists exception. */
    private void EmailAlreadyExists(final String message) throws EmailAlreadyExistsFault_Exception {
        final EmailAlreadyExistsFault faultInfo = new EmailAlreadyExistsFault();
        faultInfo.message = message;
        throw new EmailAlreadyExistsFault_Exception(message, faultInfo);
    }


    /** Helper to throw a new InvalidEmail exception. */
    private void InvalidEmail(final String message) throws InvalidEmailFault_Exception {
        final InvalidEmailFault faultInfo = new InvalidEmailFault();
        faultInfo.message = message;
        throw new InvalidEmailFault_Exception(message, faultInfo);
    }


    /** Helper to throw a new InvalidPoints exception. */
    private void InvalidPoints(final String message) throws InvalidPointsFault_Exception {
        final InvalidPointsFault faultInfo = new InvalidPointsFault();
        faultInfo.message = message;
        throw new InvalidPointsFault_Exception(message, faultInfo);
    }

    /** Helper to throw a new InvalidPoints exception. */
    private void NotEnoughBalance(final String message) throws NotEnoughBalanceFault_Exception {
        final NotEnoughBalanceFault faultInfo = new NotEnoughBalanceFault();
        faultInfo.message = message;
        throw new NotEnoughBalanceFault_Exception(message, faultInfo);
    }


}
