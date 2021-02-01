package com.forkexec.cc.ws.cli;

/** 
 * 
 * Exception to be thrown when something is wrong with the client. 
 * 
 */
public class CreditCardClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public CreditCardClientException() {
		super();
	}

	public CreditCardClientException(String message) {
		super(message);
	}

	public CreditCardClientException(Throwable cause) {
		super(cause);
	}

	public CreditCardClientException(String message, Throwable cause) {
		super(message, cause);
	}

}