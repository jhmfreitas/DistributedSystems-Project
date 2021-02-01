package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was a food id problem. */
public class InvalidFoodIdFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidFoodIdFaultException() {
	}

	public InvalidFoodIdFaultException(String message) {
		super(message);
	}
}