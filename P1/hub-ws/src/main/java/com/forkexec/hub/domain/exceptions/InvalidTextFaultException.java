package com.forkexec.hub.domain.exceptions;

/** Exception used to signal a text problem. */
public class InvalidTextFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidTextFaultException() {
	}

	public InvalidTextFaultException(String message) {
		super(message);
	}
}