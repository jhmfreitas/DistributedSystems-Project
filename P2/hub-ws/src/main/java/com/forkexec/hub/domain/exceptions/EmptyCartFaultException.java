package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was an empty cart problem. */
public class EmptyCartFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyCartFaultException() {
	}

	public EmptyCartFaultException(String message) {
		super(message);
	}
}