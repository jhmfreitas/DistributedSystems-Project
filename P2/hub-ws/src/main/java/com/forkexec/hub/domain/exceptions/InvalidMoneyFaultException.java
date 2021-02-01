package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was a money problem. */
public class InvalidMoneyFaultException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidMoneyFaultException() {
	}

	public InvalidMoneyFaultException(String message) {
		super(message);
	}
}