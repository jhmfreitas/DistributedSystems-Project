package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was an invalid initialization problem. */
public class InvalidInitFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidInitFaultException() {
	}

	public InvalidInitFaultException(String message) {
		super(message);
	}
}