package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class NotEnoughPointsFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughPointsFaultException() {
	}

	public NotEnoughPointsFaultException(String message) {
		super(message);
	}
}