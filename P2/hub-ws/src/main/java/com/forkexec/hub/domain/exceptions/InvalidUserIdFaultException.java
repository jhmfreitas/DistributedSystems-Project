package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was a user id problem. */
public class InvalidUserIdFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidUserIdFaultException() {
	}

	public InvalidUserIdFaultException(String message) {
		super(message);
	}
}