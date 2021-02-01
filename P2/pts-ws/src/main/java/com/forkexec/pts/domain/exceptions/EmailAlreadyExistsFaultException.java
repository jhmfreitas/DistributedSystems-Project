package com.forkexec.pts.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class EmailAlreadyExistsFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsFaultException() {
	}

	public EmailAlreadyExistsFaultException(String message) {
		super(message);
	}
}