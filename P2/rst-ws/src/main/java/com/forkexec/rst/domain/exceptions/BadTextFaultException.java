package com.forkexec.rst.domain.exceptions;

/** Exception used to signal a text problem. */
public class BadTextFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadTextFaultException() {
	}

	public BadTextFaultException(String message) {
		super(message);
	}
}