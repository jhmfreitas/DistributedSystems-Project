package com.forkexec.pts.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class InvalidPointsFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidPointsFaultException() {
	}

	public InvalidPointsFaultException(String message) {
		super(message);
	}
}