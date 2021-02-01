package com.forkexec.pts.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class BadInitFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadInitFaultException() {
	}

	public BadInitFaultException(String message) {
		super(message);
	}
}