package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was a credit card problem. */
public class InvalidCreditCardFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCreditCardFaultException() {
	}

	public InvalidCreditCardFaultException(String message) {
		super(message);
	}
}