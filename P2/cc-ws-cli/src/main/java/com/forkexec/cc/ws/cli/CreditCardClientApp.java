package com.forkexec.cc.ws.cli;

/** 
 * Client application. 
 * 
 * Looks for Points using UDDI and arguments provided
 */
public class CreditCardClientApp {

    public static void main(String[] args) throws Exception {
        // Check arguments
        if (args.length == 0) {
            System.err.println("Argument(s) missing!");
            System.err.println("Usage: java " + CreditCardClientApp.class.getName()
                    + " wsURL OR uddiURL wsName");
            return;
        }
        
        String wsURL = "http://ws.sd.rnl.tecnico.ulisboa.pt:8080/cc";
        // Create client
        CreditCardClient client = null;

        if (wsURL != null) {
            System.out.printf("Creating client for server at %s%n", wsURL);
            client = new CreditCardClient(wsURL);
        } 

		// The following remote invocation is just a basic example.
		// The actual tests are made using JUnit.

		System.out.println("Invoke ping()...");
		String result = client.ping("client");
		System.out.print("Result: ");
		System.out.println(result);
	}

}
