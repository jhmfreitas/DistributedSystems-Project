package com.forkexec.pts.domain.exceptions;

/** Exception used to signal that there there was an initialization problem. */
public class NotEnoughBalanceFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughBalanceFaultException() {
	}

	public NotEnoughBalanceFaultException(String message) {
		super(message);
	}
}