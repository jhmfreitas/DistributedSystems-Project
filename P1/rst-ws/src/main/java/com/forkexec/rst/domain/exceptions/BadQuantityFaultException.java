package com.forkexec.rst.domain.exceptions;

/** Exception used to signal that this isn't a valid quantity. */
public class BadQuantityFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadQuantityFaultException() {
	}

	public BadQuantityFaultException(String message) {
		super(message);
	}
}