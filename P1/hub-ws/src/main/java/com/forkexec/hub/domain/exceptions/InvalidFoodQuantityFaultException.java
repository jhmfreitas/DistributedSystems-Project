package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was a food quantity problem. */
public class InvalidFoodQuantityFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidFoodQuantityFaultException() {
	}

	public InvalidFoodQuantityFaultException(String message) {
		super(message);
	}
}