package com.forkexec.pts.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class InvalidEmailFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidEmailFaultException() {
	}

	public InvalidEmailFaultException(String message) {
		super(message);
	}
}