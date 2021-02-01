package com.forkexec.rst.domain.exceptions;

/** Exception used to signal an inexistent menu id */
public class BadMenuIdFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadMenuIdFaultException() {
	}

	public BadMenuIdFaultException(String message) {
		super(message);
	}
}