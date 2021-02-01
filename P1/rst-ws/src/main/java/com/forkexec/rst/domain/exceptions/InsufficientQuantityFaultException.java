package com.forkexec.rst.domain.exceptions;

/** Exception used to signal that there's not a sufficient quantity of menus in restaurant. */
public class InsufficientQuantityFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InsufficientQuantityFaultException() {
	}

	public InsufficientQuantityFaultException(String message) {
		super(message);
	}
}