package com.forkexec.pts.ws;


/**
 * The application is where the service starts running. The program arguments
 * are processed here. Other configurations can also be done here.
 */
public class PointsApp {

	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length == 0 || args.length == 2) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + PointsApp.class.getName() + " wsURL OR uddiURL wsName wsURL");
			return;
		}

		String uddiURL = null;
		String wsName = null;
		String wsURL = null;

		// Create server implementation object, according to options
		PointsEndpointManager endpoint = null;
		if (args.length == 1) {
			wsURL = args[0];
			endpoint = new PointsEndpointManager(wsURL);

		} else if (args.length >= 3) {
			uddiURL = args[0];
			wsName = args[1];
			wsURL = args[2];
			endpoint = new PointsEndpointManager(uddiURL, wsName, wsURL);

		}

		try {
			endpoint.start();
			endpoint.awaitConnections();
		} finally {
			endpoint.stop();
		}

	}

}